package components;

import java.util.ArrayList;

public class Hand {
	ArrayList<Card> CardList = new ArrayList<Card>(7);
	
	private int rank;
	private int HighCard, LowCard;
	
	public void addCard(Card a){	
		boolean CardAdded = false;
		
		for(int i = 0 ; i < CardList.size() ; i++){
			if( CardList.get(i).getNumber() < a.getNumber() ){
				CardList.add( i, a);
				CardAdded = true;
				break;
			}
		}
		if( !CardAdded ) CardList.add(a);
	}
	
	public void CalculateRank(){
		boolean isStraight = false;
		boolean isStraightFlush = false;
		
		int consecutiv;
		
		//Straight
		for(int i = 0 ; i < 3 ; i++){
			consecutiv = 1;
			
			for(int j = 0 ; i+j < 6 ; j++){
				if ( CardList.get(i+j).getNumber() > CardList.get(i+j+1).getNumber() + 1 ) break; //nu sunt egale sau consecutive 
				if ( CardList.get(i+j).getNumber() == CardList.get(i+j+1).getNumber() + 1 ) consecutiv++;
			}
			if (consecutiv >= 5){
				isStraight = true;
				HighCard = CardList.get(i).getNumber();
				
				isStraightFlush = true;
				for(int j = 1 ; j < 5 ; j++){
					if( CardList.get(i).getColour().compareTo( CardList.get(i+j).getColour() ) == 0 ){
						isStraightFlush = false;
						break;
					}
				}
				
				break;
			}
		}
				
		if(isStraightFlush){
			rank = 1;
			return ;
		}
		
		//Flush
		int nrClubs = 0, nrHearts = 0, nrSpades = 0, nrDiamonds = 0;
		for(int i = 0 ; i < 7 ; i++){
			if( CardList.get(i).getColour().compareTo("CLUBS") == 0 ) nrClubs++;
			else if( CardList.get(i).getColour().compareTo("SPADES") == 0 ) nrSpades++;
			else if( CardList.get(i).getColour().compareTo("HEARTS") == 0 ) nrHearts++;
			else if( CardList.get(i).getColour().compareTo("DIAMONDS") == 0 ) nrDiamonds++;
		}
	}
	
}
