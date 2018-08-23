package first.model;
import java.util.HashMap;
import java.util.Vector;
import java.util.ArrayList;
import NegEx.GenNegEx;


public class WordClass {
	public String word;
	public String sentiment;
	public Double positiveTotal;
	public Double negativeTotal;
	public Double objectiveTotal;
	public Double score;
	public HashMap<String, Vector<Double>> synsets;
	
	private int added = 0;
	
	public WordClass(String wrd) {
		word = wrd;
		sentiment = "";
		positiveTotal = new Double(0);
		negativeTotal = new Double(0);
		objectiveTotal = new Double(0);
		score = new Double(0);
		synsets = new HashMap<String, Vector<Double>>();
	}
	
	public void addSynset(String syn, Double positive, Double negative) {
		Double objective = 1 - positive - negative;
		positiveTotal += positive;
		negativeTotal += negative;
		objectiveTotal += objective;
		
		Vector<Double> values = new Vector<Double>();
		values.add(0, positive);
		values.add(1, negative);
		values.add(2, objective); 
		synsets.put(syn, values);
		
		added++;
	}
	
	public Double getPositive() {
		int num = Math.max(1, synsets.size());
		return positiveTotal/num;
	}

	public Double getNegative() {
		int num = Math.max(1, synsets.size());
		return negativeTotal/num;
	}
	
	public Double getObjective() {
		int num = Math.max(1, synsets.size());
		return objectiveTotal/num;
	}

	public Double getScore() {
		int num = Math.max(1, synsets.size());
		if (score.doubleValue() == 0) {
			score = (positiveTotal - negativeTotal)/num;
		}
		return score;
	}
	
	public Double getPositive(String synset) {
		return synsets.get(synset).elementAt(0);
	}

	public Double getNegative(String synset) {
		return synsets.get(synset).elementAt(1);
	}
	
	public Double getObjective(String synset) {
		return synsets.get(synset).elementAt(2);
	}
	
	public Double getScore(String synset) {
		Double score = synsets.get(synset).elementAt(0) - synsets.get(synset).elementAt(1);
		return score;
	}

	public boolean isNegated(String word, String sentence, ArrayList rules)
	{
		GenNegEx g 				= new GenNegEx();
		String buffer = "[NEGATED]"+word;
		String afterNegCheck = "";
		
		try {
			afterNegCheck = g.negCheck(sentence, word, rules, true);
		} catch (Exception e) {
			System.out.println("Phrase was not parsed successfully for negation detection\n");
			e.printStackTrace();
		}
		if (afterNegCheck.contains(buffer))
			return true;
		else
			return false;
	}
}
