package com.example.mc.serviceimpl;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@SuppressWarnings("unchecked")
public class ResponseDetails<T> {

	T responseBody;
	int responseStatus;

	public ResponseDetails() {
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
		ResponseDetails<T> other = (ResponseDetails<T>) obj;
		return Objects.equals(responseBody, other.responseBody) && responseStatus == other.responseStatus;
	}

	@Override
	public int hashCode() {
		return Objects.hash(responseBody, responseStatus);
	}
}
