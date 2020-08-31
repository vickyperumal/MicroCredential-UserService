package com.example.mc.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name="CUSTOMER_ADDRESS",schema="MCDBO")
@Data
@AllArgsConstructor
public class AddressEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long addressId;
	private String type;
	private String streetdetails;
	private String city;
	private String state;
	private Long pincode;
	@ManyToOne
	@JoinColumn(name="request_id",referencedColumnName = "request_id")
	@ToString.Exclude
	private CreditCardRequestEntity request;
	
	public AddressEntity() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEntity other = (AddressEntity) obj;
		return Objects.equals(addressId, other.addressId) && Objects.equals(city, other.city)
				&& Objects.equals(pincode, other.pincode) && Objects.equals(state, other.state)
				&& Objects.equals(streetdetails, other.streetdetails) && Objects.equals(type, other.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(addressId, city, pincode, state, streetdetails, type);
	}
	
	
	

}
