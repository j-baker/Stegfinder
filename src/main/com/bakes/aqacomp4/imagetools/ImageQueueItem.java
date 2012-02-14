/**
 * 
 */
package com.bakes.aqacomp4.imagetools;

import com.bakes.aqacomp4.gui.StegTableModel;
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
	private StegTableModel table;
	
	// Information about which test to use
	StegMethods method;
	ImageTypes imageType;
	
	
	public ImageQueueItem(String imagePath, StegMethods method, ImageTypes type, StegTableModel table) {
		this.imagePath = imagePath;
		this.method = method;
		this.imageType = type;
		this.table = table;
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
	
	public void runMethod()
	{
		StegMethod method = this.method.getMethod();
		Image i = (new BasicImporter()).importImage(this.imagePath);
		try {
			this.result = method.testImage(i);
			this.hasBeenTested = true;
			table.fireTableDataChanged();
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