package second.model;

import java.util.ArrayList;

public class Term {
	public ArrayList<String> words;
	public String term;
	
	public Term() {
	}
	
	public Term(String initTerm) {
		term = initTerm;
		words = new ArrayList<String>();
		words.add(initTerm);
	}
}
