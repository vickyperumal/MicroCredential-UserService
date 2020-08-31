package com.example.mc.exception;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = -725505072219963988L;

	public UserServiceException() {

	}

	public UserServiceException(String message) {
		super(message);
	}

	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
