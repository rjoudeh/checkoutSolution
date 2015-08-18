package com.checkout.errorHandling;

/**
 * For Handling undefined products
 * @author Rania
 *
 */
public class UndefinedProductException extends Exception {

	/**
	 * 
	 * @param message
	 */
	public UndefinedProductException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
