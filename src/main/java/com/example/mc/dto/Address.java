package com.example.mc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	private String type;
	private String streetdetails;
	private String city;
	private String state;
	private Long pincode;
}
