/**
 * 
 */
package com.bakes.aqacomp4;

/**
 * @author bakes
 *
 */
public class TestQueueMember {
	String imagePath;
	boolean hasBeenTested = false;
	double numericalResult[];
	String resultAsString;
	
	public TestQueueMember(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	/**
	 * Get the result in numerical form.
	 * @return A 3-item double array, all items 0 <= x <= 1.
	 * @throws ImageNotTestedException 
	 */
	public double[] getNumericalResult() throws ImageNotTestedException
	{
		if (!hasBeenTested)
		{
			throw new ImageNotTestedException();
		}
		else
		{
			return numericalResult;
		}
	}
	
	public String getResultAsString() throws ImageNotTestedException
	{
		if (!hasBeenTested)
		{
			throw new ImageNotTestedException();
		}
		else
		{
			return resultAsString;
		}
	}
	
	public void setResult(double[] numericalResult, String resultAsString) throws ImageTestedException
	{
		if (hasBeenTested)
		{
			throw new ImageTestedException();
		}
		else
		{
			this.numericalResult = numericalResult;
			this.resultAsString = resultAsString;
			hasBeenTested = true;
		}
	}

}
