package com.converter.controller;

import static com.converter.util.constant.ConverterConstant.FLOATING_POINT_SCALE;
import static com.converter.util.constant.ConverterConstant.STARTING_INDEX_TABLE_A;
import static com.converter.util.constant.ConverterConstant.ZERO_INDEX;
import static com.converter.util.constant.ConverterConstant.PriceChoice.PLN;
import static com.converter.util.ConverterUtil.prepareAndGetPrice;
import static com.converter.util.ConverterUtil.validateConvertedPrice;
import static com.converter.util.ConverterUtil.validateForCurrencyType;
import static com.converter.util.ConverterUtil.validatePricesForNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.converter.entity.Table;
import com.converter.util.constant.ConverterConstant;
import com.converter.util.ConverterUtil;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ConverterController {

	@GetMapping("/")
	public String homeToCalcRedirection() {
		return "redirect:/calculator";
	}
	
	@GetMapping("/calculator")
	public String getCalculatorScreen(Model model) {
		
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.findAndRegisterModules();
		
		Table[] tableOfRates = null;
		
		try {
			tableOfRates = jsonMapper.readValue(new URL("http://api.nbp.pl/api/exchangerates/tables/a/"), Table[].class);
		} 
		catch (StreamReadException streamReadException) {
			streamReadException.printStackTrace();
		} 
		catch (DatabindException dataBindException) {
			dataBindException.printStackTrace();
		} 
		catch (MalformedURLException malformedUrlException) {
			malformedUrlException.printStackTrace();
		} 
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
		
		List<String> availableCurrencyCodes =
				tableOfRates[STARTING_INDEX_TABLE_A].getRates()
				.stream()
				.map(rate -> rate.getCode())
				.sorted()
				.collect(Collectors.toList());
		
		model.addAttribute("codes", availableCurrencyCodes);
		
		return "calculator/calculator.html";
	}
	
	@GetMapping("/calculator/convert")
	public String calculatorDoConvertion(RedirectAttributes redirectAttr,
										 @RequestParam(name = "price-other", required = false) String priceOther,
										 @RequestParam(name = "price-pln", required = false) String pricePln,
										 @RequestParam("currency") String currency) {
		
		String infoMessage = "";
		String convertionResultMessage = "";
		String priceToConvert = null;
		ConverterConstant.PriceChoice currencyType = null; 
		
		try {
			
			if((infoMessage = validatePricesForNull(priceOther, pricePln))!=null) {
				redirectAttr.addFlashAttribute("message", infoMessage);
				return "redirect:/calculator";
			}
			
			priceToConvert = prepareAndGetPrice(priceOther, pricePln);
			
			currencyType = validateForCurrencyType(priceOther, pricePln);
			
			if((infoMessage = validateConvertedPrice(priceToConvert))!=null) {
				redirectAttr.addFlashAttribute("message", infoMessage);
				return "redirect:/calculator";
			}
			
			Table rate = null;
			
			try {
				rate = ConverterUtil.prepareTableA(currency);
			}
			catch (Exception exception) {
				exception.printStackTrace();
				infoMessage = "Conversion related to selected currency is not available";
				redirectAttr.addFlashAttribute("message", infoMessage);
				return "redirect:/calculator";
			}
			
			BigDecimal currencyRatio = new BigDecimal(rate.getRates().get(ZERO_INDEX).getMid());
			infoMessage = "1 " + currency + " = " + 
					rate.getRates().get(ZERO_INDEX).getMid() + " PLN";
			
			BigDecimal valueToCalculate = null;
			BigDecimal priceToConvertScaled = new BigDecimal(priceToConvert).setScale(FLOATING_POINT_SCALE, RoundingMode.HALF_UP);
			if (currencyType.equals(PLN)) {
				valueToCalculate = (priceToConvertScaled 
						.divide(currencyRatio,FLOATING_POINT_SCALE, RoundingMode.HALF_UP));
				
				convertionResultMessage  += (valueToCalculate.setScale(FLOATING_POINT_SCALE, RoundingMode.HALF_UP) +
						" " + currency + " = " + priceToConvertScaled + " PLN");
			}
			else {
				valueToCalculate = priceToConvertScaled
						.multiply(currencyRatio);
				convertionResultMessage  += (priceToConvertScaled  + " " + currency +
						" = " + valueToCalculate.setScale(FLOATING_POINT_SCALE, RoundingMode.HALF_UP) + " PLN");
			}
			
		}
		catch (Exception exception) {
			exception.printStackTrace();
			infoMessage = "Please provide a valid number";
		}
		
		redirectAttr.addFlashAttribute("message", infoMessage);
		redirectAttr.addFlashAttribute("convertionresult", convertionResultMessage);
		return "redirect:/calculator";
	}
}
