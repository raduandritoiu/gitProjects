package second.model;

import java.util.ArrayList;

import common.model.Opinion;


public class Presentation {
	private AnnotatedSet opSet;
	private Domain domain;
	
	public Presentation(){
	}
	
	
	public void setOpinionSet(AnnotatedSet value) {
		opSet = value;
	}
	
	
	public void setDomain(Domain value) {
		domain = value;
	}
	
	
	public void print() {
		ArrayList<ArrayList<Opinion>> clses = new ArrayList<ArrayList<Opinion>>();
		for (int i=0; i < domain.classes.size(); i++) {
			ArrayList<Opinion> cls = getOpinionsForCategory(i+1);
			clses.add(cls);
		}
		
		// print total
		System.out.println("");
		System.out.println("");
		System.out.println("========== Classification Categories ============================");
		
		int pos = 0;
		int max = -1;
		int avg = 0;
		for (int i=0; i < domain.classes.size(); i++) {
			ArrayList<Opinion> cls = clses.get(i);
			System.out.println("\t\t - " + domain.classes.get(i) + ":   " + cls.size() + " opinions");
			avg = avg + cls.size();
			if (max < cls.size()) {
				max = cls.size();
				pos = i;
			}
		}
		avg = Math.round(avg/clses.size());
		System.out.println("");
		
		System.out.println("--------------------------------------------------");
		System.out.println("Category with most opinions:   " + domain.classes.get(pos) + " - " +  max  + " opinions");
//		System.out.println("Average classification calculated by opinions:   " + domain.classes.get(avg));
		System.out.println("Average category calculated by weights:   " + domain.classes.get((int)opSet.classification - 1));
		System.out.println("=================================================================");
		System.out.println("");
		System.out.println("");
		
		
		// print for each class
		for (int i=0; i < domain.classes.size(); i++) {
			System.out.println("========== " + domain.classes.get(i) + " ====================================");
			ArrayList<Opinion> cls = clses.get(i);
			if (cls.size() > 1) {
				cls.get(0).print();
				
				int len = 6;
				if (len > cls.size())
					len = cls.size();
				for (int j = 1; j < len; j++) {
					System.out.println("--------------------------------------------------");
					cls.get(j).print();
				}
			}
		}
		
		System.out.println("=================================================================");
		System.out.println("");
	}
	
	
	
	private ArrayList<Opinion> getOpinionsForCategory(int i) {
		ArrayList<Opinion> cls = new ArrayList<Opinion>();
		for (Opinion op : opSet.opinions) {
			if (op.classification == i) {
				cls.add(op);
			}
		}
		return cls;
	}
}
