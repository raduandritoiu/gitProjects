package second.model.weightFunctions;

import java.util.ArrayList;

import utils.ArrayUtils;

import common.model.Reference;

public class WeightFunctionSimpleStems extends WeightFunction {
	
	/*
	 * Class implements case a-c. 
	 * It searches only for the word.
	 * To find words does the intersection of their stems (radicalul cuvantului).
	 * 
	 * OK - this may be a good solution, but may provide few references
	 */
	
	public ArrayList<Reference> findWordReferences(String word, ArrayList<String> words) {
		ArrayList<Reference> refs = new ArrayList<Reference>();
		ArrayList<Integer> pos; 
		
		pos = findSimpleWordReferences(word, words);
		for (int j=0; j < pos.size(); j++) {
			Reference ref = new Reference(pos.get(j), 1);
			refs.add(ref);
		}
		
		return refs;
	}
	
	
	public boolean wordsMatch(String w1, String w2) {
		if (w1.equals(w2))
			return true;
		
		if (w1.length() < 2)
			return false;
		
		if (w2.length() < 2)
			return false;
		
		String[] syns1;
		String[] syns2;
		
		syns1 = wordNet.wn.getStems(w1, "n");
		syns2 = wordNet.wn.getStems(w2, "n");
		if (ArrayUtils.intersects(syns1, syns2))
			return true;
		
		syns1 = wordNet.wn.getStems(w1, "v");
		syns2 = wordNet.wn.getStems(w2, "v");
		if (ArrayUtils.intersects(syns1, syns2))
			return true;
		
		return false;
	}
}
