/*
 * Created separate fields and methods for original hand
 * and additional hand after splitting.
 * Looks a little ugly; probably could be improved.
 * Also went a bit crazy with the usage of "this" keyword. Not sure why
 */

package casino.blackjack;

import java.util.*;
import static casino.blackjack.Deck.takeCard;

public class Hand {
	/*
	 * Fields
	 */
	private long balance;
	private long currentBet;
	private int score;
	private ArrayList<Card> hand = new ArrayList<>();
	private int splitScore;
	private ArrayList<Card> splitHand = new ArrayList<>();
	public static final int BLACKJACK = 21;

	/*
	 * Constructors
	 */
	// Regular player constructor
	public Hand() {
		System.out.print("Input your starting balance: ");
		long input = Input.inputLong();
		while (input <= 0) {
			System.out.print("Balance must be a positive number: ");
			input = Input.inputLong();
		}
		this.balance = input;
	}

	// Dealer constructor. Arg value TRUE will create a dealer hand
	// False is identical to player constructor
	public Hand(boolean dealer) {
		if (dealer) {
			this.balance = -1;
			this.currentBet = 0;
		}
		else {
			System.out.print("Input your starting balance: ");
			long input = Input.inputLong();
			while (input <= 0) {
				System.out.print("Balance must be a positive number: ");
				input = Input.inputLong();
			}
			this.balance = input;
		}
	}

	/*
	 * Getters
	 */
	public int getScore() {
		return this.score;
	}
	
	public int getSplitScore() {
		return this.splitScore;
	}
	
	public long getBet() {
		return this.currentBet;
	}

	public ArrayList<Card> getHand() {
		return this.hand;
	}
	
	public ArrayList<Card> getSplitHand() {
		return this.splitHand;
	}
	
	public long getBalance() {
		return this.balance;
	}
	
	public void printBalance() {
		System.out.printf("You currently have %d credits\n", this.balance);
	}
	
	public String getHandString() {
		return this.hand.toString().replace("[", "").replace("]", "");
	}
	
	public String getSplitHandString() {
		return this.splitHand.toString().replace("[", "").replace("]", "");
	}

	/*
	 * Methods
	 */
	// Initial game method
	public void deal() {
		this.hand.clear();
		this.score = 0;
		this.splitScore = 0;
		if (this.balance != -1) {
			this.printBalance();
			System.out.print("Input your bet: ");
			long bet = Input.inputLong();
			while (balance < bet) {
				System.out.print("Insufficient funds, input lower bet: ");
				bet = Input.inputLong();
			}
			this.currentBet = bet;
			this.balance -= bet;
			this.printBalance();
		}
		for (int i = 0; i < 2; i++) {
			Card card = takeCard();
			this.score += card.getScore();
			this.hand.add(card);
		}
		this.checkAce();
	}

	public void hit() {
		Card card = takeCard();
		this.score += card.getScore();
		this.hand.add(card);
		this.checkAce();
	}
	
	// Returns boolean value to check if doubling down has been successfull
	public boolean doubleDown() {
		if (this.balance < this.currentBet) {
			System.out.println("Insufficient funds to double down on this bet.");
			return false;
		}
		Card card = takeCard();
		this.score += card.getScore();
		this.hand.add(card);
		this.balance -= this.currentBet;
		this.currentBet *= 2;
		this.checkAce();
		return true;
	}
	
	// Returns boolean value to check if splitting has been successfull
	public boolean split() {
		if (this.balance < this.currentBet) {
			System.out.println("Insufficient funds to split this bet.");
			return false;
		}
		Card card = hand.get(0);
		hand.remove(0);
		splitHand.add(card);
		this.score /= 2;
		this.splitScore = this.score;
		this.balance -= this.currentBet;
		this.currentBet *= 2;
		hit();
		splitHit();
		return true;
	}
	
	public void splitHit() {
		Card card = takeCard();
		this.splitScore += card.getScore();
		this.splitHand.add(card);
		this.checkAceSplit();
	}

	public void stand() {
		return;
	}
	
	public void checkAce() {
		if (this.score > 21 && hand.contains(Card.ACE)) {
			this.score -= 10;
			int i = hand.indexOf(Card.ACE);
			hand.remove(i);
			hand.add(i, Card.ACE1);
		}
	}
	
	public void checkAceSplit() {
		if (this.splitScore > 21 && splitHand.contains(Card.ACE)) {
			this.splitScore -= 10;
			int i = hand.indexOf(Card.ACE);
			hand.remove(i);
			hand.add(i, Card.ACE1);
		}
	}
	
	// Returns -1 or 1 if the result is bust or blackjack
	// Returns score otherwise
	public int checkScore() {
		if (this.score > BLACKJACK)
			return -1;
		else if (this.score < BLACKJACK)
			return this.score;
		else
			return 1;
	}
	
	public int checkScoreSplit() {
		if (this.splitScore > BLACKJACK)
			return -1;
		else if (this.splitScore < BLACKJACK)
			return this.splitScore;
		else
			return 1;
	}
	
	// Boolean arg is in case player got a BJ after initial dealing
	public void payout(boolean blackjack) {
		if (blackjack)
			this.balance += (long)(this.currentBet * 2.5);
		else
			this.balance += this.currentBet << 1;
	}
	
	public void tie() {
		this.balance += this.currentBet;
	}
	
}
