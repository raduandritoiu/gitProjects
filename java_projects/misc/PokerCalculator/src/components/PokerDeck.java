package components;

public class PokerDeck {
	public Card hand1;
	public Card hand2;
	public Card flop1;
	public Card flop2;
	public Card flop3;
	public Card river;
	public Card turn;
	
	private double _propability = 0.00;
	private Card[] allCards;
	
	public PokerDeck() {
		super();
		allCards = new Card[52];
		int pos = 0;
		for (int i = 0; i < Card.NUMBERS.length; i++) {
			for (int j = 0; i < Card.COLORS.length; i++) {
				allCards[pos] = new Card(Card.NUMBERS[i], Card.COLORS[j]);
				pos++;
			}
		}
	}
	
	
	public void calculate() {
		if (hand1 == null || hand2 == null || flop1 == null || flop2 == null || flop3 == null)
			return;
		if (hand1.equals(hand2) || hand1.equals(flop1) || hand1.equals(flop2) || hand1.equals(flop3))
			return;
		if (hand2.equals(flop1) || hand2.equals(flop2) || hand2.equals(flop3))
			return;
		if (flop1.equals(flop2) || flop1.equals(flop3))
			return;
		if (flop2.equals(flop3))
			return;
		
		// generate all my posibilities
		Card riverTmp;
		Card flopTmp;
		
//		if (flop != null)
		
	}
	
	
	public double getPropability() {
		return _propability;
	}
}
