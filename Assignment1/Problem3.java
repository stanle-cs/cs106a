/*
 * File: Problem3.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
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
	}
	
	// Precon: None
	// Postcon: The spot behind is checked, if it is empty and the current space doesn't have a beeper one will be put down.
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
	
	// Precon: Karel front is clear
	// Postcon: Karel put down a beeper and moved two space ahead
	private void tryToPut(){
		if (noBeepersPresent()){
			putBeeper();
		}
	}
	
	// Precondition: Karel have already put down the beeper
	// Postcondition: Karel is moved either to the next position or up
	private void moveOn(){
		if (frontIsClear())	{
			move();
		}
		else{
			moveUp();
		}
	}
	
	// Precondition: Karel is at the end of the line facing either east or west, there are still room on top
	// Postcondition: Karel moved up one unit and turned around
	private void moveUp(){
		if (facingEast()){
			turnLeft();
			if (frontIsClear()) {
				move();
				turnLeft();
			} 
			else {
				findNearestBeeper();
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
					findNearestBeeper();
				}
			}
		}
	}
	
	// Precon: Usually at the end of the map, and the last space can't be put a beeper making the loop infinite
	// Postcon: Karel moved to the nearest beeper available, thus ending the loop
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
				}
			}
		}
	}
}
