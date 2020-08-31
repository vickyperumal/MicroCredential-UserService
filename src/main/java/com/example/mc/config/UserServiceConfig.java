package com.example.mc.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.mc.dto.CreditCardRequestDTO;
import com.example.mc.entity.CreditCardRequestEntity;

@Configuration
public class UserServiceConfig {

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper=new ModelMapper();
		
		mapper.getConfiguration().setAmbiguityIgnored(true);
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.createTypeMap(CreditCardRequestDTO.class,CreditCardRequestEntity.class)
		.addMappings(model->model.skip(CreditCardRequestEntity::setAddress));
		
		return mapper;
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	return builder.build();
}
}
