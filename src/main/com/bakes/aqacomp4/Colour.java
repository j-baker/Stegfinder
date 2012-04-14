package com.bakes.aqacomp4;
/**
 * The different colours that are supported. Stegfinder supports only 24-bit
 * bitmap files. In the future, a second `GreyscaleColor' enum could be produced,
 * allowing for black and white images. At present, they are simply converted to
 * 24-bit bitmap files before processing, which makes the computations less efficient
 * for black and white files only.
 * @author bakes
 *
 */
public enum Colour {
	RED, GREEN, BLUE;
	
	/**
	 * 
	 * @return The number of colours represented in the image data.
	 */
	public static int length()
	{
		return values().length;
	}
}