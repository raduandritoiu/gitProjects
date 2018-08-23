package second.tests;

import second.model.Domain;
import second.model.weightFunctions.IWeightFunction;


public interface ITest {
	public void startTest();
	public void stopTest();
	
	public Domain getDomain();
	public IWeightFunction getFunction();
}
