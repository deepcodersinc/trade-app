package com.dci.bot.exception;

public class OrderException extends Exception {

	private static final long serialVersionUID = 7764614194599848470L;
		
	private String developerMessage;
	private String errorCode;
	private int httpErrorCode;
	
	public OrderException(String message) {
		super(message);
	}

	public OrderException(String message, String developerMessage, String errorCode, int httpErrorCode) {
		super(message);
		this.errorCode = errorCode;
		this.developerMessage = developerMessage;
		this.httpErrorCode = httpErrorCode;
	}
	
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public int getHttpErrorCode() {
		return httpErrorCode;
	}
	
		
	
}
