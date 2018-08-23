package second.model.weightFunctions;

import java.util.ArrayList;

import common.model.Reference;

public class WeightFunctionAllSimple extends WeightFunction {
	
	/*
	 * Class implements case d-a. 
	 * It searches for the word, it's synonyms and it's meronyms.
	 * To find words does a basic equality between strings.
	 * 
	 * OK - it may provide a good number of references, but also 
	 * 			it may provide too few references
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
		
		// search for all meronymes
		String[] meronyms = wordNet.wn.getAllMeronyms(word, "n");
		if (meronyms != null) {
			for (int i=0; i < meronyms.length; i++) {
				pos = findSimpleWordReferences(meronyms[i], words);
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
			
		meronyms = wordNet.wn.getAllMeronyms(word, "v");
		if (meronyms != null) {
			for (int i=0; i < meronyms.length; i++) {
				pos = findSimpleWordReferences(meronyms[i], words);
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
		return false;
	}
}
