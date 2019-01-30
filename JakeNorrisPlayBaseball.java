/*
 * Programmer: Jake Norris
 * Course: CS 212.01
 * Assignment: Project 3
 * Date: 10/31/2018
 */
import java.util.*;

public class JakeNorrisPlayBaseball {
	public static void main( String[] args ) {
		
		// Initialize necessary objects and variables
		Scanner scan = new Scanner( System.in );
		boolean validInput = false;
		String awayTeam = "";
		String homeTeam = "";
		String instructions = ("0 -> Single\n1 -> Double\n2 -> Triple\n3 -> Home Run\n4 -> Ball\n5 -> Strike" + 
								"\n6 -> Foul Ball");
		int nextInput = -1;
		boolean currentInning = true;
		
		// Introduce the game
		System.out.println("Welcome to the baseball simulator!\nTo get started, enter the names of both teams.\n");
		
		// Get team names
		System.out.print("Enter the away team's name >> ");
		awayTeam = scan.nextLine();
		System.out.print("\nEnter the home team's name >> ");
		homeTeam = scan.nextLine();
		
		// Initialize the game
		JakeNorrisBaseball game = new JakeNorrisBaseball(awayTeam, homeTeam);
		
		// Display the instructions
		System.out.println("\nTo play the game, enter the following values for each event:");
		System.out.println(instructions);
		System.out.println("If at any point you would like to see these instructions again, enter 'help'.\n");
		
		// Begin the game
		System.out.println(game.toString());
		while(game.checkIfGameOver() == 0) {
			if(game.currentInning() != currentInning) {
				System.out.println("\nThe inning is over! Here is the current score: ");
				System.out.println("\n" + game.toString());
				currentInning = game.currentInning();
			}
			else {
				// Get the user input
				while(!validInput) {
					try {
						System.out.print("\nWhat happens next? >> ");
						nextInput = scan.nextInt();
						if(nextInput >= 0 && nextInput <= 6)
							validInput = true;
					}
					catch (InputMismatchException e) {
						if(scan.nextLine().toLowerCase().equals("help")) 
							System.out.println(instructions);
						else 
							System.out.println();
					}
				}
				validInput = false; // Set valid input back for next run
			
				// Use the user input 
				game.atBat(nextInput);
				System.out.println("\n" + game.toString());
				
			}
		}
		// Announce the results
		game.announceResult();
		scan.close();
	}
	
	
}
