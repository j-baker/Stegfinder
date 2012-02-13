/**
 * 
 */
package com.bakes.aqacomp4.stegmethods;

import java.io.File;

import org.encog.ml.data.MLData;
import org.encog.ml.data.basic.BasicMLData;
import org.encog.ml.svm.SVM;
import org.encog.persist.EncogDirectoryPersistence;

import com.bakes.aqacomp4.Colour;
import com.bakes.aqacomp4.imagetools.Image;
import com.bakes.aqacomp4.imagetools.ImageTooSmallException;


/**
 * @author bakes
 *
 */
public class SPAMMethod implements StegMethod {
	SVM network = null;
	
	public void loadSVM(String svmName)
	{
		network = (SVM)EncogDirectoryPersistence.loadObject(new File(svmName));
	}

	@Override
	public double testImage(Image image) throws Exception {
		if (network == null)
		{
			throw new Exception();
		}
		double[][] features = getSPAMFeatures(image);
		double result = 0;
		int numData = 0;
		for (int i = 0; i < 3; i++)
		{
			numData++;
			MLData d = new BasicMLData(features[i]);
			d = network.compute(d);
			result += d.getData(0);
		}
		// TODO Why mean?
		return result/numData;
	}

	private static final int MAX_DIFFERENCE = 3;
	
	double[][] getSPAMFeatures(Image image)
	{
		int numColours = 1;
		int[][][][][] counts = new int[Colour.length()][8][2*MAX_DIFFERENCE+1][2*MAX_DIFFERENCE+1][2*MAX_DIFFERENCE+1];
		int[][][][] maxCounts = new int[Colour.length()][8][2*MAX_DIFFERENCE+1][2*MAX_DIFFERENCE+1];
		
		for (Colour q : Colour.values())
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
											counts[q.ordinal()][directionToArray(offsetX, offsetY)][MAX_DIFFERENCE+difference1][MAX_DIFFERENCE+difference2][MAX_DIFFERENCE+difference3]++;
											maxCounts[q.ordinal()][directionToArray(offsetX, offsetY)][MAX_DIFFERENCE+difference1][MAX_DIFFERENCE+difference2]++;
										}
									} catch (ImageTooSmallException e) {
										e.printStackTrace();
									}
									
										
								}
							}
						}
					}
				}
			}
		}
		
		double[][] result = new double[numColours][2*(2*MAX_DIFFERENCE+1)*(2*MAX_DIFFERENCE+1)*(2*MAX_DIFFERENCE+1)];
		
		for (Colour q : Colour.values())
		{
			int count = 0;
			for (int i = 0; i < counts[0][0].length; i++)
			{
				for (int j = 0; j < counts[0][0][0].length; j++)
				{
						for (int k = 0; k < counts[0][0][0][0].length; k++)
						{
							result[q.ordinal()][count] = (counts[q.ordinal()][0][i][j][k]+counts[q.ordinal()][2][i][j][k]+counts[q.ordinal()][4][i][j][k]+counts[q.ordinal()][6][i][j][k]+0.0)/(maxCounts[q.ordinal()][0][i][j]+maxCounts[q.ordinal()][2][i][j]+maxCounts[q.ordinal()][4][i][j]+maxCounts[q.ordinal()][6][i][j]);
							result[q.ordinal()][count+1] = (counts[q.ordinal()][1][i][j][k]+counts[q.ordinal()][3][i][j][k]+counts[q.ordinal()][5][i][j][k]+counts[q.ordinal()][7][i][j][k]+0.0)/(maxCounts[q.ordinal()][1][i][j]+maxCounts[q.ordinal()][3][i][j]+maxCounts[q.ordinal()][5][i][j]+maxCounts[q.ordinal()][7][i][j]);	
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
		// TODO throw exception.
		return -1;
	}
	
	int getDifference(Image image, Colour q, int x1, int y1, int x2, int y2) throws ImageTooSmallException
	{
		if (image.getWidth() <= x1 || image.getHeight() <= y1 || image.getWidth() <= x2 || image.getHeight() <= y2 || x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0)
		{
			throw new ImageTooSmallException();
		}
		return image.getPixel(y2, x2, q) - image.getPixel(y1,  x1, q);
	}
}
