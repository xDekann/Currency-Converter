package com.converter.util;

import static com.converter.util.constant.ConverterConstant.ASSUMED_MAX_NUMBER_LENGTH;
import static com.converter.util.constant.ConverterConstant.FLOATING_POINT_SCALE;
import static com.converter.util.constant.ConverterConstant.ZERO_INDEX;
import static com.converter.util.constant.ConverterConstant.PriceChoice.OTHER;
import static com.converter.util.constant.ConverterConstant.PriceChoice.PLN;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;

import com.converter.entity.Table;
import com.converter.util.constant.ConverterConstant;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterUtil {
	
	public static String validatePricesForNull(String priceOther, String pricePln) {
		
		String message = null;
		
		if ((priceOther != null && !priceOther.isBlank()) &&
				(pricePln != null && !pricePln.isBlank()))
			return message = "Only one field should be filled!";
		else if ((priceOther == null || priceOther.isBlank()) &&
				(pricePln == null || pricePln.isBlank()))
			return message = "Please fill one of the provided fields!";
		
		return message;
	}
	
	public static String prepareAndGetPrice(String priceOther, String pricePln) {
		
		String priceToConvert = null;
		if (priceOther != null && !priceOther.isBlank())
			priceToConvert = priceOther;
		else if (pricePln != null && !pricePln.isBlank())
			priceToConvert= pricePln;
		
		if(priceToConvert != null)
			priceToConvert = priceToConvert.replaceAll("[^\\d.]", "");

		return priceToConvert;
	}
	
	public static ConverterConstant.PriceChoice checkForCurrencyType(String priceOther, String pricePln){
		
		if (priceOther !=null && !priceOther.isBlank())
			return OTHER;
		else if (pricePln != null && !pricePln.isBlank())
			return PLN;
		else 
			return null;
	}
	
	public static String validateConvertedPrice(String convertedPrice) {
		
		String message = null;
		
		if (convertedPrice.isBlank() || convertedPrice.startsWith("."))
			message = "Please fill one of the provided fields correctly!";
		else if (convertedPrice.length()>=ASSUMED_MAX_NUMBER_LENGTH) 
			message = "Provided value is too long!";

		return message;
	}
	
	public static Table prepareTableA(String currencyType) throws StreamReadException, DatabindException, MalformedURLException, IOException {
		
		ObjectMapper jsonMapper = new ObjectMapper();
		jsonMapper.findAndRegisterModules();
		
		return jsonMapper.readValue(new URL("http://api.nbp.pl/api/exchangerates/rates/a/" +
					currencyType.toLowerCase() + "/"),
						Table.class);
	}
	
	public static BigDecimal convertStringToDecimalAndScale(String priceToConvert) {
		return new BigDecimal(priceToConvert).setScale(FLOATING_POINT_SCALE, RoundingMode.HALF_UP);
	}
	
	public static BigDecimal getConversionResult(Table singleRate, 
												 ConverterConstant.PriceChoice currencyType,
												 BigDecimal priceToConvertScaled) {
		BigDecimal valueToCalculate = null;
		BigDecimal currencyRatio =
				new BigDecimal(singleRate.getRates().get(ZERO_INDEX).getMid());
		
		if (currencyType.equals(PLN)) 
			valueToCalculate = (priceToConvertScaled 
					.divide(currencyRatio,FLOATING_POINT_SCALE, RoundingMode.HALF_UP));	
		else
			valueToCalculate = priceToConvertScaled
			.multiply(currencyRatio).setScale(FLOATING_POINT_SCALE, RoundingMode.HALF_UP);
		
		return valueToCalculate;
	}
}
