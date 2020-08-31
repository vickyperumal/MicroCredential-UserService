package com.example.mc.dto;

import java.time.LocalDate;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardRequestDTO {

	@Email
	@NotNull(message = "EmailId must not be null")
	private String emailId;
	private String mobileNumber;
	private String fullName;
	private LocalDate dateOfBirth;
	@Size(min = 10, max = 10, message = "PanCard must be of length 10")
	@NotNull(message = "PanCard must not be null")
	private String panCard;
	private String profession;
	@Digits(fraction = 2, integer = 10)
	@NotNull(message = "AnnualIncome must not be null")
	private Double annualIncome;
	private Set<Address> address;
}
