
package com.bakes.aqacomp4.imagetools;

/**
 * @author bakes
 * If the image is too small to be tested, or the specified pixel is out of range, this exception is thrown.
 */
@SuppressWarnings("serial")
public class ImageTooSmallException extends Exception {

	public ImageTooSmallException() {
	}
	
	public ImageTooSmallException(String message) {
		super(message);
	}

	public ImageTooSmallException(Throwable cause) {
		super(cause);
	}


	public ImageTooSmallException(String message, Throwable cause) {
		super(message, cause);
	}

}
