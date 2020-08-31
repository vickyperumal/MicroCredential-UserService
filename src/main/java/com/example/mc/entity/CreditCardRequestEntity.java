package com.example.mc.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.example.mc.AttributeEncryptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CREDIT_CARD_REQUEST",schema="MCDBO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardRequestEntity {
	
	@Id
	@Column(name="request_id")
	@GeneratedValue(generator ="system-uuid")
	@GenericGenerator(name="system-uuid",strategy = "uuid")
	private String requestId;
	private String emailId;
	private String mobileNumber;
	private String fullName;
	private LocalDate dateOfBirth;
	@Convert(converter = AttributeEncryptor.class)
	private String panCard;
	private String profession;
	private String requestStatus;
	private Double annualIncome;
	private String approver;
	@OneToMany(mappedBy = "request",cascade = CascadeType.ALL)
	private Set<AddressEntity> address=new HashSet<>();
	
	public void addAddress(AddressEntity addressEntity) {
		address.add(addressEntity);
		addressEntity.setRequest(this);

	}
	
	
	
	

}


