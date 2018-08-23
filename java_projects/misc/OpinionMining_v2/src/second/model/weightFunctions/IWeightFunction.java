package second.model.weightFunctions;

import java.util.ArrayList;

import second.model.Term;

import common.model.Opinion;
import common.model.Reference;

public interface IWeightFunction {
	public double[] calculateWeights(ArrayList<Term> terms, Opinion op);
	public double[] calculateTermsWeight(ArrayList<Term> terms, ArrayList<String> words);
	public double calculateTermWeight(Term term, ArrayList<String> words);
	
	/* Returns an array of points where x means the location where the word was found ant y is how relevant the 
	 * the finding was (if it was the actual word or one of it's synonyms or a synonym of a synonym and so on)
	 */
	public ArrayList<Reference> findWordReferences(String word, ArrayList<String> words);
	
	/*Returns an array of positions where this word is found.
	 */
	public ArrayList<Integer> findSimpleWordReferences(String word, ArrayList<String> words);
	
	public double calculateWordWeight(int wordPos, ArrayList<String> words);
	public boolean wordsMatch(String w1, String w2);	
}
