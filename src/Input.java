package casino.blackjack;

import java.util.*;

public class Input {

	private static Scanner in = new Scanner(System.in);

	public static long inputLong() {
		String input = in.nextLine();
		try {
			long num = Long.parseLong(input);
			return num;
		}
		catch (Exception ex) {
			System.out.print("You have to input a long integer number, try again: ");
			return inputLong();
		}
	}
	
	public static int inputInt() {
		String input = in.nextLine();
		try {
			int num = Integer.parseInt(input);
			return num;
		}
		catch (Exception ex) {
			System.out.print("You have to input an integer number, try again: ");
			return inputInt();
		}
	}
	
	public static int inputMove(Hand player) {
		ArrayList<Card> hand = player.getHand();
		boolean allowSplit = false;
		boolean allowDouble = false;
		System.out.println("INPUT YOUR MOVE: ");
		System.out.print("1 = Hit, 2 = Stand");
		if (hand.size() == 2 && player.getSplitHand().size() == 0) {
			System.out.print(", 3 = Double Down");
			allowDouble = true;
			if (hand.get(0) == hand.get(1)) {
				System.out.print(", 4 = Split");
				allowSplit = true;
			}
		}
		
		System.out.println();
		int code = inputInt();
		if (allowSplit && allowDouble)
			while (code < 1 || code > 4) {
				System.out.print("Invalid input, try again: ");
				code = inputInt();
			}
		else if (!allowSplit && allowDouble)
			while (code < 1 || code > 3) {
				System.out.print("Invalid input, try again: ");
				code = inputInt();
			}
		else if (!allowSplit && !allowDouble)
			while (code < 1 || code > 2) {
				System.out.print("Invalid input, try again: ");
				code = inputInt();
			}
		
		switch (code) {
			case 1:
				player.hit();
				break;
			case 2:
				player.stand();
				break;
			case 3:
				player.doubleDown();
				break;
			case 4:
				player.split();
		}
		return code;
	}
	
	public static int inputSplitMove(Hand player) {
		System.out.println("INPUT YOUR SPLIT HAND MOVE: ");
		System.out.print("1 = Hit, 2 = Stand");
		int code = Input.inputInt();
		while (code < 1 && code > 2) {
			System.out.print("Invalid input, try again: ");
			code = Input.inputInt();
		}
		
		switch (code) {
			case 1:
				player.splitHit();
				break;
			case 2:
				player.stand();
		}
		return code;
	}
	
	public static boolean inputYesNo() {
		String yn = in.nextLine();
		yn = yn.toLowerCase();
		while (!yn.equals("y") && !yn.equals("n")) {
			System.out.print("Invalid input, try again: ");
			yn = in.nextLine();
			yn = yn.toLowerCase();
		}
		
		return (yn.equals("y"));
	}
}
