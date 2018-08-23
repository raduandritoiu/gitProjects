package second.model.weightFunctions;

import java.util.ArrayList;

import common.model.Reference;


public class WeightFunctionMeronyms extends WeightFunction implements IWeightFunction {

	
	public ArrayList<Reference> findWordReferences(String word, ArrayList<String> words) {
		ArrayList<Reference> refs = new ArrayList<Reference>();
		ArrayList<Integer> pos; 
		
		pos = findSimpleWordReferences(word, words);
		for (int j=0; j < pos.size(); j++) {
			Reference ref = new Reference(pos.get(j), 1);
			refs.add(ref);
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

		return refs;
	}
}
