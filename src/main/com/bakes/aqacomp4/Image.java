/**
 * 
 */
package com.bakes.aqacomp4;

/**
 * @author bakes
 *
 */
public class Image {
	private int width;
	private int height;
	private int size;
	private byte[][][] imageData;
	private String type;

	/**
	 * 
	 */
	public Image(byte[][][] imageData, String type) {
		this.type = type;
		this.imageData = imageData;
		height = imageData.length;
		width = imageData[0].length;
		size = height*width;
	}
	

	public int getPixel(int xOffset, int yOffset, int colour)
	{
		/* Remove the sign. */
		return 255 & imageData[xOffset][yOffset][colour];
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
	
	public String getType()
	{
		return type;
	}

}
