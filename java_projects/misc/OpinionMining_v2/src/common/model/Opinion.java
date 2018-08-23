package common.model;

import java.util.ArrayList;


public class Opinion {
	public String author;
	public String date;
	public String text;
	public ArrayList <String> words; 
	
	public double polarity;
	public int preclassification;
	public int classification;
	public double[] vector;
	public int genClass;
	public ArrayList classifications;
	
	
	public Opinion() {
		//constructor
	}
	
	
	public Opinion(String nAuthor, String nDate, String nText) {
		author = nAuthor;
		date = nDate;
		text = nText;
		preclassification = -1;
		classification = -1;
	}
	
	
	public void addWeight(double weight, int position) {
		vector[position] = weight;
	}
	
	
	public void separateWords() {
		words = new ArrayList <String> ();
		String tmpText = new String(text);
		String[] temp = tmpText.split("[ \\.,\n\t\\?\\!]");
		for (int i=0; i < temp.length; i++) {
			if (!temp[i].equals("")) {
				words.add(temp[i].toLowerCase());
			}
		}
	}
	
	
	public void print() {
		System.out.println(" " + author + "\t\t" + date);
		// print max 150 characters
		int len = Math.min(60, text.length());
		System.out.println(text.substring(0, len) + " ...");
		System.out.println("");
	}
}
