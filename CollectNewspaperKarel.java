/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 * 
 * In this assignment, the initial position of Karel and the location
 * of the beeper are not randomized. Hence, there is no need to
 * create a generalized algorithm for Karel. However, if we would 
 * like to use this subclass in a general simple house with one 
 * door, Karel starts in the upper left corner, beeper at the front
 * of the house then we can use a more generalized algorithm presented
 * below.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	
	// Main program
	public void run() {
		goToSite();
		pickUp();
		goBack();
	}
	
	// Go to the front of the house, where the beeper is
	private void goToSite() {
		goToWall();
		turnRight();
		goToDoor();
		turnLeft();
		move();
	}
	
	// Pick up beeper if present
	private void pickUp() {
		if (beepersPresent()) {
			pickBeeper();
		}
	}
	
	// Go straight until hit the wall
	private void goToWall() {
		while (frontIsClear()) {
			move();
		}
	}
	
	// Go to the door, since it is always on your left
	private void goToDoor() {
		while (leftIsBlocked()) {
			move();
		}
	}
	
	// Go back to the original position, facing the same direction as we started
	private void goBack() {
		turnAround();
		goToWall();
		turnRight();
		goToWall();
		turnRight();
	}
}
