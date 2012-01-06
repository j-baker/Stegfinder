package com.bakes.aqacomp4;

public interface StegMethod {
	String name = "";
	String infoLevel1 = "";
	String infoLevel2 = "";
	String infoLevel3 = "";
	
	/**
	 * Loads an image into the steganalysis engine.
	 * @param i The image that is to be loaded.
	 */
	public void loadImage(Image i);
	
	/**
	 * Runs the steganalytical test on the loaded image.
	 * @throws ImageTooSmallException if the image file is too small to be tested.
	 */
	public void testImage() throws ImageTooSmallException;
	
	/**
	 * @return the results of testing.
	 * @throws ImageNotTestedException if image has not been tested.
	 */
	public double[] getNumericalResult() throws ImageNotTestedException;
	
	/**
	 * @return The results of the test as a String, with the results provided in the English Language.
	 * @throws ImageNotTestedException
	 */
	public String getTextResult() throws ImageNotTestedException;
	
	

}
