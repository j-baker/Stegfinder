package com.bakes.aqacomp4.stegmethods;

import com.bakes.aqacomp4.imagetools.Image;
import com.bakes.aqacomp4.imagetools.ImageTooSmallException;

public interface StegMethod {
	
	/**
	 * Runs the steganalytical test on the loaded image.
	 * @throws ImageTooSmallException if the image file is too small to be tested.
	 */
	public double testImage(Image i) throws ImageTooSmallException, Exception;

}
