package casino.blackjack;

public enum Card {
	TWO,
	THREE,
	FOUR,
	FIVE,
	SIX,
	SEVEN,
	EIGHT,
	NINE,
	TEN,
	JACK,
	QUEEN,
	KING,
	ACE,
	ACE1; // Not a "real" card, used only to indicate that an ace represents a score of 1
	
	// Aces always return the score of 11
	// Further checks are done in the "Hand" class
	public int getScore() {
		int i = this.ordinal();
		if (i >= 0 && i <= 8)
			return i + 2;
		else if (i >= 9 && i <= 11)
			return 10;
		else
			return 11;
	}
	
	public static Card[] getArr() {
		Card[] arr = {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};
		return arr;
	}
}
