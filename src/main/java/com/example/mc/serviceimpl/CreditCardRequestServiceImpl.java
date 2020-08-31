package com.example.mc.serviceimpl;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.example.mc.dto.CreditCardApprovalModel;
import com.example.mc.dto.CreditCardConfirmation;
import com.example.mc.dto.CreditCardRequestDTO;
import com.example.mc.entity.AddressEntity;
import com.example.mc.entity.CreditCardRequestEntity;
import com.example.mc.exception.UserServiceException;
import com.example.mc.repository.CreditCardRequestRepository;
import com.example.mc.service.CreditCardService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 605750
 *
 */
@Component
@Slf4j
public class CreditCardRequestServiceImpl implements CreditCardService {

	@Autowired
	private CreditCardRequestRepository creditCardRequestRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${approvalservice.url}")
	private String approvalServiceUrl;

	private static final String ERROR_RESPONSE = "Application under maintainence. Please try after sometime.Thanks for your patience";

	private static final String APPROVED = "APPROVED";

	private static final String EMAIL_SUBJECT = "Acknowlegement -Credit Card";

	private static final String EMAIL_BODY = "Dear Customer  We have recieved your credit card Request";

	/**
	 * saves credit card in DB
	 *
	 * Takes input as CreditCardRequestDTO
	 */
	@HystrixCommand(fallbackMethod = "enableFalseResponse")
	@Override
	public String saveCreditCardRequest(CreditCardRequestDTO request) {
		String requestId = null;
		try {
			CreditCardRequestEntity entity = modelMapper.map(request, CreditCardRequestEntity.class);
			request.getAddress().forEach(address -> entity.addAddress(modelMapper.map(address, AddressEntity.class)));
			entity.setRequestStatus("RECIEVED");
			CreditCardRequestEntity creditCardRequestEntity = creditCardRequestRepository.save(entity);
			requestId = creditCardRequestEntity.getRequestId();
			log.info("Generated Request ID {} for the customer {}", creditCardRequestEntity.getRequestId(),
					request.getFullName());
			CreditCardApprovalModel model = CreditCardApprovalModel.builder()
					.creditCardRequestId(creditCardRequestEntity.getRequestId()).status("APPROVAL_PENDING")
					.userEmailId(creditCardRequestEntity.getEmailId()).approverId("MANAGER").build();
			
			restTemplate.postForObject(approvalServiceUrl, model, String.class);
		} catch (UserServiceException use) {
			log.error("Error occured {}", use.getMessage());
			return "Unable to process the request";
		}
		return requestId;
	}

	/**
	 * used to fetch credit card details Takes input as RequestId
	 * @param requestId
	 */
	@Override
	public ResponseDetails<Object> getCreditCardDetails(String requestId) {
		CreditCardRequestDTO requestDTO = null;
		Optional<CreditCardRequestEntity> requestEntity = creditCardRequestRepository.findByRequestId(requestId);
		log.info("Fetching creditCardDetails");
		if (requestEntity.isPresent()) {
			requestDTO = modelMapper.map(requestEntity.get(), CreditCardRequestDTO.class);
			ResponseDetails.builder().responseBody(requestDTO).responseStatus(HttpStatus.OK.value()).build();
		}
		return ResponseDetails.builder().responseBody("No Records")
				.responseStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();

	}

	public String enableFalseResponse(CreditCardRequestDTO request) {
		log.info("Into false tolerance");
		if (request != null) {
			return ERROR_RESPONSE;
		}
		return null;

	}

	/**
	 * updates credit card status
	 *
	 * @param creditCardConfirmation
	 */
	@Override
	public void updateCreditCardRequest(CreditCardConfirmation creditCardConfirmation) {
       log.info("Updating request");
		if (creditCardConfirmation != null) {
			Optional<CreditCardRequestEntity> requestEntity = creditCardRequestRepository
					.findByRequestId(creditCardConfirmation.getRequestId());
			if (requestEntity.isPresent()) {
				CreditCardRequestEntity creditCardRequest = requestEntity.get();
				if (APPROVED.equalsIgnoreCase(creditCardConfirmation.getStatus()))
					creditCardRequest.setRequestStatus(APPROVED);
				else
					creditCardRequest.setRequestStatus("REJECTED");
				creditCardRequestRepository.save(creditCardRequest);
			}
			log.info("Final status of the credit card -> {}",creditCardConfirmation.getStatus());
		}

	}

	/**
	 * send notification to the customer
	 */
	void sendEmail() {
     log.info("Sending notification");
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("vickyperumalsvp@gmail.com");
		msg.setTo("vickyperumalsvp@gmail.com");
		msg.setSubject(EMAIL_SUBJECT);
		msg.setText(EMAIL_BODY);
		javaMailSender.send(msg);
		log.info("Notificaton sent");

	}
}
