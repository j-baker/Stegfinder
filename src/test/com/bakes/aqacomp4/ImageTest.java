package com.bakes.aqacomp4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bakes.aqacomp4.imagetools.BasicImporter;
import com.bakes.aqacomp4.imagetools.Image;
import com.bakes.aqacomp4.imagetools.ImageTooSmallException;
import com.bakes.aqacomp4.imagetools.ImageTypes;

public class ImageTest {
	BasicImporter b = new BasicImporter();
	Image i = b.importImage("kosmo_50.bmp");

	@Test
	public void testGetPixel() {
		try {
			// Boundary test
			// Top left corner
			assertEquals(i.getPixel(0, 0, Colour.RED), 136);
			assertEquals(i.getPixel(0, 0, Colour.GREEN), 146);
			assertEquals(i.getPixel(0, 0, Colour.BLUE), 149);
			// Top right corner
			assertEquals(i.getPixel(0, 3871, Colour.RED), 116);
			assertEquals(i.getPixel(0, 3871, Colour.GREEN), 122);
			assertEquals(i.getPixel(0, 3871, Colour.BLUE), 127);
			// Bottom left corner
			assertEquals(i.getPixel(2591, 0, Colour.RED), 208);
			assertEquals(i.getPixel(2591, 0, Colour.GREEN), 216);
			assertEquals(i.getPixel(2591, 0, Colour.BLUE), 224);
			// Bottom right corner
			assertEquals(i.getPixel(2591, 3871, Colour.RED), 123);
			assertEquals(i.getPixel(2591, 3871, Colour.GREEN), 89);
			assertEquals(i.getPixel(2591, 3871, Colour.BLUE), 85);
			
		} catch (ImageTooSmallException e) {
			fail("Exception occurred");
		}
		
		try {
			// xOffset < 0
			assertEquals(i.getPixel(2591, -5, Colour.RED), 208);
			fail("IllegalArgumentException not triggered");
		} catch (IllegalArgumentException e) {
			
		} catch (ImageTooSmallException e) {
			fail("Wrong exception triggered!");
		}
		
		try {
			// yOffset < 0
			assertEquals(i.getPixel(-5, 100, Colour.RED), 208);
			fail("IllegalArgumentException not triggered");
		} catch (IllegalArgumentException e) {
			
		} catch (ImageTooSmallException e) {
			fail("Wrong exception triggered!");
		}
		
		try {
			// xOffset >= 3872
			assertEquals(i.getPixel(2591, 4000, Colour.RED), 208);
			fail("ImageTooSmallException not triggered");
		} catch (IllegalArgumentException e) {
			fail("Wrong exception triggered!");
		} catch (ImageTooSmallException e) {
			
		}
		
		try {
			// yOffset >= 2592
			assertEquals(i.getPixel(2644, 200, Colour.RED), 208);
			fail("ImageTooSmallException not triggered");
		} catch (IllegalArgumentException e) {
			fail("Wrong exception triggered!");
		} catch (ImageTooSmallException e) {
			
		}
	}

	@Test
	public void testGetSize() {
		assertEquals(i.getSize(), 3872*2592);
	}

	@Test
	public void testGetWidth() {
		assertEquals(i.getWidth(), 3872);
	}

	@Test
	public void testGetHeight() {
		assertEquals(i.getHeight(), 2592);
	}

	@Test
	public void testGetType() {
		assertEquals(i.getType(), ImageTypes.BITMAP);
	}

}
