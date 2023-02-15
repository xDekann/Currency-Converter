package com.converter.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Table {

	@JsonAlias("table")
	private String table;
	@JsonAlias("currency")
	public String currency;
	@JsonAlias("code")
	public String code;
	@JsonAlias("effectiveDate")
	private LocalDate dateOfDataUpload;
	@JsonAlias("rates")
	private List<Rate> rates;
	
}
