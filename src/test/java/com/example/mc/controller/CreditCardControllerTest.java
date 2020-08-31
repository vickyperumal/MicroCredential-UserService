package com.example.mc.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.mc.dto.CreditCardRequestDTO;
import com.example.mc.serviceimpl.CreditCardRequestServiceImpl;
import com.example.mc.serviceimpl.ResponseDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
@ExtendWith(SpringExtension.class)
@SuppressWarnings({"rawtypes", "unchecked"})
class CreditCardControllerTest {

private MockMvc mock;
	
	private ObjectMapper objectMapper;
	
	@Mock
	private CreditCardRequestServiceImpl service;
	
	private CreditCardRequestDTO cardRequest;
	private ResponseDetails responseDetails;
	@BeforeEach
	void setUp() throws Exception {
		this.mock = MockMvcBuilders.standaloneSetup(new UserServiceController(service)).build();
		objectMapper = new ObjectMapper();
		
		cardRequest = new CreditCardRequestDTO();
		cardRequest.setAddress(Collections.singleton(null));
		cardRequest.setMobileNumber("7845622546");
		cardRequest.setAnnualIncome(78345.00);
		cardRequest.setPanCard("ABC1234XYZ");
	}
	

	@Test
	void testApplyCreditCard() throws Exception {
		cardRequest.setEmailId("abc@gmail.com");
		when(service.saveCreditCardRequest(Mockito.any(CreditCardRequestDTO.class))).thenReturn("request1");
		MvcResult result = this.mock.perform(post("/user-service/applyCreditCard")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cardRequest)))
				.andExpect(status().isOk())
				.andReturn();
		verify(service, times(1)).saveCreditCardRequest(Mockito.any(CreditCardRequestDTO.class));
	}
	
	@Test
	void testApplyCreditCardNegativeTest() throws Exception {
		this.mock.perform(post("/user-service/applyCreditCard")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cardRequest)))
				.andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	void getCreditCardDetailsbyRequestID() throws Exception {
		cardRequest.setEmailId("abc@gmail.com");
		responseDetails = ResponseDetails.builder().responseBody(cardRequest).responseStatus(200).build();
		when(service.getCreditCardDetails(Mockito.anyString())).thenReturn(responseDetails);
		MvcResult result = this.mock.perform(get("/user-service/retrieveCreditCardDetails/123456"))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse(), "Get request response is null");
		ResponseDetails<CreditCardRequestDTO> actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseDetails.class);
		assertEquals(responseDetails.getResponseStatus(), actualResponse.getResponseStatus(), "Response status not matched");
	}
	
	@Test
	void getCreditCardDetailsbyRequestIdNegativeTest() throws Exception {
		responseDetails = ResponseDetails.builder().responseBody("No Records").responseStatus(500).build();
		when(service.getCreditCardDetails(Mockito.anyString())).thenReturn(responseDetails);
		MvcResult result = this.mock.perform(get("/user-service/retrieveCreditCardDetails/123456"))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(result.getResponse(), "Get request response is null");
		ResponseDetails<CreditCardRequestDTO> actualResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ResponseDetails.class);
		assertEquals(responseDetails.getResponseStatus(), actualResponse.getResponseStatus(), "Response status not matched");
		assertEquals(responseDetails.getResponseBody(), actualResponse.getResponseBody(), "Response status not matched");
	}
}
