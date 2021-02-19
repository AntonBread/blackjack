package casino.blackjack;

import static casino.blackjack.Deck.refreshDeck;
import static casino.blackjack.Input.inputMove;
import static casino.blackjack.Input.inputSplitMove;
import static casino.blackjack.Input.inputYesNo;

public class Game {

	public static void main(String[] args) {
		Hand player = new Hand();
		Hand dealer = new Hand(true);
		boolean running = true;

		while (running) {
			gameCycle(player, dealer);
			
			if (player.getBalance() <= 0) {
				System.out.println("You ran out of credits and cannot continue playing.");
				break;
			}
			System.out.println("PLAY AGAIN? (Y/N): ");
			running = inputYesNo();
		}
	}

	public static void gameCycle(Hand player, Hand dealer) {
		System.out.println("\n\n");
		refreshDeck();
		player.deal();
		dealer.deal();
		
		if (dealer.checkScore() == 1) {
			System.out.printf("\nYOUR HAND: %s (%d)\t\tDEALER'S HAND: %s\n", player.getHandString(), player.getScore(), dealer.getHandString());
			System.out.println("BLACKJACK, HOUSE WINS!");
			player.printBalance();
			return;
		}
		
		System.out.printf("\nYOUR HAND: %s (%d)\t\tDEALER'S CARD: %s\n", player.getHandString(), player.getScore(),
				dealer.getHand().get(1));
		if (player.checkScore() == 1) { // Auto win if player got the BJ
			System.out.println("BLACKJACK!");
			player.payout(true);
			player.printBalance();
			return;
		}
		
		boolean  split = false; // Flag if player chose to split
		while (true) {
			int move = inputMove(player);
			if (move == 2 && !split)
				break;
			else if (move == 4) {
				split = true;
				System.out.printf("YOUR HAND: %s (%d)\n", player.getHandString(), player.getScore());
				System.out.printf("YOUR SPLIT HAND: %s (%d)\n", player.getSplitHandString(), player.getSplitScore());
				continue;
			}
			
			if (split) {
				int splitMove = inputSplitMove(player);
				if (splitMove == 2)
					split = false;
			}
			
			if (move == 3 || move == 4)
				player.printBalance();
			
			System.out.printf("YOUR HAND: %s (%d)\n", player.getHandString(), player.getScore());
			if (player.getSplitHand().size() > 0)
				System.out.printf("YOUR SPLIT HAND: %s (%d)\n", player.getSplitHandString(), player.getSplitScore());
			
			if (player.checkScore() == -1 || player.checkScoreSplit() == -1) {
				System.out.println("YOU'RE BUST!");
				return;
			}
		}
		
		while (dealer.getScore() < 17)
			dealer.hit();
		System.out.printf("\nDEALER'S HAND: %s (%d)\n", dealer.getHandString(), dealer.getScore());
		if (dealer.checkScore() == -1) {
			System.out.println("DEALER'S BUST!");
			player.payout(false);
			player.printBalance();
			return;
		}
		
		int pScore = player.checkScore();
		int pSplitScore = 0;
		if (player.getSplitHand().size() > 0)
			pSplitScore = player.checkScoreSplit();
		int dScore = dealer.checkScore();
		
		if ((pScore == 1 || pSplitScore == 1) && dScore != 1) {
			System.out.println("YOU WON!");
			player.payout(false);
			player.printBalance();
		}
		else if (pScore > dScore || pSplitScore > dScore) {
			System.out.println("YOU WON!");
			player.payout(false);
			player.printBalance();
		}
		else if (dScore == 1 && pScore != 1 && pSplitScore != 1) {
			System.out.println("HOUSE WINS!");
			player.printBalance();
		}
		else if (dScore > pScore && dScore > pSplitScore) {
			System.out.println("HOUSE WINS!");
			player.printBalance();
		}
		else if (dScore == pScore) {
			if (pSplitScore > 0 && dScore == pSplitScore) {
				System.out.println("TIE");
				player.tie();
				player.printBalance();
			}
			else if (pSplitScore > 0 && dScore > pSplitScore)
				System.out.println("HOUSE WINS!");
			else if (pSplitScore <= 0) {
				System.out.println("TIE");
				player.tie();
				player.printBalance();
			}
		}
	}

}
