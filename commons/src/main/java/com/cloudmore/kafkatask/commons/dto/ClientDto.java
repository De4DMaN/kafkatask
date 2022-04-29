package com.cloudmore.kafkatask.commons.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
public class ClientDto {

	@NotBlank
	@Size(max = 250)
	private String name;

	@NotBlank
	@Size(max = 250)
	private String surname;

	@PositiveOrZero
	private double wage;

	@NotNull
	private Instant eventTime;
}
