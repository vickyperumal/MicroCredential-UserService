package com.example.mc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardApprovalModel {

	private String approvalRequestId;
	private String creditCardRequestId;
	private String status;
	private String userEmailId;
	private String approverId;
}
