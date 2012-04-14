package com.bakes.aqacomp4.imagetools;

import com.bakes.aqacomp4.stegmethods.StegMethod;
import com.bakes.aqacomp4.stegmethods.StegMethods;

/**
 * @author bakes
 * The queue item. Contains the path to the image that is to be tested, and information regarding which method of steganalysis should be used. If said method has been run correctly, the result of steganalysis can also be acccessed.
 */
public class ImageRecord {
	private String imagePath;
	private boolean hasBeenTested = false;
	private double result;
	
	// Information about which test to use
	StegMethods method;
	
	
	public ImageRecord(String imagePath, StegMethods method) {
		this.imagePath = imagePath;
		this.method = method;
	}
	
	/**
	 * Get the path of the image this item links to.
	 * @return The absolute path to the image (on the filesystem)
	 */
	public String getImagePath()
	{
		return imagePath;
	}

	/**
	 * We might want to get the StegMethod that has been used (for example, to print its name). Note, that this is the unique identifier of the StegMethod, and not a link to any Steganalysis object.
	 * @return The StegMethod specified for use with this image.
	 */
	public StegMethods getStegMethod()
	{
		return method;
	}
	
	/**
	 * Run the steganalysis
	 * @return true if steganalysis was performed correctly, false otherwise.
	 */
	public boolean runMethod()
	{
		if (!hasBeenTested)
		{
			StegMethod method = this.method.getMethod();
			Image i = new Image(this.imagePath);
			try {
				this.result = method.testImage(i);
				this.hasBeenTested = true;
			} catch (ImageTooSmallException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
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
