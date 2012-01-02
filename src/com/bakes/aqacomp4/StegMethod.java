package com.bakes.aqacomp4;

public interface StegMethod {
	String name = "";
	String infoLevel1 = "";
	String infoLevel2 = "";
	String infoLevel3 = "";
	
	public void testImage(Image i) throws ImageNotTestedException;
	
	public double getNumericalResult() throws ImageNotTestedException;
	
	public String getTextResult() throws ImageNotTestedException;
	
	

}
