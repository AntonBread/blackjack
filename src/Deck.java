package casino.blackjack;

import java.util.*;

public class Deck {
	
	public static ArrayList<Card> deck = new ArrayList<>(52);
	
	public static void refreshDeck() {
		deck.clear();
		deck.ensureCapacity(52);
		
		for (Card card : Card.getArr()) {
			for (int i = 0; i < 4; i++)
				deck.add(card);
		}
		Collections.shuffle(deck);
	}
	
	public static Card takeCard() {
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}
	
}