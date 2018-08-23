import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

import NegEx.GenNegEx;
import NegEx.Sorter;

import rita.wordnet.*;
import rita.*;

import java.io.*;
import java.util.*;

public class MainClass {
	public static void main(String args[]) {
		System.out.println("start!\n");
		
		
		RiWordnet wordnet = new RiWordnet();  
	    SentiWN swn = new SentiWN(wordnet);
		System.out.println("Informal - " + swn.getScore("Informal"));
		System.out.println("zxcxzc - " + swn.getScore("alksjdhsak"));
		
		// Generating the rules for negation detection
		GenNegEx g 			= new GenNegEx();
		String fillerString		= "_";
		boolean negatePossible		= true;
		String triggersFile		= "D:\\Work\\Eclipse\\opinionMining\\src\\NegEx\\negex_triggers.txt";	
        File ruleFile           = new File(triggersFile);
		try {
			Scanner sc 		= new Scanner(ruleFile);
			ArrayList rules	= new ArrayList();
			ArrayList sortedRules	= new ArrayList();
			String afterNegCheck		= "";
			String sentence = "The phone is not beautiful.";

			while (sc.hasNextLine()) {
				rules.add(sc.nextLine());
			}
			sortedRules = Sorter.sortRules(rules);
			System.out.println("asdsadsa\n");
			
			/* ------ testing negation detection
			try {
				afterNegCheck = g.negCheck(sentence, "beautiful", sortedRules, negatePossible);
				System.out.println("results:"+afterNegCheck+"\n");
			}
			catch (Exception e) {
				System.out.println(e);
			}
			*/
			sc.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/* ========= Parsing the XML file ========= */
		InputDocumentParser prs = new InputDocumentParser();
		prs.parseDocument();
		for(Opinion op : prs.myOpinions){
			System.out.println("result: " + op.text + "\n");
		}
	}
}
