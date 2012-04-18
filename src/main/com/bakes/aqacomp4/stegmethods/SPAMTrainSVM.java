/**
 * 
 */
package com.bakes.aqacomp4.stegmethods;

import java.io.File;

import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.training.SVMTrain;
import org.encog.persist.EncogDirectoryPersistence;
import com.bakes.aqacomp4.imagetools.Image;

/**
 * @author bakes
 *
 */
public class SPAMTrainSVM {

	
	public static void main(String[] args)
	{
		SPAMTrainSVM s = new SPAMTrainSVM();
		s.trainAndSave();	
	}
	
	public void trainAndSave()
	{
		int numImages = 5000;
		String fileName = "replace_5";
		String training = "res/BOWS2/training/";
		String testing = "res/BOWS2/testing/";
		MLDataSet trainingSet = getFeatures(training, 0, 0, 0, numImages);
		MLDataSet testingSet = getFeatures(testing, 1, 0, numImages, numImages);
		final int features = trainingSet.get(0).getInputArray().length;
		System.out.println(features);
		SVM network = new SVM(features, false);
		final SVMTrain train = new SVMTrain(network, trainingSet);
		double bestC=0;
		double bestGamma=0;
		double bestError=1;
		int epoch = 1;
		double e = 1;
		
		double c;
		for (int i = 0; i < 10; i+=1)
		{
			for (int j = 0; j < 11; j+=1)
			{
				c = Math.pow(2, -5+(2*j));
				double gamma = Math.pow(2, (-15 +(2*i)));
				train.setC(c);
				train.setGamma(gamma);
				train.iteration();
				train.finishTraining();
				e = calculateError(testingSet, network);
				if (e < bestError)
				{
					bestError = e;
					bestC = c;
					bestGamma = gamma;
				}
				System.out.println("Epoch: "+epoch+" Best Error: "+bestError+" Current Error: "+e+" Self Error: "+calculateError(trainingSet, network)+ " Best Gamma: "+bestGamma+" Best C: "+bestC);
				epoch++;
				//EncogDirectoryPersistence.saveObject(new File(fileName+"_"+Double.toString(e).replace(".","")+"_"+epoch+".eg"), network);
			}
		}
		train.setC(bestC);
		train.setGamma(bestGamma);
		train.iteration();
		train.finishTraining();
		System.out.println("Trained to a margin of error of: "+train.getError());
		System.out.println("Done! Saving the network.");
		EncogDirectoryPersistence.saveObject(new File(fileName+".eg"), network);
		System.out.println("Done! Shutting down.");
		
		
	}
	
	public MLDataSet getFeatures(String prefix, int offset, int colour, int offset2, final int numImages)
	{
		SPAMMethod s = new SPAMMethod();
		
		final String suffix = ".bmp";
		double[][] expected = new double[numImages][1];
		double[][] features = new double[numImages][];
		for (int i = 0; i < numImages; i++)
		{
			Image image = new Image(prefix+(i+offset2)+suffix);
			double[][] imageFeatures = s.getSPAMFeatures(image);
			/*String s1 = "";
			for (int j = 0; j < imageFeatures[0].length; j++)
			{
				s1+= imageFeatures[0][j] + ",";
			}
			System.out.println(s1);*/
			features[i] = imageFeatures[colour];
			if (i % 2 == offset)
			{
				expected[i][0] = 1;
			}
			else
			{
				expected[i][0] = 0;
			}
			if (i % 5 == 0)
			{
				System.out.println(i);
			}
		}
	
		return new BasicMLDataSet(features, expected);
	}

	
	public double calculateError(MLDataSet testSet, MLRegression network)
	{
		int falsePositiveCount = 0;
		int falseNegativeCount = 0;
		int totalCount = 0;
		for (MLDataPair pair : testSet) {
			MLData output = network.compute(pair.getInput());
			double expected = pair.getIdealArray()[0];
			double achieved = output.getData()[0];
			if (!Double.isNaN(achieved) && !Double.isNaN(expected))
			{
				totalCount++;
				if (expected != 0)
				{
					if (achieved <= 0.5)
					{
						falseNegativeCount++;
					}
				}
				else
				{
					if (achieved >= 0.5)
					{
						falsePositiveCount++;
					}
				}
				
			}
		}
		return (falsePositiveCount + falseNegativeCount + 0.0)/(totalCount + 0.0);
	}
}
