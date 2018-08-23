package second.model.weightFunctions;

import java.util.ArrayList;

import utils.ArrayUtils;

import common.model.Reference;

public class WeightFunctionSynsStems extends WeightFunction {
	
	/*
	 * Class implements case b-c. 
	 * It searches for the word and it's synonyms.
	 * To find words does the intersection between the stems of the words.
	 * 
	 * GOOD - this maybe will provide good amount of references.
	 */
	
	public ArrayList<Reference> findWordReferences(String word, ArrayList<String> words) {
		ArrayList<Reference> refs = new ArrayList<Reference>();
		ArrayList<Integer> pos; 
		
		pos = findSimpleWordReferences(word, words);
		for (int j=0; j < pos.size(); j++) {
			Reference ref = new Reference(pos.get(j), 1);
			refs.add(ref);
		}
		
		
		String[] synonyms = wordNet.wn.getAllSynonyms(word, "n");
		if (synonyms != null) {
			for (int i=0; i < synonyms.length; i++) {
				pos = findSimpleWordReferences(synonyms[i], words);
				for (int j=0; j < pos.size(); j++) {
					// check to see if position already added
					boolean found = false;
					for (Reference ref : refs) {
						if (ref.x == pos.get(j))
							found = true;
					}
					if (!found) {
						Reference ref = new Reference(pos.get(j), 1);
						refs.add(ref);
					}
				}
			}
		}
			
		synonyms = wordNet.wn.getAllSynonyms(word, "v");
		if (synonyms != null) {
			for (int i=0; i < synonyms.length; i++) {
				pos = findSimpleWordReferences(synonyms[i], words);
				for (int j=0; j < pos.size(); j++) {
					// check to see if position already added
					boolean found = false;
					for (Reference ref : refs) {
						if (ref.x == pos.get(j))
							found = true;
					}
					if (!found) {
						Reference ref = new Reference(pos.get(j), 1);
						refs.add(ref);
					}
				}
			}
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
