package com.bakes.aqacomp4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bakes.aqacomp4.imagetools.ImageNotTestedException;
import com.bakes.aqacomp4.imagetools.ImageRecord;
import com.bakes.aqacomp4.stegmethods.StegMethods;

public class TestEffectiveness {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		double target = 0.9;
		int offset = 5000;
		int numImages = 500;
		String path = "res/BOWS2/training/";
		int correct = 0;
		for (int i = 0; i < numImages; i++)
		{
			System.out.println(i+ " " +  (correct/(i+0.0))*100+ " %");
			ImageRecord SPAM = new ImageRecord(path+(i+offset)+".bmp", StegMethods.SPAM);
			ImageRecord RS = new ImageRecord(path+(i+offset)+".bmp", StegMethods.RS);
			SPAM.runMethod();
			RS.runMethod();
			try {
				double score = ((6*SPAM.getResult())+RS.getResult())/7;
				if (i % 2 == 0)
				{
					if (score > 1.0/7)
					{
						correct++;
					}
				}
				else
				{
					if (score <= 1.0/7)
					{
						correct++;
					}
				}
			} catch (ImageNotTestedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (correct/(numImages + 0.0) < target)
		{
			fail("Target result "+target+" Achieved result "+(correct/(numImages+0.0)));
		}
		System.out.println(correct/(numImages+0.0) + "");
	}

}
