package second.model;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;
import common.model.Opinion;


public class SVM {
	private boolean trained = false;
	private double accuracy;
	
	private Domain domain;
	private svm_model model;
	private svm_problem problem;
	private svm_parameter params;
	
	
	public SVM() {}
	
	
	public boolean isTrained() {
		return trained;
	}
	
	
	public double getAccuracy() {
		return accuracy;
	}
	
	
	public void setDomain(Domain newDomain) {
		domain = newDomain;
	}
	
	
	public void train(AnnotatedSet trainingSet) {
		// create svm parameters - ?
		params = new svm_parameter();
		// default values
		params.svm_type = svm_parameter.C_SVC;
		params.kernel_type = svm_parameter.RBF;
		params.degree = 3;
		params.gamma = 1.0 / domain.size();
		params.coef0 = 0;
		params.nu = 0.5;
		params.cache_size = 100;
		params.C = 1;
		params.eps = 1e-3;
		params.p = 0.1;
		params.shrinking = 1;
		params.probability = 0;
		params.nr_weight = 0;
		
		// create svm problem
		problem = new svm_problem();
		problem.l = trainingSet.size();
		problem.y = new double[trainingSet.size()];
		for (int i=0; i < trainingSet.size(); i++) {
			Opinion op = trainingSet.opinions.get(i);
			problem.y[i] = op.preclassification;
		}
		
		problem.x = new svm_node[trainingSet.size()][domain.size() + 1];
		for (int i=0; i < trainingSet.size(); i++) {
			Opinion op = trainingSet.opinions.get(i);
			for (int j=0; j < op.vector.length; j++) {
				svm_node node = new svm_node();
				node.index = j;
				node.value = op.vector[j];
				problem.x[i][j] = node;
			}
			svm_node node = new svm_node();
			node.index = -1;
			node.value = 0;
			problem.x[i][op.vector.length] = node;
		}
		
		// train the svm
		String error_msg = svm.svm_check_parameter(problem, params);
		if (error_msg != null) {
			System.err.print("ERROR: "+error_msg+"\n");
			System.exit(1);
		}

		model = svm.svm_train(problem, params);
		trained = true;
	}
	
	
	public void classifySet(AnnotatedSet opinionSet) {
		for (Opinion op : opinionSet.opinions) {
			svm_node[] vec = new svm_node[op.vector.length];
			for (int i = 0; i < op.vector.length; i++) {
				svm_node node = new svm_node();
				node.index = i;
				node.value = op.vector[i];
				vec[i] = node;
			}
			
			double classification = svm.svm_predict(model, vec);
			op.classification = (int) classification;
		}
	}
	
	public double classifyVector(double[] vector) {
		svm_node[] vec = new svm_node[vector.length];
		for (int i = 0; i < vector.length; i++) {
			svm_node node = new svm_node();
			node.index = i;
			node.value = vector[i];
			vec[i] = node;
		}
		
		double classification = svm.svm_predict(model, vec);
		return classification;
	}
	
	
	public void test(AnnotatedSet testSet) {
		classifySet(testSet);
		double correct = 0.0;
		double incorrect = 0.0;
		
		for (Opinion op : testSet.opinions) {
			if (op.preclassification == op.classification) {
				correct = correct + 1;
			}
			else {
				incorrect = incorrect + 1;
			}
		}
		accuracy = correct / (correct + incorrect);
	}
	
	
	
	public void print(Presentation presentation) {
		String out = "";
		if (trained) {
			out = "SVM is trained with ACCURACY: " + accuracy;
		}
		else {
			out = "SVM is not trained";
		}
		System.out.println("========== SVM =================================================");
		System.out.println(out);
		System.out.println("=================================================================");
		System.out.println("");

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void testTrain(svm_problem problem) {
		// create svm parameters - ?
		params = new svm_parameter();
		// default values
		params.svm_type = svm_parameter.C_SVC;
		params.kernel_type = svm_parameter.RBF;
		params.degree = 3;
		params.gamma = 1.0 / 13.0;
		params.coef0 = 0;
		params.nu = 0.5;
		params.cache_size = 100;
		params.C = 1;
		params.eps = 0.001;
		params.p = 0.1;
		params.shrinking = 1;
		params.probability = 0;
		params.nr_weight = 0;
		
		
		// train the svm
		String error_msg = svm.svm_check_parameter(problem, params);
		if (error_msg != null) {
			System.err.print("ERROR: "+error_msg+"\n");
			System.exit(1);
		}

		model = svm.svm_train(problem, params);
	}
	
	
	public void testClassifycation(svm_problem problem) {
		for (int i=0; i < problem.l; i++) {
			double expect = problem.y[i];
			double result = svm.svm_predict(model,problem.x[i]);
			
			double tst = expect - result;
		}
	}
}
