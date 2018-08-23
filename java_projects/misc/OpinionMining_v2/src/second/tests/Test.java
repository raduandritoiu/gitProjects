package second.tests;

import second.model.Domain;
import second.model.Term;
import second.model.weightFunctions.IWeightFunction;
import second.model.weightFunctions.WeightFunction;
import second.model.weightFunctions.WeightFunctionMeroStems;


public class Test implements ITest{
	protected Domain testDomain;
	protected IWeightFunction testFunction;
	
	
	public Test() {
		testDomain = defineTestDomain();
		testFunction = defineWeightFunction();
	}
	
	
	public void startTest() {
	}
	
	
	public void stopTest() {
	}
	
	
	public Domain getDomain() {
		return testDomain;
	}
	
	
	public IWeightFunction getFunction() {
		return testFunction;
	}
	
	
	protected Domain defineTestDomain() {
		Domain testDomain = new Domain("overall");
		
		Term newTerm = new Term("phone");
		newTerm.words.add("smartphone");
		newTerm.words.add("sg2");
		newTerm.words.add("sgs2");
		testDomain.terms.add(newTerm);
		
		newTerm = new Term("display");
		newTerm.words.add("touch");
		newTerm.words.add("video");
		newTerm.words.add("photo");
		testDomain.terms.add(newTerm);
		
		newTerm = new Term("processor");
		newTerm.words.add("speed");
		newTerm.words.add("cpu");
		newTerm.words.add("operations");
		testDomain.terms.add(newTerm);
		
		newTerm = new Term("operating system");
		newTerm.words.add("OS");
		newTerm.words.add("android");
		testDomain.terms.add(newTerm);
		
		newTerm = new Term("battery");
		newTerm.words.add("life");
		testDomain.terms.add(newTerm);
		
		newTerm = new Term("price");
		newTerm = new Term("purchase");
		testDomain.terms.add(newTerm);
		
		testDomain.classes.add("Very Negative");
		testDomain.classes.add("Negative");
		testDomain.classes.add("Indifferent");
		testDomain.classes.add("Positive");
		testDomain.classes.add("Very Positive");
		
		return testDomain;
	}
	
	
	protected IWeightFunction defineWeightFunction() {
		IWeightFunction test = new WeightFunctionMeroStems();
		return test;
	}
}
