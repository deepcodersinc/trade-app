package com.dci.bot.exception;

public class ApplicationException extends Exception {

	private static final long serialVersionUID = 3658828385623293723L;

	private String errorCode;
	private String developerMessage;

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	
	public ApplicationException(String message, String developerMessage, String errorCode) {
		super(message);
		this.errorCode = errorCode;
		this.developerMessage = developerMessage;
	}	

	public String getErrorCode() {
		return this.errorCode;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	
}
