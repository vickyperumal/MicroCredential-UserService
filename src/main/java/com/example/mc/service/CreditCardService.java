package com.example.mc.service;

import com.example.mc.dto.CreditCardConfirmation;
import com.example.mc.dto.CreditCardRequestDTO;
import com.example.mc.serviceimpl.ResponseDetails;

public interface CreditCardService {
	
	public String saveCreditCardRequest(CreditCardRequestDTO request);
	
	public ResponseDetails<Object> getCreditCardDetails(String requestId);
	
	public void updateCreditCardRequest(CreditCardConfirmation creditCardConfirmation);

}
