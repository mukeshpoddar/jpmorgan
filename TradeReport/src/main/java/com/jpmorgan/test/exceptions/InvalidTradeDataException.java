package com.jpmorgan.test.exceptions;

/**
 * Exception class representing a Invalid Trade Data
 * 
 * @author Mukesh
 *
 */
public class InvalidTradeDataException extends Exception {

	/**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a InvalidTradeDataException object
	 * 
	 * @param message
	 */
	public InvalidTradeDataException(String message) {
		super(message);
	}

}
