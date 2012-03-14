package com.bakes.aqacomp4.stegmethods;

import com.bakes.aqacomp4.imagetools.Image;
import com.bakes.aqacomp4.imagetools.ImageTooSmallException;

/**
 * StegMethods are used for performing Steganalysis on Images, passed as arguments.
 * Calling the method testImage with an appropriate argument will return a double
 * with a number close to one indicated steganography detected and zero indicating no steganography detected.
 * @author bakes
 *
 */
public interface StegMethod {
	
	/**
	 * Runs the steganalytical test on the loaded image.
	 * @throws ImageTooSmallException if the image file is too small to be tested.
	 */
	public double testImage(Image i) throws ImageTooSmallException;

}
