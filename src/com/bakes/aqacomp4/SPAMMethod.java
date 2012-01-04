/**
 * 
 */
package com.bakes.aqacomp4;


/**
 * @author bakes
 *
 */
public class SPAMMethod implements StegMethod {

	/**
	 * 
	 */
	public SPAMMethod() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public String getTextResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadImage(Image i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testImage() {
		
		// TODO Auto-generated method stub
		
	}


	@Override
	public double[] getNumericalResult() throws ImageNotTestedException {
		// TODO Auto-generated method stub
		return null;
	}

	private static final int MAXDIFFERENCE = 3;

	
	public double[][] getSPAMFeatures(byte[][][] imageData)
	{
		double[][] result = new double[2*(2*MAXDIFFERENCE+1)*(2*MAXDIFFERENCE+1)*(2*MAXDIFFERENCE+1)][3];
		for (int q = 0; q < 3; q++)
		{
			int[][] right = getDifferences(imageData, q, 0, 1);
			int[][] left = getDifferences(imageData, q, 0, -1);
			int[][] up = getDifferences(imageData, q, -1, 0);
			int[][] down = getDifferences(imageData, q, 1, 0);
			double[][][] markovRight = getMarkovSecondOrder(right, 0, 1);
			double[][][] markovLeft = getMarkovSecondOrder(left, 0, -1);
			double[][][] markovUp = getMarkovSecondOrder(up, -1, 0);
			double[][][] markovDown = getMarkovSecondOrder(down, 1, 0);
			
			int[][] downRight = getDifferences(imageData, q, 1, 1);
			int[][] downLeft = getDifferences(imageData, q, 1, -1);
			int[][] upLeft = getDifferences(imageData, q, -1, -1);
			int[][] upRight = getDifferences(imageData, q, -1, 1);
			double[][][] markovDownRight = getMarkovSecondOrder(downRight, 1, 1);
			double[][][] markovDownLeft = getMarkovSecondOrder(downLeft, 1, -1);
			double[][][] markovUpLeft = getMarkovSecondOrder(upLeft, -1, -1);
			double[][][] markovUpRight = getMarkovSecondOrder(upRight, -1, 1);
			
			double[][][] straights = new double[2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1];
			double[][][] diagonals = new double[2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1];
			for (int i = 0; i < 2*MAXDIFFERENCE+1; i++)
			{
				for (int j = 0; j < 2*MAXDIFFERENCE+1; j++)
				{
					for (int k = 0; k < 2*MAXDIFFERENCE+1; k++)
					{
						straights[i][j][k] = 0.25*(markovRight[i][j][k] + markovLeft[i][j][k] + markovUp[i][j][k] + markovDown[i][j][k]);
						diagonals[i][j][k] = 0.25*(markovDownRight[i][j][k] + markovDownLeft[i][j][k] + markovUpLeft[i][j][k] + markovUpRight[i][j][k]);
					}
				}
			}
			
			double[] spamFeatures = new double[2*(2*MAXDIFFERENCE+1)*(2*MAXDIFFERENCE+1)*(2*MAXDIFFERENCE+1)];
			for (int i = 0; i < 2*MAXDIFFERENCE+1; i++)
			{
				for (int j = 0; j < 2*MAXDIFFERENCE+1; j++)
				{
					for (int k = 0; k < 2*MAXDIFFERENCE+1; k++)
					{
						spamFeatures[((2*MAXDIFFERENCE+1)*(2*MAXDIFFERENCE+1)*i)+((2*MAXDIFFERENCE+1)*j)+k] = straights[i][j][k];
						spamFeatures[((2*MAXDIFFERENCE+1)*(2*MAXDIFFERENCE+1)*(2*MAXDIFFERENCE+1))+(((2*MAXDIFFERENCE+1)*(2*MAXDIFFERENCE+1)*i)+((2*MAXDIFFERENCE+1)*j)+k)] = diagonals[i][j][k];
					}
				}
			}
			result[q] = spamFeatures;
		}
		return result;
	}

	public double[][][] getMarkovSecondOrder(int[][] differences, int changeRows, int changeColumns)
	{
		int height = differences.length;
		int width = differences[0].length;
		
		int[][][] counts = new int[2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1];
		int[][] generalCounts = new int[2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1];
		double[][][] results = new double[2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1][2*MAXDIFFERENCE+1];
		
		for (int i = 0 + ((changeRows < 0)? 2 : 0); i < height - ((changeRows > 0)? 2 : 0); i++)
		{
			for (int j = 0 + ((changeColumns < 0)? 2 : 0); j < width - ((changeColumns > 0)? 2 : 0); j++)
			{
				if (Math.abs(differences[i][j]) <= MAXDIFFERENCE && Math.abs(differences[i+changeRows][j+changeColumns]) <= MAXDIFFERENCE)
				{
					if (Math.abs(differences[i+(2*changeRows)][j+(2*changeColumns)]) <= MAXDIFFERENCE)
					{
						counts[MAXDIFFERENCE+differences[i][j]][MAXDIFFERENCE+differences[i+changeRows][j+changeColumns]][MAXDIFFERENCE+differences[i+(2*changeRows)][j+(2*changeColumns)]]++;	
					}
					generalCounts[MAXDIFFERENCE+differences[i][j]][MAXDIFFERENCE+differences[i+changeRows][j+changeColumns]]++;
				}
			}
		}
		
		for (int i = 0; i < 2*MAXDIFFERENCE+1; i++)
		{
			for (int j = 0; j < 2*MAXDIFFERENCE+1; j++)
			{
				for (int k = 0; k < 2*MAXDIFFERENCE+1; k++)
				results[i][j][k] = ((double) counts[i][j][k])/((double) generalCounts[i][j]);
			}
		}
		return results;
	}
	
	public int[][] getDifferences(byte[][][] channelData, int channel, int changeRows, int changeColumns)
	{
		int height = channelData.length;
		int width = channelData[0].length;
		
		
		int[][] differences = new int[height][width];
		
		for (int i = 0 + ((changeRows < 0)? 1 : 0); i < height - ((changeRows > 0)? 1 : 0); i++)
		{
			for (int j = 0 + ((changeColumns < 0)? 1 : 0); j < width - ((changeColumns > 0)? 1 : 0); j++)
			{
				int difference = channelData[i][j][channel] - channelData[i+changeRows][j+changeColumns][channel];
				differences[i][j] = difference;
			}
		}
		
		return differences;		
	}
}
