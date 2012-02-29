/**
 * 
 */
package com.bakes.aqacomp4.imagetools;

import com.bakes.aqacomp4.stegmethods.StegMethod;
import com.bakes.aqacomp4.stegmethods.StegMethods;

/**
 * @author bakes
 *
 */
public class ImageQueueItem {
	private String imagePath;
	private boolean hasBeenTested = false;
	private double result;
	
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
	
	public ImageTypes getImageType()
	{
		return imageType;
	}
	
	public StegMethods getStegMethod()
	{
		return method;
	}
	
	public boolean runMethod()
	{
		if (!hasBeenTested)
		{
			StegMethod method = this.method.getMethod();
			Image i = (new BasicImporter()).importImage(this.imagePath);
			try {
				this.result = method.testImage(i);
				this.hasBeenTested = true;
			} catch (ImageTooSmallException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ImageNotTestedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			return true;
		}
		else
		{
			return false;
		}

		
	}
	
	/**
	 * Get the result in numerical form.
	 * @return A 3-item double array, all items 0 <= x <= 1.
	 * @throws ImageNotTestedException 
	 */
	public double getResult() throws ImageNotTestedException
	{
		if (!hasBeenTested)
		{
			throw new ImageNotTestedException();
		}
		else
		{
			return result;
		}
	}

}
