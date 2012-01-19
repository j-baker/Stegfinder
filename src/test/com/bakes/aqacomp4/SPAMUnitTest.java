package com.bakes.aqacomp4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SPAMUnitTest {
	Image i;
	byte[][] differencesRight;
	private byte[][] differencesLeft;

	@Before
	public void setUp() throws Exception {
		byte[][][] imageData = {{
				{ 37, 39, 22, 30, 8, 10, 5, 30},
				{ 6, 22, 14, 13, 41, 6, 23, 35},
				{3, 49, 6, 38, 34, 20, 49, 49},
				{45, 35, 32, 29, 18, 22, 47, 16},
				{33, 5, 25, 18, 26, 43, 30, 5},
				{23, 47, 4, 31, 4, 28, 1, 43},
				{27, 10, 11, 4, 50, 45, 48, 2},
				{36, 39, 39, 15, 21, 26, 50, 15}
		},null, null};
		short[][] differencesRight = {
				{2, -17, 8, -22, 2, -5, 25},
				{16, -8, -1, 28, -35, 17, 12},
				{46, -43, 32, -4, -14, 29, 0},
				{-10, -3, -3, -11, 4, 25, -31},
				{-28, 20, -7, 8, 17, -13, -25},
				{24, -43, 27, -27, 24, -27, 42},
				{-17, 1, -7, 46, -5, 3, -46},
				{3, 0, -24, 6, 5, 24, -35},
				
		};
		short[][] differencesLeft = new short[differencesRight.length][differencesRight[0].length];
		for (int i = 0; i < differencesLeft.length; i++)
		{
			for (int j = 0; j < differencesLeft[0].length; j++)
			{
				differencesLeft[i][j] = (short) (-1*differencesRight[i][j]);
			}
		}
		byte[][] differencesLeft = {
				{-2, 17, -8, 22, -2, 5, -25},
				{-16, 8, 1, -28, 35, -17, -12},
				{-46, 43, -32, 4, 14, -29, 0},
				{10, 3, 3, 11, -4, -25, 31},
				{28, -20, 7, -8, -17, 13, 25},
				{-24, 43, -27, 27, -24, 27, -42},
				{17, -1, 7, -46, 5, -3, 46},
				{-3, 0, 24, -6, -5, -24, 35}
		};
		
		byte[][] differencesDown = {
				{-31, -17, -8, -17, 33, -4, 18, 5},
				{-3, 27, -8, 25, -7, 14, 26, 14},
				{42, -14, 26, -9, -16, 2, -2, -33},
				{-12, -30, -7, -11, 8, 21, -17, -11},
				{-10, 42, -21, 13, -22, -15, -29, 38},
				{4, -37, 7, -27, 46, 17, 47, -41},
				{9, 29, 28, 11, -29, -19, 2, 13}
		};
		
		
		
		
		
		i = new Image(imageData, "bmp");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSPAMFeatures() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMarkovSecondOrder() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDifferences() {
		fail("Not yet implemented");
	}

}
