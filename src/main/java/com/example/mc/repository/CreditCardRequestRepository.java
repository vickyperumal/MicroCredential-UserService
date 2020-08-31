package com.example.mc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.mc.entity.CreditCardRequestEntity;

@Repository
public interface CreditCardRequestRepository extends JpaRepository<CreditCardRequestEntity,String>{
	
	Optional<CreditCardRequestEntity> findByRequestId(String requestId);
	

}
