package second.model;

import java.util.ArrayList;
import java.util.Random;

import common.model.Opinion;


public class AnnotatedSet {
	public ArrayList<Opinion> opinions;
	public AnnotatedSet trainingSet;
	public AnnotatedSet testSet;
	public double[] vector;
	public double classification;
	
	public AnnotatedSet() {
		opinions = new ArrayList<Opinion>();
	}
	
	
	public int size() {
		if (opinions == null)
			return 0;
		return opinions.size();
	}
	
	
	public void addOpinion(Opinion op) {
		if (opinions == null)
			opinions = new ArrayList<Opinion>();
		
		opinions.add(op);
	}
	
	
	public void addOpinions(ArrayList<Opinion> newOp) {
		if (opinions == null)
			opinions = new ArrayList<Opinion>();
		
		for (int i = 0; i < newOp.size(); i++) {
			opinions.add(newOp.get(i));
		}
	}

	
	public void createTrainingSet(int size){
		if (size > size())
			return;
		
		trainingSet = new AnnotatedSet();
		testSet = new AnnotatedSet();
		testSet.addOpinions(opinions);
		
		Random randomGen = new Random();
		for (int i = 0; i < size; i++) {
			int idx = randomGen.nextInt(testSet.size());
			trainingSet.addOpinion(testSet.opinions.get(idx));
			testSet.opinions.remove(idx);
		}
	}
	
	
	public void computeWeights() {
		vector = new double[opinions.get(0).vector.length];
		
		for (int i=0; i < vector.length; i++) {
			for (Opinion op : opinions) {
				vector[i] = vector[i] + op.vector[i];
			}
			vector[i] = vector[i] / opinions.size();
		}
	}
}
