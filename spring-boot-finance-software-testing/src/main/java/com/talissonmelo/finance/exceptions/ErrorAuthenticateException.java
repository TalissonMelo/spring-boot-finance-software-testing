package com.talissonmelo.finance.exceptions;

public class ErrorAuthenticateException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ErrorAuthenticateException(String msg) {
		super(msg);
	}

}
