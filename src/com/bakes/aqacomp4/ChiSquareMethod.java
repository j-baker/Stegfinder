/**
 * 
 */
package com.bakes.aqacomp4;


/**
 * @author bakes
 *
 */
public class ChiSquareMethod implements StegMethod {
	private Image image = null;
	private int size;
	private double[] result = new double[3];
	private boolean executed = false;

	/**
	 * 
	 */
	public ChiSquareMethod() {
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
	public void testImage() {
		for (int q = 0; q < 3; q++)
		{
			int[] bins = new int[256];
			for (int i = 0; i < image.getHeight(); i++)
			{
				for (int j = 0; j < image.getWidth(); j++)
				{
					bins[255 & image.getPixel(i, j, q)]++;
				}
			}
			double chiSquare = 0;
			for (int i = 0; i < 128; i++)
			{
				chiSquare += Math.pow((bins[2*i] - bins[2*i+1])/2, 2)/((bins[2*i] + bins[2*i +1])/2);
			}
			// TODO More accurate critical values.
			if (chiSquare < 80)
			{
				result[q] = 1;
			}
			else if (chiSquare < 180)
			{
				result[q] = 0.75;
			}
			else
			{
				result[q] = 0;
			}
			executed = true;
		}
	}

}
