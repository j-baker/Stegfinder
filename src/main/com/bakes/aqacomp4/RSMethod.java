/**
 * 
 */
package com.bakes.aqacomp4;

import java.util.Arrays;

/**
 * @author bakes
 *
 */
public class RSMethod implements StegMethod {
	private Image image = null;
	private int size;
	private double[] result = new double[3];
	private boolean executed = false;
	static final int GROUPSIZE = 3;

	/**
	 * 
	 */
	public RSMethod() {
		// TODO Auto-generated constructor stub
	}
	
	public double getTimeEstimate(double benchmark)
	{
		return 1/size;
	}

	@Override
	public double[] getNumericalResult() throws ImageNotTestedException {
		if (!executed)
		{
			throw new ImageNotTestedException();
		}
		return result;
	}

	@Override
	public String getTextResult() throws ImageNotTestedException {
		if (!executed)
		{
			throw new ImageNotTestedException();
		}
		// TODO Auto-generated method stub
		return ""+result;
	}

	@Override
	public void loadImage(Image i) {
		image = i;
		size = image.getSize();
	}

	@Override
	public void testImage() throws ImageTooSmallException {
		if (image.getWidth() < GROUPSIZE)
		{
			throw new ImageTooSmallException();
		}
		int[][] counts = new int[3][9];
		for (int q = 0; q < 3; q++)
		{
			for (int i = 0; i < image.getHeight(); i++)
			{
				for (int j = 0; j <= image.getWidth()-GROUPSIZE; j+=GROUPSIZE)
				{
					int[] group = new int[GROUPSIZE];
					for (int k = 0; k < group.length; k++)
					{
						group[k] = image.getPixel(j+k, i, q);
					}
					int t = calculateNoise(mask(group, 1));
					if (calculateNoise(group) < t)
					{
						counts[q][0]++;
					}
					else if (calculateNoise(group) == t)
					{
						counts[q][2]++;
					}
					else
					{
						counts[q][1]++;
					}
					t = calculateNoise(mask(group, -1));
					if (calculateNoise(group) < t)
					{
						counts[q][3]++;
					}
					else if (calculateNoise(group) == t)
					{
						counts[q][5]++;
					}
					else
					{
						counts[q][4]++;
					}
					
					t = calculateNoise(flipSLSB(group));
					
					if (calculateNoise(group) < t)
					{
						counts[q][6]++;
					}
					else if (calculateNoise(group) == t)
					{
						counts[q][8]++;
					}
					else
					{
						counts[q][7]++;
					}
				}
			}
			
			final int numGroups = counts[q][1] + counts[q][2] + counts[q][3];

			double R1Left = (double) (counts[q][0]/(numGroups+0.0));
			double S1Left = (double) (counts[q][1]/(numGroups+0.0));
			double Rm1Left = (double) (counts[q][3]/(numGroups+0.0));
			double Sm1Left = (double) (counts[q][4]/(numGroups+0.0));
			
			double R1Right = S1Left;
			double S1Right = R1Left;
			double Rm1Right = (double) (counts[q][6]/(numGroups+0.0));
			double Sm1Right = (double) (counts[q][7]/(numGroups+0.0));
						
			double d0 = R1Left-S1Left;
			double dm0 = Rm1Left-Sm1Left;
			double d1 = R1Right-S1Right;
			double dm1 = Rm1Right-Sm1Right;
			
			double z = (d0-dm0)/(-dm0+dm1+d1+(3*d0));

			result[q] = z/(z-0.5);
		}
		executed = true;
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
