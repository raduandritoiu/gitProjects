import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import rita.*;
import rita.wordnet.*;

public class SentiWN {
	private String pathToSWN = "./bin/data/SentiWordNet_1.0.1.txt";
	private HashMap<String, WordClass> _dict;
	public RiWordnet ritaWN;
	public Boolean addNewWords;
	
	public SentiWN() {
		this(new RiWordnet());
	}
	
	public SentiWN(RiWordnet rwn) {
		System.out.println("mumu");
		
		_dict = new HashMap<String, WordClass>();
		ritaWN = rwn;
		addNewWords = false;
		
		Double positive;
		Double negative;
		WordClass wordObj;
				
		try{
			BufferedReader csv =  new BufferedReader(new FileReader(pathToSWN));
			String line = "";			
			while((line = csv.readLine()) != null)
			{
				if (line.charAt(0) == '#') {
					continue;
				}
				String[] data = line.split("\t");
				
				positive = Double.parseDouble(data[2]);
				negative = Double.parseDouble(data[3]);
				
				String[] words = data[4].split(" ");

				for(String word:words)
				{
					String[] w_n = word.split("#");
					if (_dict.containsKey(w_n[0])) {
						wordObj = _dict.get(w_n[0]);
					}
					else {
						wordObj = new WordClass(w_n[0]);	
					}
					wordObj.addSynset(word, positive, negative);
					_dict.put(w_n[0], wordObj);
				}
			}
		}
		catch(Exception e){e.printStackTrace();}		
	}
	
	public Double getPositive(String word) {
//		WordClass wordObj = _dict.get(word);
		WordClass wordObj = getWord(word);
		return wordObj.getPositive();
	}

	public Double getNegative(String word) {
//		WordClass wordObj = _dict.get(word);
		WordClass wordObj = getWord(word);
		return wordObj.getNegative();
	}
	
	public Double getObjective(String word) {
//		WordClass wordObj = _dict.get(word);
		WordClass wordObj = getWord(word);
		return wordObj.getObjective();
	}

	public Double getScore(String word) {
//		WordClass wordObj = _dict.get(word);
		WordClass wordObj = getWord(word);
		return wordObj.getScore();
	}

	public Double getSynPositive(String synset) {
		String[] word = synset.split("#");
//		WordClass wordObj = _dict.get(word[0]);
		WordClass wordObj = getWord(word[0]);
		return wordObj.getPositive(synset);
	}

	public Double getSynNegative(String synset) {
		String[] word = synset.split("#");
//		WordClass wordObj = _dict.get(word[0]);
		WordClass wordObj = getWord(word[0]);
		return wordObj.getNegative(synset);
	}
	
	public Double getSynObjective(String synset) {
		String[] word = synset.split("#");
//		WordClass wordObj = _dict.get(word[0]);
		WordClass wordObj = getWord(word[0]);
		return wordObj.getObjective(synset);
	}
	
	public Double getSynScore(String synset) {
		String[] word = synset.split("#");
//		WordClass wordObj = _dict.get(word[0]);
		WordClass wordObj = getWord(word[0]);
		return wordObj.getScore(synset);
	}
	
	public WordClass getWord(String word){
		WordClass wordObj = _dict.get(word);
		if (wordObj == null) {
			if (addNewWords) {
				wordObj = findNewWord(word);
			}
			else
				wordObj = new WordClass(word);
		}
		return wordObj;
	}
	
	
	public WordClass findNewWord(String word) {
		WordClass wordObj = new WordClass(word);
		wordObj.addSynset(word+"#n#2", new Double(0), new Double(0));
		
		Double positive = new Double(0);
		Double negative = new Double(0);
		int synCnt = 0;		
		String[] synonims;

		synonims = ritaWN.getAllSynonyms(word, "n");
		if (synonims.length > 0) {
			synCnt = 0;
			positive = new Double(0);
			negative = new Double(0);
			for (String syn:synonims) {
				positive += getPositive(syn);
				negative += getNegative(syn);
				synCnt++;
			}
			wordObj.addSynset(word+"#n#1", positive/synCnt, negative/synCnt);
		}
		
		synonims = ritaWN.getAllSynonyms(word, "a");
		if (synonims.length > 0) {
			synCnt = 0;
			positive = new Double(0);
			negative = new Double(0);
			for (String syn:synonims) {
				positive += getPositive(syn);
				negative += getNegative(syn);
				synCnt++;
			}
			wordObj.addSynset(word+"#a#1", positive/synCnt, negative/synCnt);
		}
		
		synonims = ritaWN.getAllSynonyms(word, "v");
		if (synonims.length > 0) {
			synCnt = 0;
			positive = new Double(0);
			negative = new Double(0);
			for (String syn:synonims) {
				positive += getPositive(syn);
				negative += getNegative(syn);
				synCnt++;
			}
			wordObj.addSynset(word+"#v#1", positive/synCnt, negative/synCnt);
		}
		
		synonims = ritaWN.getAllSynonyms(word, "r");
		if (synonims.length > 0) {
			synCnt = 0;
			positive = new Double(0);
			negative = new Double(0);
			for (String syn:synonims) {
				positive += getPositive(syn);
				negative += getNegative(syn);
				synCnt++;
			}
			wordObj.addSynset(word+"#r#1", positive/synCnt, negative/synCnt);
		}

		return wordObj;
	}
	
}
