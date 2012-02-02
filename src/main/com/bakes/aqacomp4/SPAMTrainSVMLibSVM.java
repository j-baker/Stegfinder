/**
 * 
 */
package com.bakes.aqacomp4;

import java.io.File;

import libsvm.svm_model;
import libsvm.svm_parameter;
import libsvm.svm_problem;


import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.svm.SVM;
import org.encog.ml.svm.training.SVMTrain;
import org.encog.persist.EncogDirectoryPersistence;

/**
 * @author bakes
 *
 */
public class SPAMTrainSVMLibSVM {

	
	public static void main(String[] args)
	{
		SPAMTrainSVMLibSVM s = new SPAMTrainSVMLibSVM();
		s.trainAndSave();	
	}
	
	public void trainAndSave()
	{
		int numImages = 500;
		String fileName = "test1";
		String training = "res/BOWS2/training/";
		String testing = "res/BOWS2/testing/";
		MLDataSet trainingSet = getFeatures(training, 0, 0, 0, numImages);
		MLDataSet testingSet = getFeatures(testing, 1, 0, numImages, numImages);
		final int features = trainingSet.get(0).getInputArray().length;
		System.out.println(features);
		SVM network = new SVM(features, false);
		
		svm_parameter param = new svm_parameter();
		
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = svm_parameter.RBF;
		param.degree = 3;
		param.gamma = 0;
		param.coef0 = 0;
		param.nu = 0.5;
		param.cache_size = 40;
		param.C = 1;
		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 0;
		param.nr_weight = 0;
		param.weight_label = new int[0];
		param.weight = new double[0];
		
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
				EncogDirectoryPersistence.saveObject(new File(fileName+"_"+Double.toString(e).replace(".","")+"_"+epoch+".eg"), network);
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
		BasicImporter b = new BasicImporter();
		svm_problem prob = new svm_problem();
		final String suffix = ".bmp";
		double[][] expected = new double[numImages][1];
		double[][] features = new double[numImages][];
		for (int i = 0; i < numImages; i++)
		{
			Image image = b.importImage(prefix+(i+offset2)+suffix);
			double[][] imageFeatures = s.getSPAMFeatures(image);
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
		prob.l = numImages;
		prob.y = new double[numImages];
		
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
