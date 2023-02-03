package com.mws.phoenix.web.exceptions;

/**
 * Represents an exception occurring when trying to locate a resource in the webapp.
 * This Exception is thrown when an Action cannot find the file requested in the 
 * user's home folder or the default folder.
 *
 * @author Ed Webb
 */
public class ResourcePathException extends ParameterisableException {

	/**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;

	public ResourcePathException() {
		super();
	}

	public ResourcePathException(String s) {
		super(s);
	}

	public ResourcePathException(String s, String arg) {
		super(s, arg);
	}

	public ResourcePathException(String s, String arg0, String arg1) {
		super(s, arg0, arg1);
	}

	public ResourcePathException(String s, String[] args) {
		super(s, args);
	}

}
