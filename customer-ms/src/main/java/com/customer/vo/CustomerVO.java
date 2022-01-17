package com.customer.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CustomerVO {

	private Long id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String dateOfBirth;
}
