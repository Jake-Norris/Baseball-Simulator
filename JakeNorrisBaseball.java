/*
 * Programmer: Jake Norris
 * Course: CS 212.01
 * Assignment: Project 3
 * Date: 10/31/2018
 */
public class JakeNorrisBaseball {
	
	// Declare instance variables
	private String team1, team2; // Team 1 is away, team 2 is home
	private int score1, score2, inning, outs, balls, strikes;
	private boolean topInning;
	private boolean [] baserunners = new boolean[3];
	
	// Constructor 
	public JakeNorrisBaseball(String name1, String name2) {
		team1 = name1;
		team2 = name2;
		topInning = true;
		inning = 1;
	}
	
	// Method controls the next event that happens in the game
	public void atBat(int event) {
		if(event == 4 || event == 5 || event == 6) { // Ball or strike or foul
			if( (event == 6 && strikes < 2) || event == 5 )
				updateCount(true);
			else if (event == 4)
				updateCount(false);
		}
		else { // The ball was hit
			moveRunners(event);
		}
	}
	
	// Updates the count 
	private void updateCount( boolean s ) {
		if(s && strikes == 2) {
			strikeout();
		}
		else if(s) {
			strikes++;
		}
		else if(balls == 3) {
			walk();
		}
		else {
			balls++;
		}
	}
	
	// Move the runners 
	private void moveRunners(int hit) {
		resetCount();
		boolean [] temp = {false, false, false};
 		if(hit == 0) { // single
 			temp[0] = true;
 			if(baserunners[2]) {
 				score();
 				temp[2] = false;
 			}
			for(int i = 0; i < baserunners.length - 1; i++) {
				if(baserunners[i])
					temp[i+1] = baserunners[i];
			}
			baserunners = temp;
		}
		else if(hit == 1) { // double
			temp[1] = true;
 			if(baserunners[2] && baserunners[1]) {
 				score();
 				score();
 				temp[2] = false;
 				temp[1] = false;
 			}
 			else if(baserunners[2] || baserunners[1]) {
 				score();
 				temp[2] = false;
 				temp[1] = false;
 			}
			for(int i = 0; i < baserunners.length - 2; i++) {
				if(baserunners[i])
					temp[i+2] = baserunners[i];
			}
			baserunners = temp;
		}
		else if(hit == 2) { // triple  
			for(int i = 0; i < baserunners.length; i++) {
				if(baserunners[i])
					score();
					baserunners[i] = false;
			}
			baserunners[2] = true;
		}
		else { // home run
			score();
			for(int i = 0; i < baserunners.length; i++) {
				if(baserunners[i])
					score();
					baserunners[i] = false;
			}
		}
	}
	
	// Add to the score
	private void score() {
		if(topInning) // The away team scores
			score1++;
		else
			score2++; // The home team scores
	}
	
	// Reset the count
	private void resetCount() {
		balls = 0;
		strikes = 0;
	}
	
	// Walk the batter
	private void walk() {
		resetCount();
		if(baserunners[0] && baserunners[1] && baserunners[2])
			score();
		else if(baserunners[0] && baserunners[1])
			baserunners[2] = true;
		else if(baserunners[0])
			baserunners[1] = true;
		else
			baserunners[0] = true;
	}
	
	// Strike out the batter
	private void strikeout() {
		resetCount();
		outs++;
		if(outs == 3) {
			if(!topInning)
				inning++;
			topInning = !topInning;
			outs = 0;
			for(int i = 0; i < baserunners.length; i++) {
				baserunners[i] = false;
			}
		}
	}
	
	// toString method
	public String toString() {
		String display = "";
		display += (team1 + " " + score1 + " " + team2 + " " + score2 + "; ");
		if(topInning)
			display += ("top of inning " + inning + "\n");
		else
			display += ("bottom of inning " + inning + "\n");
		
		display += ("outs: " + outs + "; balls: " + balls + "; strikes: " + strikes + "\n");
		display += ("runners on base: ");
		for(int i = 0; i < baserunners.length; i++) {
			if(baserunners[i])
				display += ( (i+1) + " "); 
		}
		return display;
		
	}
	
	
	
	// Check if the game is over
	public int checkIfGameOver() {
		if(inning > 2 && score1 != score2) {
			if(score2 > score1)
				return 2;
			else
				return 1;	
		}
		else if (inning == 2 && !topInning && score2 > score1){
			return 2;
		}
		else {
			return 0;
		}
	}
	
	// Announce the result
	public void announceResult() {
		String resultString;
		int result = checkIfGameOver();
		if(result == 1)
			resultString = (team1 + " won the game " + score1 + "-" + score2 + " against the " + team2);
		else
			resultString = (team2 + " won the game " + score2 + "-" + score1 + " against the " + team1);
		System.out.println(resultString);
	}
	
	// Get the current inning 
	public boolean currentInning() {
		boolean current = topInning;
		return current;
	}
}














