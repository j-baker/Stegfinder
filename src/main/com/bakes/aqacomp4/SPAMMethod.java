/**
 * 
 */
package com.bakes.aqacomp4;

import java.io.File;
import java.util.Arrays;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.svm.SVM;
import org.encog.persist.EncogDirectoryPersistence;


/**
 * @author bakes
 *
 */
public class SPAMMethod implements StegMethod {
	private Image image;
	double[] result;
	SVM network;


	@Override
	public String getTextResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadImage(Image i) {
		this.image = i;
		String fileName = "res/test/testSVM.eg";
		network = (SVM)EncogDirectoryPersistence.loadObject(new File(fileName));
	}

	@Override
	public void testImage() {
		double[][] features = getSPAMFeatures(image);
		double[] result = new double[3];
		for (int i = 0; i < 3; i++)
		{
			MLData d = new BasicMLData(features[i]);
			d = network.compute(d);
			result[i] = d.getData(0);
		}
		this.result = result;
		
	}


	@Override
	public double[] getNumericalResult() throws ImageNotTestedException {
		if (result == null)
		{
			throw new ImageNotTestedException();
		}
		return result;
	}

	private static final int MAX_DIFFERENCE = 3;
	
	public static void main(String[] args)
	{
		Image i = new BasicImporter().importImage("kosmo_0.bmp");
		SPAMMethod s = new SPAMMethod();
		s.getSPAMFeatures(i);
		//s.tester();
	}
	
	double[][] getSPAMFeatures(Image image)
	{
		final boolean lessFeatures = true;
		
		int numColours = 1;
		int[][][][][] counts = new int[numColours][8][2*MAX_DIFFERENCE+1][2*MAX_DIFFERENCE+1][2*MAX_DIFFERENCE+1];
		int[][][][] maxCounts = new int[numColours][8][2*MAX_DIFFERENCE+1][2*MAX_DIFFERENCE+1];
		
		for (int q = 0; q < numColours; q++)
		{
			for (int i = 0; i < image.getHeight(); i++)
			{
				for (int j = 0; j < image.getWidth(); j++)
				{
					for (int offsetY = -1; offsetY <= 1; offsetY++)
					{
						if (i + (3*offsetY) >= 0 && i+(3*offsetY) < image.getHeight())
						{
							for (int offsetX = -1; offsetX <= 1; offsetX++)
							{
								if (j + (3*offsetX) >= 0 && j+(3*offsetX) < image.getWidth() && !(offsetX == 0 && offsetY ==0))
								{
									try {
										int difference1 = getDifference(image, q, j, i, j+offsetX, i+offsetY);
										int difference2 = getDifference(image, q, j+offsetX, i+offsetY, j+(2*offsetX), i+(2*offsetY));
										int difference3 = getDifference(image, q, j+(2*offsetX), i+(2*offsetY), j+(3*offsetX), i+(3*offsetY));
										if (Math.abs(difference1) <= MAX_DIFFERENCE && Math.abs(difference2) <= MAX_DIFFERENCE && Math.abs(difference3) <= MAX_DIFFERENCE)
										{
											counts[q][directionToArray(offsetX, offsetY)][MAX_DIFFERENCE+difference1][MAX_DIFFERENCE+difference2][MAX_DIFFERENCE+difference3]++;
											maxCounts[q][directionToArray(offsetX, offsetY)][MAX_DIFFERENCE+difference1][MAX_DIFFERENCE+difference2]++;
										}
									} catch (ImageTooSmallException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
										
								}
							}
						}
					}
				}
			}
		}
		
		double[][] result;
		
		if (lessFeatures)
		{
			result = new double[numColours][2*(2*MAX_DIFFERENCE+1)*(2*MAX_DIFFERENCE+1)*(MAX_DIFFERENCE+1)];
		}
		else
		{
			result = new double[numColours][2*(2*MAX_DIFFERENCE+1)*(2*MAX_DIFFERENCE+1)*(2*MAX_DIFFERENCE+1)];
		}
		for (int q = 0; q < numColours; q++)
		{
			int count = 0;
			for (int i = 0; i < counts[0][0].length; i++)
			{
				for (int j = 0; j < counts[0][0][0].length; j++)
				{
					for (int k = (lessFeatures ? MAX_DIFFERENCE: 0); k < counts[0][0][0][0].length; k++)
					{
						result[q][count] = (counts[q][0][i][j][k]+counts[q][2][i][j][k]+counts[q][4][i][j][k]+counts[q][6][i][j][k]+0.0)/(maxCounts[q][0][i][j]+maxCounts[q][2][i][j]+maxCounts[q][4][i][j]+maxCounts[q][6][i][j]);
						result[q][count+1] = (counts[q][1][i][j][k]+counts[q][3][i][j][k]+counts[q][5][i][j][k]+counts[q][7][i][j][k]+0.0)/(maxCounts[q][1][i][j]+maxCounts[q][3][i][j]+maxCounts[q][5][i][j]+maxCounts[q][7][i][j]);	
						count += 2;
					}
				}
			}
		}
		
		return result;
	}
	
	int directionToArray(int x, int y)
	{
		if (x == 0 && y == 1)
		{
			return 0;
		}
		else if (x == 1 && y == 1)
		{
			return 1;
		}
		else if (x == 1 && y == 0)
		{
			return 2;
		}
		else if (x == 1 && y == -1)
		{
			return 3;
		}
		else if (x== 0 && y == -1)
		{
			return 4;
		}
		else if (x == -1 && y == -1)
		{
			return 5;
		}
		else if (x == -1 && y == 0)
		{
			return 6;
		}
		else if (x == -1 && y == 1)
		{
			return 7;
		}
		System.out.println("Returning fail for "+x + " "+y);
		return -1;
	}
	
	int getDifference(Image image, int q, int x1, int y1, int x2, int y2) throws ImageTooSmallException
	{
		if (image.getWidth() <= x1 || image.getHeight() <= y1 || image.getWidth() <= x2 || image.getHeight() <= y2 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0)
		{
			throw new ImageTooSmallException();
		}
		return image.getPixel(x2, y2, q) - image.getPixel(x1,  y1, q);
	}
}
