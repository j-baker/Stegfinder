
package com.bakes.aqacomp4.imagetools;

/**
 * @author bakes
 * If the image is too small to be tested, or the specified pixel is out of range, this exception is thrown.
 */
@SuppressWarnings("serial")
public class ImageTooSmallException extends Exception {

	public ImageTooSmallException() {
		// TODO Auto-generated constructor stub
	}
	
	public ImageTooSmallException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ImageTooSmallException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}


	public ImageTooSmallException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
