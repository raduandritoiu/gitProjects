package second.model.weightFunctions;

import java.util.ArrayList;

import second.model.Term;
import utils.ArrayUtils;

import common.model.Opinion;
import common.model.Reference;
import common.model.WordNetSingleton;

import first.model.SentiWN;
import first.model.WordClass;


public class WeightFunction implements IWeightFunction{
	protected SentiWN sentiWn;
	protected WordNetSingleton wordNet;
	private int range;
	
	public WeightFunction() {
		sentiWn = SentiWN.getInstance();
		wordNet = WordNetSingleton.getInstance();
		range = 5;
	}
	
	
	public double[] calculateWeights(ArrayList<Term> terms, Opinion op) {
		return calculateTermsWeight(terms, op.words);
	}
	

	public double[] calculateTermsWeight(ArrayList<Term> terms, ArrayList<String> words) {
		double[] weights = new double[terms.size()];
		for (int i=0; i < terms.size(); i++) {
			double weight = calculateTermWeight(terms.get(i), words);
			weights[i] = weight;
		}
		
		return weights;
	}
	
	
	public double calculateTermWeight(Term term, ArrayList<String> words) {
		double totalWeight = 0.0;
		
		// some of the positions found for different words of the term overlap 
		// so the value is calculated more than one for an occurence - so first find all positions then calculate
		ArrayList<Reference> references = new ArrayList<Reference>();
		
		for (int i=0; i < term.words.size(); i++) {
			ArrayList<Reference> refs = findWordReferences(term.words.get(i), words);
			for (Reference newRef : refs) {
				// check to see if position already added
				boolean found = false;
				for (Reference ref : references) {
					if (ref.x == newRef.x)
						found = true;
				}
				if (!found) {
					references.add(newRef);
				}
			}
		}
		
		for (int j=0; j < references.size(); j++) {
			double weight = calculateWordWeight(references.get(j).x, words);
			// i don't care about the relevance of the word reference found - no scaling 
			totalWeight += weight;
		}
		
		// I don't care how many words are there to describe a term - no scaling 
		return totalWeight;
	}
	
	
	public double calculateWordWeight(int wordPos, ArrayList<String> words) {
		double weight = 0.0;
		
		int start 	= Math.max(wordPos - range, 0);
		int stop 	= Math.min(wordPos + range, words.size());
		for (int i=start; i <stop; i++) {
			WordClass word = sentiWn.getWord(words.get(i));
			
			// check if the word is positive or negative (which value is bigger)
			// add half of the objectivity to it and if it negative change the sign
			// also multiply it by 10 - SentiWordNet gives values between [0, 1] and for reading purposes use values * 10
			double val;
			if (word.negativeTotal > word.positiveTotal) {
				val = - word.getNegative() * 10;// - word.getObjective() / 2;
			}
			else {
				val = word.getPositive() * 10;// + word.getObjective() / 2;
			}
			weight += val;
		}
		
		// I don't care if the range is not complete (the beginning or end of the text is near) - no scaling 
		return weight;
	}

	
	/* Returns an array of points where x means the location where the word was found ant y is how relevant the 
	 * the finding was (if it was the actual word or one of it's synonyms or a synonym of a synonym and so on)
	 */
	public ArrayList<Reference> findWordReferences(String word, ArrayList<String> words) {
		ArrayList<Reference> refs = new ArrayList<Reference>();
		ArrayList<Integer> pos; 
		
		pos = findSimpleWordReferences(word, words);
		for (int j=0; j < pos.size(); j++) {
			Reference ref = new Reference(pos.get(j), 1);
			refs.add(ref);
		}
		
		// search for all meronymes
//		String[] meronyms = wordNet.wn.getAllMeronyms(word, "n");
		String[] meronyms = wordNet.wn.getAllSynonyms(word, "n");
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
			
		meronyms = wordNet.wn.getAllMeronyms(word, "n");
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
		
		meronyms = wordNet.wn.getAllSynonyms(word, "v");
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
	
	
	/*Returns an array of positions where this word is found.
	 */
	public ArrayList<Integer> findSimpleWordReferences(String word, ArrayList<String> words) {
		ArrayList<Integer> refs = new ArrayList<Integer>();
		for (int i=0; i < words.size(); i++) {
			if (wordsMatch(word, words.get(i))) {
				refs.add(i);
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
		
//		syns1 = wordNet.wn.getAllSynonyms(w1, "n");
//		syns2 = wordNet.wn.getAllSynonyms(w2, "n");
//		if (ArrayUtils.intersects(syns1, syns2))
//			return true;
//		
//		syns1 = wordNet.wn.getAllSynonyms(w1, "v");
//		syns2 = wordNet.wn.getAllSynonyms(w2, "v");
//		if (ArrayUtils.intersects(syns1, syns2))
//			return true;
//		
//		syns1 = wordNet.wn.getAllSynonyms(w1, "a");
//		syns2 = wordNet.wn.getAllSynonyms(w2, "a");
//		if (ArrayUtils.intersects(syns1, syns2))
//			return true;
//		
//		syns1 = wordNet.wn.getAllSynonyms(w1, "r");
//		syns2 = wordNet.wn.getAllSynonyms(w2, "r");
//		if (ArrayUtils.intersects(syns1, syns2))
//			return true;
		
		return false;
	}
}
