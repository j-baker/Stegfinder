/**
 * 
 */
package com.bakes.aqacomp4;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
		Queue<ImageQueueItem> queue = new LinkedList<ImageQueueItem>();
		
		int numImages = console.readInteger("How many images do you want to load? ");
		for (int i = 0; i < numImages; i++)
		{
			String path = console.readLine("What is the path for image "+i+"? ");
			ImageQueueItem member = new ImageQueueItem(path, StegMethods.RS, ImageTypes.BITMAP);
			queue.add(member);
		}
		
		while (!queue.isEmpty())
		{
			ImageQueueItem item = queue.remove();
			item.runMethod();
			try {
				System.out.println(Arrays.toString(item.getNumericalResult()));
			} catch (ImageNotTestedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
