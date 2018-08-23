package second.model.weightFunctions;

import java.util.ArrayList;

import common.model.Reference;


public class WeightFunctionSimple extends WeightFunction {

	/*
	 * Class implements case a-a. 
	 * It searches only for the word.
	 * To find words does only a standard equality between strings.
	 * 
	 * (BAD -  will not work because won't find any references)
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
		return false;
	}

}
