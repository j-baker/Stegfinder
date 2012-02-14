/**
 * 
 */
package com.bakes.aqacomp4.imagetools;

import com.bakes.aqacomp4.Colour;

//TODO I wanted to potentially end up using JPEG images so I didn't use the Java default image.
/**
 * @author bakes
 *
 */
public class Image {
	private final int width;
	private final int height;
	private final int size;
	private final byte[][][] imageData;
	private final ImageTypes type;

	/**
	 * 
	 */
	public Image(byte[][][] imageData, ImageTypes type) {
		this.type = type;
		this.imageData = imageData;
		height = imageData.length;
		width = imageData[0].length;
		size = height*width;
	}
	

	public int getPixel(int yOffset, int xOffset, Colour colour) throws ImageTooSmallException, IllegalArgumentException
	{
		// TODO Throw Exception if image too small.
		if (yOffset >= height || xOffset >= width)
		{
			throw new ImageTooSmallException();
		}
		// TODO Throw exception if index is less than zero.
		else if (yOffset < 0 || xOffset < 0)
		{
			throw new IllegalArgumentException();
		}
		// TODO Remove the image's sign. Byte is a signed datatype in java.
		return 0xFF & imageData[yOffset][xOffset][colour.ordinal()];
	}
	
	public int getSize()
	{
		return size;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public ImageTypes getType()
	{
		return type;
	}

}