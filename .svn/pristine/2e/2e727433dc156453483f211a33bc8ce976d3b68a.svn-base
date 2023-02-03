package com.mws.phoenix.web.exceptions;

/**
 * Exception that takes a key into a Resource bundle and a number of
 * Parameters to create the message property. This must be subclassed
 * into a real Exception class
 *
 * @author Ed Webb
 */
public abstract class ParameterisableException extends Exception {

	/**
	 * The version ID
	 */
	private static final long serialVersionUID = 1L;
	
	protected String[] args;

	/**
	 * Creates a new Exception.
	 * 
	 * @param s a key into the Struts application properties file
	 */
	public ParameterisableException() {
		super();
	}

	/**
	 * Creates a new Exception.
	 * 
	 * @param s a key into the Struts application properties file
	 */
	public ParameterisableException(String s) {
		super(s);
	}

	/**
	 * Creates a new Exception
	 * 
	 * @param s a key into the Struts application properties file
	 * @param arg the String to replace the {0} place holder with
	 */
	public ParameterisableException(String s, String arg) {
		super(s);
		args = new String[1];
		args[0] = arg;
	}

	/**
	 * Creates a new Exception
	 * 
	 * @param s a key into the Struts application properties file
	 * @param arg0 the String to replace the {0} place holder with
	 * @param arg1 the String to replace the {1} place holder with
	 */
	public ParameterisableException(String s, String arg0, String arg1) {
		super(s);
		args = new String[2];
		args[0] = arg0;
		args[1] = arg1;
	}

	/**
	 * Creates a new Exception
	 * 
	 * @param s a key into the Struts application properties file
	 * @param args an array of Strings to replace the {n} place holders with
	 */
	public ParameterisableException(String s, String[] args) {
		super(s);
		this.args = args;
	}

	/**
	 * Returns the length of the Arguments array
	 * 
	 * @return the number of Strings in the arguments array
	 */
	public int getArgCount() {
		return args.length;
	}

	/**
	 * Returns the string at the specified position in the array
	 * 
	 * @param index the index in the array of the argument to return
	 * @return the String at the position in the Arguments array specified by index
	 */
	public String getArgument(int index) {
		if (null == args) {
			return "";
		}
		if (index >= 0 && index <= args.length-1) {
			return args[index];
		} else {
			return "";
		}
	}

	/**
	 * Returns the Arguments array
	 * 
	 * @return the array of String arguments
	 */
	public String[] getArguments() {
		return args;
	}

}
