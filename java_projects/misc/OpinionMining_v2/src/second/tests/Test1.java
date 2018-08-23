package second.tests;

import second.model.weightFunctions.IWeightFunction;
import second.model.weightFunctions.WeightFunctionTest;


public class Test1 extends Test implements ITest{

	protected IWeightFunction defineWeightFunction() {
		IWeightFunction test = new WeightFunctionTest();
		return test;
	}
}
