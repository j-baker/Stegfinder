package com.bakes.aqacomp4;

public enum StegMethods {
	RS, CHI_SQUARE, SPAM;
	
	
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
		default:
			// It won't ever get to this part, due to structure of enum. Inserted in order to satisfy compiler!
			return null;
		}
	}
}
