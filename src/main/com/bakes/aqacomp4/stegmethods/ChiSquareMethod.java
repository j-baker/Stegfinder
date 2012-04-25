/**
 * 
 */
package com.bakes.aqacomp4.stegmethods;

import com.bakes.aqacomp4.Colour;
import com.bakes.aqacomp4.imagetools.Image;
import com.bakes.aqacomp4.imagetools.ImageTooSmallException;


/**
 * @author bakes
 *
 */
public class ChiSquareMethod implements StegMethod {

	public ChiSquareMethod() {
	}
	
	@Override
	public double testImage(Image image) {
		int numResults = 0;
		double result = 0;
		for (Colour q : Colour.values())
		{
			int[] bins = new int[256];
			for (int i = 0; i < image.getHeight(); i++)
			{
				for (int j = 0; j < image.getWidth(); j++)
				{
					try {
						bins[image.getPixel(i, j, q)]++;
					} catch (ImageTooSmallException e) {
						// No need to do anything, since this should never happen.
						e.printStackTrace();
					}
				}
			}
			double chiSquare = 0;
			for (int i = 0; i < 128; i++)
			{
				chiSquare += Math.pow((bins[2*i] - bins[2*i+1])/2, 2)/((bins[2*i] + bins[2*i +1])/2);
			}
			numResults++;
			if (chiSquare < 80)
			{
				result += 1;
			}
			else if (chiSquare < 180)
			{
				result += 0.75;
			}
			else
			{
				result += 0;
			}
		}
		return result/numResults;
	}

}
