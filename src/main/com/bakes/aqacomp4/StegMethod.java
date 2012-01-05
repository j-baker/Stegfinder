package com.bakes.aqacomp4;

public interface StegMethod {
	String name = "";
	String infoLevel1 = "";
	String infoLevel2 = "";
	String infoLevel3 = "";
	
	public void loadImage(Image i);
	
	public void testImage() throws ImageTooSmallException;
	
	public double[] getNumericalResult() throws ImageNotTestedException;
	
	public String getTextResult() throws ImageNotTestedException;
	
	

}
