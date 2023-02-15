package com.converter;

import static com.converter.util.constant.ConverterConstant.PriceChoice.OTHER;
import static com.converter.util.constant.ConverterConstant.PriceChoice.PLN;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.converter.entity.Rate;
import com.converter.entity.Table;
import com.converter.util.ConverterUtil;
import com.converter.util.constant.ConverterConstant;
class ConverterUtilTests {

	@Test
	void validateForBothNullFields() {
		
		// given
		String priceOther = null;
		String pricePln = null;
		String message = null;
		
		// when
		message = ConverterUtil.validatePricesForNull(priceOther, pricePln);
		
		// then
		Assertions.assertEquals(message, "Please fill one of the provided fields!");
	}
	
	@Test
	void validateForBothFilled() {
		
		// given
		String priceOther = "153";
		String pricePln = "153";
		String message = null;
		
		// when
		message = ConverterUtil.validatePricesForNull(priceOther, pricePln);
		
		// then
		Assertions.assertEquals(message, "Only one field should be filled!");
	}
	
	@Test
	void validateForBothBlank() {
		
		// given
		String priceOther = "";
		String pricePln = "";
		String message = null;
		
		// when
		message = ConverterUtil.validatePricesForNull(priceOther, pricePln);
		
		// then
		Assertions.assertEquals(message, "Please fill one of the provided fields!");
	}
	
	@Test
	void validateForPriceOtherCorrect() {
		
		// given
		String priceOther = "153";
		String pricePln = "";
		String message = null;
		
		// when
		message = ConverterUtil.validatePricesForNull(priceOther, pricePln);
		
		// then
		Assertions.assertNull(message);
	}

	@Test
	void validateForPricePlnCorrect() {
		
		// given
		String priceOther = "";
		String pricePln = "153";
		String message = null;
		
		// when
		message = ConverterUtil.validatePricesForNull(priceOther, pricePln);
		
		// then
		Assertions.assertNull(message);
	}
	
	@Test
	void validatePrepareForNull() {
		
		// given
		String priceOther = null;
		String pricePln = null;
		String priceToConvert = null;
		
		// when
		priceToConvert = ConverterUtil.prepareAndGetPrice(priceOther, pricePln);
		
		// then
		Assertions.assertNull(priceToConvert);
	}
	
	@Test
	void validatePrepareForBlank() {
		
		// given
		String priceOther = "";
		String pricePln = "";
		String priceToConvert = null;
		
		// when
		priceToConvert = ConverterUtil.prepareAndGetPrice(priceOther, pricePln);
		
		// then
		Assertions.assertNull(priceToConvert);
	}
	
	@Test
	void validatePrepareForCorrectOther() {
		
		// given
		String priceOther = "153";
		String pricePln = null;
		String priceToConvert = null;
		
		// when
		priceToConvert = ConverterUtil.prepareAndGetPrice(priceOther, pricePln);
		
		// then
		Assertions.assertEquals(priceToConvert, "153");
	}
	
	@Test
	void validatePrepareForCorrectPln() {
		
		// given
		String priceOther = null;
		String pricePln = "153";
		String priceToConvert = null;
		
		// when
		priceToConvert = ConverterUtil.prepareAndGetPrice(priceOther, pricePln);
		
		// then
		Assertions.assertEquals(priceToConvert, "153");
	}
	
	@Test
	void validatePrepareForInputWithLetters() {
		
		// given
		String priceOther = null;
		String pricePln = "e15G3@";
		String priceToConvert = null;
		
		// when
		priceToConvert = ConverterUtil.prepareAndGetPrice(priceOther, pricePln);
		
		// then
		Assertions.assertEquals(priceToConvert, "153");
	}
	
	@Test
	void validateForTypeBothNull() {
		
		// given
		String priceOther = null;
		String pricePln = null;
		ConverterConstant.PriceChoice currencyType = null;
		
		// when
		currencyType = ConverterUtil.checkForCurrencyType(priceOther, pricePln);
		
		// then
		Assertions.assertNull(currencyType);
	}
	
