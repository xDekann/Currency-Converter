package com.converter.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Rate {

	@JsonAlias("effectiveDate")
	private LocalDate dateOfDataUpload;
	@JsonAlias("mid")
	public double mid;
	@JsonAlias("code")
	public String code;
	
}
