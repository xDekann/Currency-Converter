package com.converter;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.converter.controller.ConverterController;

@WebMvcTest(ConverterController.class)
@AutoConfigureMockMvc
public class ConverterControllerTests {

	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testConverterForCorrectValue() throws Exception {
		
		String priceOther = null;
		String pricePln = "153";
		String currency = "USD";
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/calculator/convert")
			.param("price-other", priceOther)
			.param("price-pln", pricePln)
			.param("currency", currency))
			.andExpect(MockMvcResultMatchers.flash().attribute("convertionresult", Matchers.notNullValue()))
			.andExpect(MockMvcResultMatchers.flash().attribute("message", Matchers.notNullValue()))
			.andExpect(redirectedUrl("/calculator"))
			.andExpect(status().isFound());		
	}
	
	@Test
	void testConverterForWrongValue() throws Exception {
		
		String priceOther = null;
		String pricePln = "15d3d";
		String currency = "USD";
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.get("/calculator/convert")
			.param("price-other", priceOther)
			.param("price-pln", pricePln)
			.param("currency", currency))
			.andExpect(MockMvcResultMatchers.flash().attribute("convertionresult", Matchers.notNullValue()))
			.andExpect(MockMvcResultMatchers.flash().attribute("message", Matchers.notNullValue()))
			.andExpect(redirectedUrl("/calculator"))
			.andExpect(status().isFound());		
	}
	
	@Test
	void testShowCalculator() throws Exception {
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/calculator"))
		.andExpect(MockMvcResultMatchers.model().attributeExists("codes"))
		.andExpect(status().isOk());		
	}
}
