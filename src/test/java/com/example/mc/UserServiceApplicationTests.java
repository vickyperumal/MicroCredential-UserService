package com.example.mc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.example.mc.controller.UserServiceController;
import com.example.mc.service.CreditCardService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceApplicationTests {

	@Autowired
	private UserServiceController userServiceController;
	
	@Autowired
	private CreditCardService creditCardService;

	@Test
	void contextLoads() {
		assertNotNull(userServiceController, "Controller is null");
		assertNotNull(creditCardService, "Service is null");
	}

}
