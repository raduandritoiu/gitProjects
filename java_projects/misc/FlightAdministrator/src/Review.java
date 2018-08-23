import java.util.ArrayList;
import java.util.Date;



public class Review {
	public String text;
	public Date timeStamp;
	public String author;
	public Double polarity;
	public ArrayList<String> features;
	
	
	public Review() { 
		text = "None";
		author = "anonimus";
		timeStamp = new Date();
		features = new ArrayList<String>();
	}
	
	public void addfeature(String feat) {
		features.add(feat);
	}
}
