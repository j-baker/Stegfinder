package com.bakes.aqacomp4.stegmethods;



/***
 * Dispatcher for the methods of steganography.
 * 
 * Each method of steganography is a generic StegMethod. Whilst this means that software that calls one StegMethod can call all StegMethods, in Java methods cannot be referenced.
 * I thus use a dispatcher. All present methods of steganography are hard-coded into this file. Adding a new method of steganography requires the addition of only two lines.
 * Running the method getMethod() returns the appropriate StegMethod, ready for use.
 * 
 *
 *@author bakes
 */

public enum StegMethods {
	RS("RS"), CHI_SQUARE("Chi-Square"), SPAM("SPAM");
	
	private String type;
	
	StegMethods(String name)
	{
		type = name;
	}
	
	/**
	 * @return The StegMethod linked to the chosen entry in the enum.
	 */
	public StegMethod getMethod()
	{
		switch(this)
		{
		case RS:
			return new RSMethod();
		case CHI_SQUARE:
			return new ChiSquareMethod();
		case SPAM:
			return new SPAMMethod();
		}
		// TODO It won't ever get to this part, due to structure of enum. Inserted in order to satisfy compiler!
		return null;
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString()
	{
		return type;
	}
	// TODO One less data dependency.
}
