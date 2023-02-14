package com.converter.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.converter.entity.Table;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CalculatorController {
	
	private final int STARTING_INDEX_TABLE_A=0;

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
		} catch (StreamReadException streamReadException) {
			streamReadException.printStackTrace();
		} catch (DatabindException dataBindException) {
			dataBindException.printStackTrace();
		} catch (MalformedURLException malformedUrlException) {
			malformedUrlException.printStackTrace();
		} catch (IOException ioException) {
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
	
}
