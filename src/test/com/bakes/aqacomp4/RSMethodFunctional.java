package com.bakes.aqacomp4;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bakes.aqacomp4.imagetools.Image;
import com.bakes.aqacomp4.imagetools.ImageTooSmallException;
import com.bakes.aqacomp4.stegmethods.RSMethod;
import com.bakes.aqacomp4.stegmethods.StegMethod;

public class RSMethodFunctional {
	static final String prefix = "res/kosmo_";
	static final String suffix = ".bmp";
	StegMethod s;
	static final double ERROR_MARGIN = 0.3;

	@Before
	public void setUp() throws Exception {
		s = new RSMethod();
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Load 20 images with known embedding (see folder res/test/).
	 * Run test on each.
	 * Verify that test results are within a predefined margin of error.
	 */
	@Test
	public void performFunctionalTest() {

		for (int i = 0; i <= 100; i+=5)
		{
			System.out.println("Testing "+i);
			try {
				double result = s.testImage(new Image(prefix+i+suffix));
				if (result + ERROR_MARGIN < i*0.01 || result - ERROR_MARGIN > i*0.01)
				{
					fail("Test not accurate enough");
				}
			} catch (ImageTooSmallException e) {
				e.printStackTrace();
			}
		}
	}

}
