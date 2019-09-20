/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 * 
 * Stanle-cs said:
 * How this class work:
 * 1. Karel always try to put the first beeper down if possible.
 * 2. Karel move on to the next space if available.
 * 3. Karel check if the space below or behind (if available) have beeper. 
 * 		3.1. If yes, Karel won't put a beeper down. If no, Karel put a beeper down.
 * 4. Karel move on to the next space.
 * 5. Repeat until all space are surveyed.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run(){
		tryToPut();
		moveOn();
		while (noBeepersPresent()){
			checkBehind();
			checkBelow();
			moveOn();
		}
		moveOn(); // Purely a cosmetic thing, so that we can ensure that Karel is at the last space when execution stop.
	}
	
	// Precon: None
	// Postcon: The space behind is checked, if it is empty and the current space doesn't have a beeper one will be put down.
	private void checkBehind() {
		turnAround();
		if (frontIsClear()) {
			move();
			if (noBeepersPresent()) {
				turnAround();
				move();
				tryToPut();
			} 
			else {
				turnAround();
				move();
			}
		}
		else {
			turnAround();
		}	
	} 
	
	// Precon: None
	// Postcon: The space below will be checked, if it is empty and the current space doesn't have a beeper one will be put down.
	private void checkBelow() {
		if (facingWest()) {
			turnRight();
			checkBehind();
			turnLeft();
		} 
		else if (facingEast()) {
			turnLeft();
			checkBehind();
			turnRight();
		}
	}
	
	// Precon: None
	// Postcon: Karel put down a beeper if none present.
	private void tryToPut(){
		if (noBeepersPresent()){
			putBeeper();
		}
	}
	
	// Precondition: Karel is finished with the current space.
	// Postcondition: Karel is moved either to the next space accordingly.
	private void moveOn(){
		if (frontIsClear())	{
			move();
		}
		else{
			moveUp();
		}
	}
	
	// Precondition: Karel is at the end of the line facing either east or west, there are still room on top.
	// Postcondition: Karel moved up one unit and turned around.
	private void moveUp(){
		if (facingEast()){
			turnLeft();
			if (frontIsClear()) {
				move();
				turnLeft();
			} 
			else {
				turnRight();
				findNearestBeeper(); //no room on top means end of execution, find nearest beeper to end loop.
			}
		}
		else{
			if (facingWest()){
				turnRight();
				if (frontIsClear()) {
					move();
					turnRight();
				}
				else {
					turnLeft();
					findNearestBeeper();
				}
			}
		}
	}
	
	// Precon: Usually at the end of the map, and the last space can't be put a beeper making the loop infinite.
	// Postcon: Karel moved to the nearest beeper available, thus ending the loop.
	private void findNearestBeeper() {
		if (noBeepersPresent()) {
			turnAround();
			if (frontIsClear()) {
				move();
			}
			else {
				turnRight();
				if (frontIsClear()) {
					move();
				}
				else {
					turnAround();
					move();
					turnAround();
				}
				turnLeft();
			}
			turnAround();
		}
	}
}