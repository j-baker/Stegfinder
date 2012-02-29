package com.bakes.aqacomp4.stegmethods;

import com.bakes.aqacomp4.gui.OptionsWindow;
import com.bakes.aqacomp4.gui.SPAMOptionsWindow;

public enum StegMethods {
	RS, CHI_SQUARE, SPAM, PAIRS, SPAM_CALIBRATED, /*NEW_METHOD*/;
	
	
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
		case PAIRS:
			return new RSMethod();
		case SPAM_CALIBRATED:
			return new RSMethod();
		/*case NEW_METHOD:
			return new RSMethod();*/
		}
		// TODO It won't ever get to this part, due to structure of enum. Inserted in order to satisfy compiler!
		return null;
	}
	
	@Override
	public String toString()
	{
		switch(this)
		{
		case RS:
			return "RS";
		case CHI_SQUARE:
			return "Chi-Square";
		case SPAM:
			return "SPAM";
		case PAIRS:
			return "Pairs";
		case SPAM_CALIBRATED:
			return "SPAM Calibrated";
		/*case NEW_METHOD:
			return "New Method";*/
		}
		return null;
	}
	
	public OptionsWindow getOptionsWindow()
	{
		switch(this)
		{
		case RS:
			return null;
		case CHI_SQUARE:
			return null;
		case SPAM:
			return new SPAMOptionsWindow();
		}
		return null;
	}
}