	@Test
	void validateForTypeOther() {
		
		// given
		String priceOther = "153";
		String pricePln = null;
		ConverterConstant.PriceChoice currencyType = null;
		
		// when
		currencyType = ConverterUtil.checkForCurrencyType(priceOther, pricePln);
		
		// then
		Assertions.assertEquals(currencyType, ConverterConstant.PriceChoice.OTHER);
	}
	
	@Test
	void validateForTypePln() {
		
		// given
		String priceOther = null;
		String pricePln = "153";
		ConverterConstant.PriceChoice currencyType = null;
		
		// when
		currencyType = ConverterUtil.checkForCurrencyType(priceOther, pricePln);
		
		// then
		Assertions.assertEquals(currencyType, ConverterConstant.PriceChoice.PLN);
	}
	
	@Test
	void validateConvertedForBlank() {
		
		// given
		String convertedPrice = "";
		String message = null;
		
		// when
		message = ConverterUtil.validateConvertedPrice(convertedPrice);
		
		// then
		Assertions.assertEquals(message, "Please fill one of the provided fields correctly!");
	}
	
	@Test
	void validateConvertedForAssumedLength() {
		
		// given
		String convertedPrice = "3333333333333333333333333333333333";
		String message = null;
		
		// when
		message = ConverterUtil.validateConvertedPrice(convertedPrice);
		
		// then
		Assertions.assertEquals(message, "Provided value is too long!");
	}
	
	@Test
	void validateForCorrectPrice() {
		
		// given
		String convertedPrice = "153";
		String message = null;
		
		// when
		message = ConverterUtil.validateConvertedPrice(convertedPrice);
		
		// then
		Assertions.assertNull(message);
	}
	
	@Test
	void checkPrepareTableAForExistingCurrency() throws Exception {
		
		// given
		String currency = "USD";
		Table table;
		
		// when
		table = ConverterUtil.prepareTableA(currency);
		
		// then
		Assertions.assertNotNull(table);
	}
	
	@Test
	void checkPrepareTableAForNonCurrency(){
		
		Exception exception = Assertions.assertThrows(Exception.class,
				() -> ConverterUtil.prepareTableA("USDD"), "no exception");
		
		Assertions.assertNotEquals(exception.getMessage(), "no exception");
	}
	
	@Test
	void testConvertStringToDecimalAndScaleSuccessfully() {
		
		// given
		String price="12.54321";
		BigDecimal convertedPrice = null;
		
		// when
		convertedPrice = ConverterUtil.convertStringToDecimalAndScale(price);
		
		// then
		Assertions.assertEquals(convertedPrice.toString(), "12.54");
	}
	
	@Test
	void testCnvertStringToDecimalAndScaleException() {
		
		NumberFormatException numException = Assertions
				.assertThrows(NumberFormatException.class,
						() -> ConverterUtil.convertStringToDecimalAndScale("1e2.f"),
						"no exception");
		
		Assertions.assertNotEquals(numException.getMessage(), "no exception");
	}
	
	@Test
	void testConvertionResultPlnToOther() {
		
		// given
		BigDecimal priceToConvert = new BigDecimal("150.00");
		BigDecimal finalValue = null;
		
		Rate rate = Rate.builder()
				.mid(5.3674)
				.build();
		
		Table tableWithSingleRate = Table.builder()
				.rates(new ArrayList<>(List.of(rate)))
				.build();
		
		ConverterConstant.PriceChoice currencyType = PLN;
		
		// when
		finalValue = ConverterUtil.getConversionResult(tableWithSingleRate,
				currencyType, priceToConvert);
		
		// then
		Assertions.assertEquals(finalValue.toString(), "27.95");
	}
	
	@Test
	void testConvertionResultOtherToPln() {
		
		// given
		BigDecimal priceToConvert = new BigDecimal("0.6");
		BigDecimal finalValue = null;
		
		Rate rate = Rate.builder()
				.mid(5.3674)
				.build();
		
		Table tableWithSingleRate = Table.builder()
				.rates(new ArrayList<>(List.of(rate)))
				.build();
		
		ConverterConstant.PriceChoice currencyType = OTHER;
		
		// when
		finalValue = ConverterUtil.getConversionResult(tableWithSingleRate,
				currencyType, priceToConvert);
		
		// then
		Assertions.assertEquals(finalValue.toString(), "3.22");
	}
}
