/**
 * 
 */
package com.bakes.aqacomp4;

import java.util.Arrays;

import com.aqa.Console;

/**
 * @author bakes
 *
 */
public class TestConsole {
	Console console = new Console();

	/**
	 * 
	 */
	public TestConsole() {
	}
	
	public static void main(String[] args)
	{
		TestConsole c = new TestConsole();
		c.run();
	}
	
	public void run()
	{
		
		
		
		
		String path = console.readLine("Where is the image file? ");
		ImageImporter b = new BasicImporter();
		Image i = b.importImage(path);
		StegMethod s = new RSMethod();
		s.loadImage(i);
		try {
			s.testImage();
		} catch (ImageTooSmallException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			console.println(Arrays.toString(s.getNumericalResult()));
		} catch (ImageNotTestedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
