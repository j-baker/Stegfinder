/**
 * 
 */
package com.bakes.aqacomp4;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author bakes
 *
 */
public class BasicImporter implements ImageImporter {

	/* (non-Javadoc)
	 * @see com.bakes.aqacomp4.ImageImporter#importImage(java.lang.String)
	 */
	@Override
	public Image importImage(String path) {
		
		Image i = new Image(loadImage(path),ImageTypes.BITMAP);
		return i;
	}

	private static byte[][][] loadImage(String fileName)
	{
		BufferedImage image = openImage(fileName);
		byte[][][] result;
		int width = image.getWidth();
		int height = image.getHeight();
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
		return result;
	}

	private static BufferedImage openImage(String fileName)
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
