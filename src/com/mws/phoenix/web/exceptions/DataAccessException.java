package com.mws.phoenix.web.exceptions;

/**
 * Represents an exception occuring in the Data Access layer of the web application.
 * This Exception usually represents an SQLException but may also be thrown if data
 * written to or read from the database is incorrect or malformed.
 * This is the only exception that is thrown from the XxxFactory classes
 *
 * @author Ed Webb
 */
public class DataAccessException extends ParameterisableException {

	/**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String s) {
		super(s);
	}

	public DataAccessException(String s, String arg) {
		super(s, arg);
	}

	public DataAccessException(String s, String arg0, String arg1) {
		super(s, arg0, arg1);
	}

	public DataAccessException(String s, String[] args) {
		super(s, args);
	}

}
