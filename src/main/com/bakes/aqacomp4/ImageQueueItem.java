/**
 * 
 */
package com.bakes.aqacomp4;

/**
 * @author bakes
 *
 */
public class ImageQueueItem {
	private String imagePath;
	private boolean hasBeenTested = false;
	private double[] numericalResult;
	private String resultAsString;
	
	// Information about which test to use
	StegMethods method;
	ImageTypes imageType;
	
	
	public ImageQueueItem(String imagePath, StegMethods method, ImageTypes type) {
		this.imagePath = imagePath;
		this.method = method;
		this.imageType = type;
	}
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	public void runMethod()
	{
		StegMethod method = this.method.getMethod();
		Image i = (new BasicImporter()).importImage(this.imagePath);
		method.loadImage(i);
		try {
			method.testImage();
			this.numericalResult = method.getNumericalResult();
			this.resultAsString = method.getTextResult();
			this.hasBeenTested = true;
		} catch (ImageTooSmallException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImageNotTestedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

}
