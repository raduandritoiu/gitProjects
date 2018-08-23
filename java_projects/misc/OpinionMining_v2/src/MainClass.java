import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import libsvm.svm_node;
import libsvm.svm_problem;
import second.model.AnnotatedSet;
import second.model.Domain;
import second.model.Presentation;
import second.model.SVM;
import second.model.weightFunctions.IWeightFunction;
import second.model.weightFunctions.WeightFunction;
import second.model.weightFunctions.WeightFunctionAllSimple;
import second.model.weightFunctions.WeightFunctionAllStems;
import second.model.weightFunctions.WeightFunctionAllSyns;
import second.model.weightFunctions.WeightFunctionMeroSimple;
import second.model.weightFunctions.WeightFunctionMeroStems;
import second.model.weightFunctions.WeightFunctionMeroSyns;
import second.model.weightFunctions.WeightFunctionMeronyms;
import second.model.weightFunctions.WeightFunctionSimple;
import second.model.weightFunctions.WeightFunctionSimpleStems;
import second.model.weightFunctions.WeightFunctionSimpleSyns;
import second.model.weightFunctions.WeightFunctionSynsSimple;
import second.model.weightFunctions.WeightFunctionSynsStems;
import second.model.weightFunctions.WeightFunctionSynsSyns;
import second.tests.ITest;
import second.tests.Test;
import second.tests.Test1;
import utils.InputDocumentParser;

import common.model.Opinion;
import common.model.Reference;

public class MainClass {
	public static void main(String args[]) throws IOException {
		System.out.println("start!\n");
		
		
//		RiWordnet wordnet = new RiWordnet();  
//	    SentiWN swn = new SentiWN(wordnet);
//		System.out.println("Informal - " + swn.getScore("Informal"));
//		System.out.println("zxcxzc - " + swn.getScore("alksjdhsak"));
//		
//		// Generating the rules for negation detection
//		GenNegEx g 			= new GenNegEx();
//		String fillerString		= "_";
//		boolean negatePossible		= true;
//		String triggersFile		= "D:\\Work\\Eclipse\\opinionMining\\src\\NegEx\\negex_triggers.txt";	
//        File ruleFile           = new File(triggersFile);
//		try {
//			Scanner sc 		= new Scanner(ruleFile);
//			ArrayList rules	= new ArrayList();
//			ArrayList sortedRules	= new ArrayList();
//			String afterNegCheck		= "";
//			String sentence = "The phone is not beautiful.";
//
//			while (sc.hasNextLine()) {
//				rules.add(sc.nextLine());
//			}
//			sortedRules = Sorter.sortRules(rules);
//			System.out.println("asdsadsa\n");
//			
//			/* ------ testing negation detection
//			try {
//				afterNegCheck = g.negCheck(sentence, "beautiful", sortedRules, negatePossible);
//				System.out.println("results:"+afterNegCheck+"\n");
//			}
//			catch (Exception e) {
//				System.out.println(e);
//			}
//			*/
//			sc.close();
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		/* ========= Parsing the XML file ========= */
		InputDocumentParser parser = new InputDocumentParser();
		parser.parseDocument();
		
		// separate opinions into words
		for (Opinion op : parser.myOpinions) {
			op.separateWords();
		}
		
		
		// START A TEST
		ITest test1 = new Test1();
		
		// define a domain
		Domain testDomain = test1.getDomain();
		IWeightFunction weightFunction = test1.getFunction();
		
		// determine weight vectors for opinions
		for (Opinion op : parser.myOpinions) {
			double [] weights = weightFunction.calculateWeights(testDomain.terms, op);
			op.vector = new double[testDomain.size()];
			for (int i=0; i < weights.length; i++) {
				op.addWeight(weights[i], i);
			}
		}
		
		// separate annotated set from opinion set
		AnnotatedSet opinionSet = new AnnotatedSet();
		AnnotatedSet annotSet = new AnnotatedSet();
		for (Opinion op : parser.myOpinions) {
			if (op.preclassification >= 0) {
				annotSet.addOpinion(op);
			}
			else {
				opinionSet.addOpinion(op);
			}
		}
		
		// create training and test sets
		int setSize = annotSet.size();
		setSize = (setSize * 5) / 7;
		annotSet.createTrainingSet(setSize);
		AnnotatedSet trainingSet = annotSet.trainingSet;
		AnnotatedSet testSet = annotSet.testSet;
		
		// create SVM
		SVM svm = new SVM();
		svm.setDomain(testDomain);
		svm.train(trainingSet);
		
		// test the SVM and calculate accuracy
		svm.test(testSet);
		
		// do something with SVM accuracy
		System.out.println("");
		System.out.println("");
		svm.print();
		
		// classify the remaining opinions in opinion set
		svm.classifySet(opinionSet);
		
		opinionSet.computeWeights();
		opinionSet.classification = svm.classifyVector(opinionSet.vector);
		
		
		// all done - present data;
		Presentation presentationData = new Presentation();
		presentationData.setDomain(testDomain);
		presentationData.setOpinionSet(opinionSet);
		presentationData.print();
		
		//START A DIFFERENT TEST
		//test2();
	}
	
	
	
	
	
	
	
	
	
	private static void test2()  throws IOException {
		String input_file_name = "d:\\heart_scale.txt";

		BufferedReader fp = new BufferedReader(new FileReader(input_file_name));
		Vector<Double> vy = new Vector<Double>();
		Vector<svm_node[]> vx = new Vector<svm_node[]>();
		int max_index = 0;

		while(true)
		{
			String line = fp.readLine();
			if(line == null) break;

			StringTokenizer st = new StringTokenizer(line," \t\n\r\f:");

			vy.addElement(atof(st.nextToken()));
			int m = st.countTokens()/2;
			svm_node[] x = new svm_node[m];
			for(int j=0;j<m;j++)
			{
				x[j] = new svm_node();
				x[j].index = atoi(st.nextToken());
				x[j].value = atof(st.nextToken());
			}
			if(m>0) max_index = Math.max(max_index, x[m-1].index);
			vx.addElement(x);
		}

		svm_problem prob = new svm_problem();
		prob = new svm_problem();
		prob.l = vy.size();
		prob.x = new svm_node[prob.l][];
		for(int i=0;i<prob.l;i++)
			prob.x[i] = vx.elementAt(i);
		prob.y = new double[prob.l];
		
		for(int i=0;i<prob.l;i++)
			prob.y[i] = vy.elementAt(i);
		
		SVM mySvm = new SVM();
		mySvm.testTrain(prob);
		
		mySvm.testClassifycation(prob);
	}
	
	
	private static double atof(String s)
	{
		double d = Double.valueOf(s).doubleValue();
		if (Double.isNaN(d) || Double.isInfinite(d))
		{
			System.err.print("NaN or Infinity in input\n");
			System.exit(1);
		}
		return(d);
	}

	private static int atoi(String s)
	{
		return Integer.parseInt(s);
	}

}
