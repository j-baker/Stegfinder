package com.bakes.aqacomp4.imagetools;

/**
 * @author bakes
 * If we attempt to access the result of the steganalysis before the steganalysis has been run, this exception is thrown.
 */
public class ImageNotTestedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5655102824543700278L;

	/**
	 * 
	 */
	public ImageNotTestedException() {
	}

	/**
	 * @param arg0
	 */
	public ImageNotTestedException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public ImageNotTestedException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ImageNotTestedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
