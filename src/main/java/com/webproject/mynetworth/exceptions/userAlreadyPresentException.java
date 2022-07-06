package com.webproject.mynetworth.exceptions;

public class userAlreadyPresentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1346338204482738054L;

	public userAlreadyPresentException(String exceptionMessage) {
		super(exceptionMessage);
	}

}