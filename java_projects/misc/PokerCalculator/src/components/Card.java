package components;

public class Card {
	public static final int ONE=1;
	public static final int TWO=2;
	public static final int THREE=3;
	public static final int FOUR=4;
	public static final int FIVE=5;
	public static final int SIX=6;
	public static final int SEVEN=7;
	public static final int EIGHT=8;
	public static final int NINE=9;
	public static final int TEN=10;
	public static final int JACK=11;
	public static final int QWEEN=12;
	public static final int KING=13;
	public static final int ACE=14;
	public static final int[] NUMBERS = {ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QWEEN, KING, ACE};
	public static final String[] NUMBERS_STR = {ONE+"", TWO+"", THREE+"", FOUR+"", FIVE+"", SIX+"", SEVEN+"", EIGHT+"", NINE+"", TEN+"", JACK+"", QWEEN+"", KING+"", ACE+""};
	
	public static final String CLUBS="CLUBS";
	public static final String HEARTS="HEARTS";
	public static final String SPADES="SPADES";
	public static final String DIAMONDS="DIAMONDS";
	public static final String[] COLORS={CLUBS, HEARTS, SPADES, DIAMONDS};
	
	private int number;
	private String colour;
	
	
	
	public Card() {
		super();
	}
	
	public Card(int newNumber, String newColor) {
		super();
		number = newNumber;
		colour = newColor;
	}
	
	public void setColour(String a){
		colour = a;
	}
	public void setNumber(int a){
		number = a;
	}
	public String getColour(){
		return colour;
	}
	public int getNumber(){
		return number;
	}
}
