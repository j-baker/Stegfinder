package com.bakes.aqacomp4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RSMethodFunctional {
	static final String prefix = "res/test/kosmo_";
	static final String suffix = ".bmp";
	ImageImporter importer = new BasicImporter();
	StegMethod s;

	@Before
	public void setUp() throws Exception {
		s = new RSMethod();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNumericalResult() {
		for (int i = 0; i <= 100; i+=5)
		{
			System.out.println("Testing "+i);
			s.loadImage(importer.importImage(prefix+i+suffix));
			try {
				s.testImage();
			} catch (ImageTooSmallException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				double[] result = s.getNumericalResult();
				for (int j = 0; j < 3; j++)
				{
					if (result[j] + 0.3 < i*0.01 || result[j] - 0.3 > i*0.01)
					{
						fail("Test not accurate enough");
					}
				}
			} catch (ImageNotTestedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testGetTextResult() {
		fail("Not yet implemented");
	}

	@Test
	public void testLoadImage() {
		fail("Not yet implemented");
	}

	@Test
	public void testTestImage() {
		fail("Not yet implemented");
	}

}
