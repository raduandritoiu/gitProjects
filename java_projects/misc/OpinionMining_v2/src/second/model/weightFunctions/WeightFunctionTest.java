package second.model.weightFunctions;

import java.util.ArrayList;
import java.util.Random;

import second.model.Term;

import common.model.Opinion;

public class WeightFunctionTest extends WeightFunction implements IWeightFunction {
	
	/*
	 *  This class sets the weights of all words as if they are in a #D SPace.
	 *  Class used just to check if SVM works ok.
	 */
	
	public double[] calculateWeights(ArrayList<Term> terms, Opinion op) {
		Random randomGen = new Random();
		double preClass = op.preclassification;
		if (preClass < 0) {
			preClass = randomGen.nextInt(5) + 1;
		}
		
		double x = randomGen.nextInt(100) / 10;
		double y = (terms.size() == 3) ? randomGen.nextInt(100) / 10 : 0;
		double z = preClass - x - y;
		
		double[] weights = new double[terms.size()];
		weights[0] = x;
		weights[1] = z;
		if (terms.size() == 3)
			weights[2] = y;
		
		return weights;
	}

}
