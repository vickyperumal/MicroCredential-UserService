package com.example.mc.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashSet;
import java.util.Optional;

import org.apache.commons.lang.reflect.FieldUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import com.example.mc.dto.CreditCardApprovalModel;
import com.example.mc.dto.CreditCardConfirmation;
import com.example.mc.dto.CreditCardRequestDTO;
import com.example.mc.entity.CreditCardRequestEntity;
import com.example.mc.repository.CreditCardRequestRepository;
import com.example.mc.serviceimpl.CreditCardRequestServiceImpl;
import com.example.mc.serviceimpl.ResponseDetails;
@ExtendWith(SpringExtension.class)
class CreditCardServiceImplTest {
	
	@InjectMocks
	private CreditCardRequestServiceImpl service;
	
	@Mock
	private CreditCardRequestRepository creditCardRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	private CreditCardRequestEntity request;
	
	@Mock
	private RestTemplate restTemplate;
	
	private CreditCardRequestDTO requestDto;
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		FieldUtils.writeField(service, "approvalServiceUrl", "http://localhost:8082", true);
		request = new CreditCardRequestEntity();
		request.setRequestId("requsest1");
		request.setRequestStatus("RECIEVED");
		requestDto = new CreditCardRequestDTO();
		requestDto.setAddress(new HashSet<>());
	
	}
	@Test
	void testSaveCreditCardRequest() {
	
		when(modelMapper.map(Mockito.any(CreditCardRequestDTO.class),Mockito.eq(CreditCardRequestEntity.class))).thenReturn(request);
		when(creditCardRepository.save(Mockito.any(CreditCardRequestEntity.class))).thenReturn(request);
		when(restTemplate.postForObject(Mockito.anyString(),Mockito.any(CreditCardApprovalModel.class),Mockito.any())).thenReturn("");
		
		String response=service.saveCreditCardRequest(requestDto);
		
		assertNotNull(response, "RequestId is null");
		verify(creditCardRepository, times(1)).save(Mockito.any(CreditCardRequestEntity.class));
	}

	@Test
	void testGetCreditCardDetails() {
		when(creditCardRepository.findByRequestId(Mockito.anyString())).thenReturn(Optional.of(request));
		when(modelMapper.map(Mockito.any(CreditCardRequestEntity.class),Mockito.eq(CreditCardRequestDTO.class))).thenReturn(requestDto);
		ResponseDetails<Object> response = service.getCreditCardDetails("request1");
		assertNotNull(response.getResponseBody(), "ResponseBody is null");
		assertEquals(500, response.getResponseStatus(), "Response status not matched");
		verify(creditCardRepository, times(0)).findById(Mockito.anyString());
	}

	@Test
	void testUpdateCreditCardRequest() {
		CreditCardConfirmation confirmation=new CreditCardConfirmation();
		confirmation.setRequestId("request1");
		confirmation.setStatus("APPROVED");
		when(creditCardRepository.findByRequestId(Mockito.anyString())).thenReturn(Optional.of(request));
		 service.updateCreditCardRequest(confirmation);
		verify(creditCardRepository, times(0)).findById(Mockito.anyString());
	}

}
