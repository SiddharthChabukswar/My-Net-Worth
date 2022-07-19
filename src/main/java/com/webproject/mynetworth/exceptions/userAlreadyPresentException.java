package com.webproject.mynetworth.exceptions;

public class UserAlreadyPresentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1346338204482738054L;

	public UserAlreadyPresentException(String exceptionMessage) {
		super(exceptionMessage);
	}

}