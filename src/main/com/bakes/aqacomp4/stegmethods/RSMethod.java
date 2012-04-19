/**
 * 
 */
package com.bakes.aqacomp4.stegmethods;

import java.util.Arrays;

import com.bakes.aqacomp4.Colour;
import com.bakes.aqacomp4.imagetools.Image;
import com.bakes.aqacomp4.imagetools.ImageTooSmallException;

/**
 * @author bakes
 *
 */
public class RSMethod implements StegMethod {
	static final int GROUPSIZE = 3;
	
	@Override
	public double testImage(Image image) throws ImageTooSmallException {
		if (image.getWidth() < GROUPSIZE)
		{
			throw new ImageTooSmallException();
		}
		int resultCount = 0;
		double result = 0;
		int[][] counts = new int[3][9];
		for (Colour q : Colour.values())
		{
			for (int i = 0; i < image.getHeight(); i++)
			{
				for (int j = 0; j <= image.getWidth()-GROUPSIZE; j+=GROUPSIZE)
				{
					int[] group = new int[GROUPSIZE];
					for (int k = 0; k < group.length; k++)
					{
						group[k] = image.getPixel(i, j+k, q);
					}
					int t = calculateNoise(mask(group, 1));
					if (calculateNoise(group) < t)
					{
						counts[q.ordinal()][0]++;
					}
					else if (calculateNoise(group) == t)
					{
						counts[q.ordinal()][2]++;
					}
					else
					{
						counts[q.ordinal()][1]++;
					}
					t = calculateNoise(mask(group, -1));
					if (calculateNoise(group) < t)
					{
						counts[q.ordinal()][3]++;
					}
					else if (calculateNoise(group) == t)
					{
						counts[q.ordinal()][5]++;
					}
					else
					{
						counts[q.ordinal()][4]++;
					}
					
					t = calculateNoise(flipSLSB(group));
					
					if (calculateNoise(group) < t)
					{
						counts[q.ordinal()][6]++;
					}
					else if (calculateNoise(group) == t)
					{
						counts[q.ordinal()][8]++;
					}
					else
					{
						counts[q.ordinal()][7]++;
					}
				}
			}
			
			final int numGroups = counts[q.ordinal()][1] + counts[q.ordinal()][2] + counts[q.ordinal()][3];

			double R1Left = (double) (counts[q.ordinal()][0]/(numGroups+0.0));
			double S1Left = (double) (counts[q.ordinal()][1]/(numGroups+0.0));
			double Rm1Left = (double) (counts[q.ordinal()][3]/(numGroups+0.0));
			double Sm1Left = (double) (counts[q.ordinal()][4]/(numGroups+0.0));
			
			double R1Right = S1Left;
			double S1Right = R1Left;
			double Rm1Right = (double) (counts[q.ordinal()][6]/(numGroups+0.0));
			double Sm1Right = (double) (counts[q.ordinal()][7]/(numGroups+0.0));
						
			double d0 = R1Left-S1Left;
			double dm0 = Rm1Left-Sm1Left;
			double d1 = R1Right-S1Right;
			double dm1 = Rm1Right-Sm1Right;
			
			double z = (d0-dm0)/(-dm0+dm1+d1+(3*d0));

			resultCount++;
			result += z/(z-0.5);
		}
		double mean = result / resultCount;
		
		if (mean > 0.1)
		{
			return 1;
		}
		else if (mean > 0.05)
		{
			return 0.5;
		}
		else if (mean < -0.05)
		{
			return 0.5;
		}
		else if (mean < -0.1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
	private int calculateNoise(int[] x)
	{
		int sum = 0;
		for (int i = 0; i<x.length-1;i++)
		{
			sum += Math.abs((255 & x[i+1])-(255 & x[i]));
		}
		return sum;
	}
	
	private int[] mask(int[] x, int mask)
	{
		int[] y = Arrays.copyOf(x, x.length);
		for (int i = 0; i < y.length; i++)
		{
			if (mask ==1)
			{
				y[i] = (byte) (y[i]^1);
			}
			else if (mask ==-1)
			{
				y[i] = (byte)(((y[i]-1)^1) +1);
			}
		}
		return y;
	}
	
	private int[] flipSLSB(int[] x)
	{
		int[] y = Arrays.copyOf(x, x.length);
		for (int i = 0; i < y.length; i++)
		{
			y[i] += (y[i]%2==0)?2:-2;
		}
		return y;
	}
}
