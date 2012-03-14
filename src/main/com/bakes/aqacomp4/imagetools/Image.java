/**
 * 
 */
package com.bakes.aqacomp4.imagetools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.bakes.aqacomp4.Colour;

/**
 * Class for the storage of images while they are being used.
 * 
 * class Image provides basic methods for use with accessing stored bitmap image data.
 *
 *@author bakes
 */

public class Image {
	private final int width;
	private final int height;
	private final int size;
	
	// imageData[xOffset][yOfffset][colour]
	private final byte[][][] imageData;
	
	
	/**
	 * Loads a supported image file (for the purposes of StegFinder, supported image files are 24-bit .bmp and .png files).
	 * @param fileName The path to the image file. Assumed to be a valid path on the file system, so must be pre-validated.
	 */
	public Image(String fileName) {
		BufferedImage image = openImage(fileName);
		byte[][][] result;
		width = image.getWidth();
		height = image.getHeight();
		result = new byte[height][width][3];
			
		for (int i = 0; i < height; i++)
		{
			for (int j = 0; j < width; j++)
			{
				int pixel = image.getRGB(j, i);
				int red = (pixel & 0x00ff0000) >> 16;
				int green = (pixel & 0x0000ff00) >> 8;
				int blue = (pixel & 0x000000ff);
				result[i][j][0] = (byte) red;
				result[i][j][1] = (byte) green;
				result[i][j][2] = (byte) blue;
			}
		}
		this.imageData = result;
		size = height*width;
	}
	

	/**
	 * Returns the value of a single pixel in the stored image.
	 * @param yOffset The y-coordinate of the pixel that is being accessed. 0 is considered to be the top of the image.
	 * @param xOffset The x-coordinate of the pixel that is being accessed. 0 is considered to be the left of the image.
	 * @param colour The colour channel that is to be accessed.
	 * @return
	 * @throws ImageTooSmallException
	 * @throws IllegalArgumentException
	 */
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
	
	/**
	 * @return number of pixels in image.
	 */
	public int getSize()
	{
		return size;
	}
	
	/**
	 * @return width of image (in pixels)
	 */
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * @return height of image (in pixels)
	 */
	public int getHeight()
	{
		return height;
	}
	
	
	private BufferedImage openImage(String fileName)
	{
		File file = new File(fileName);
		try {
			BufferedImage image = ImageIO.read(file);
			return image;
		} catch (IOException e){
			return null;
		}
	}

}
