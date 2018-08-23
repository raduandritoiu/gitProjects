package second.model;

import java.util.ArrayList;


public class Domain {
	public String name;
	public ArrayList<Term> terms;
	public ArrayList<String> classes;
	
	
	public Domain (String nName) {
		name = nName;
		terms = new ArrayList<Term>();
		classes = new ArrayList<String>();
	}
	
	
	public int size() {
		if (terms == null)
			return 0;
		else
			return terms.size();
	}
}
