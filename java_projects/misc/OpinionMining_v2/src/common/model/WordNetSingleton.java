package common.model;

import rita.wordnet.RiWordnet;

public class WordNetSingleton {
	public RiWordnet wn;
	
	private WordNetSingleton() {
		wn = new RiWordnet();
	}
	
	
	// Singleton implementation
	static private WordNetSingleton instance;
	static public WordNetSingleton getInstance() {
		if (instance == null) {
			instance = new WordNetSingleton();
		}
		return instance;
	}
}
