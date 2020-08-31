package com.example.mc.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mc.dto.CreditCardConfirmation;
import com.example.mc.dto.CreditCardRequestDTO;
import com.example.mc.serviceimpl.CreditCardRequestServiceImpl;
import com.example.mc.serviceimpl.ResponseDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 605750
 * Used to handle credit card request
 */

@RestController
@RequestMapping("/v1/user-service")
@Slf4j
public class UserServiceController {

	@Autowired
	private CreditCardRequestServiceImpl service;
	
	@Autowired
	public UserServiceController(CreditCardRequestServiceImpl service) {
		super();
		this.service = service;
	}
	
	
	/**
	 * @param requestDto
	 * @return
	 */
	@PostMapping(value="/applyCreditCard")
	public String applyCreditCard(@RequestBody @Valid CreditCardRequestDTO requestDto) {
		log.info("Applying credit card");
		return service.saveCreditCardRequest(requestDto);
	}
	
	/**
	 * retrieveDetails
	 * @param requestId
	 * @return Response
	 */
	@GetMapping(value="/retrieveCreditCardDetails/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseDetails<Object> retrieveDetails (@PathVariable (name="requestId",required = true) String requestId) {
		log.info("Retrieving details for creditcard details using {}",requestId);
		return service.getCreditCardDetails(requestId);
		
	}
	/**
	 * updateRequest
	 * @param CreditCardConfirmation
	 * @return Response
	 */
	@PostMapping("/updateCreditCardRequest")
	public void updateRequest(@RequestBody CreditCardConfirmation creditCardConfirmation) {
		log.info("Updating request for the request Id {} ",creditCardConfirmation.getRequestId());
		service.updateCreditCardRequest(creditCardConfirmation);
		
	}
}
