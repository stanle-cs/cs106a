/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 * 
 * Stanle-cs said:
 * there may be other more elegant way to to this, but this is
 * the most generalized algorithm I can think of. Maybe I don't need
 * to check everytime I put down a beeper but it is better just in case.
 * How this class work:
 *  1. Fill half the row with beepers (fill one space, skip one space).
 *  2. Push all of them to the corner.
 *  3. Re-distribute them until each beeper is in one space. This means
 *  the last beeper is at the middle of the row.
 *  4. Collect all other beepers and come back to the beeper in the middle.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		fillCheckered();
		turnAround();
		pushAllToCorner();
		turnAround();
		pushAndLeaveOne();
		cleanUp();
		goToBeeper();
		
	}
	
	// Precon: Karel is at the begining/end of the line, facing the wall.
	// Postcon: Karel moved to the nearest beeper.
	private void goToBeeper() {
		turnAround();
		while (noBeepersPresent()) {
			if (frontIsClear()) {
				move();
			}
		}
	}
	// precon: The middle beeper have been established and Karel is standing on it, facing away from the trail of beeper he put down.
	// postcon: Karel picked up all the beepers all the way back to the wall.
	private void cleanUp() {
		turnAround();
		move();
		collectAll();
	}
	
	// precon: None
	// postcon: The row is chcker-filled with beepers.
	private void fillCheckered() {
		while (frontIsClear()) {
			tryToPutBeeper();
			move();
		}
		tryToPutBeeper();
	}
	
	// precon: None
	// postcon: A beeper is put down if none present
	private void tryToPutBeeper() {
		turnAround();
		// check if there is already beeper behind
		if (frontIsClear()) {
			move();
			if (noBeepersPresent()) {
				//if not, then come back to the original position and put one down
				turnAround();
				move();
				putBeeper();
			}
			else {
				//if yes, then just move on
				turnAround();
				move();
			}
		}
		else {
			//fix OBOB
			turnAround();
			if (noBeepersPresent()) {
				putBeeper();
			}
		}
	}
	
	// precon: The row has been filled with beepers.
	// postcon: Karel pushed all of them into the corner.
	private void pushAllToCorner() {
		while (frontIsClear()) {
			tryToPushBeeper();
			move();
		}
	}
	
	// precon: A pile of beeper is present.
	// postcon: It is pushed one space to the front.
	private void tryToPushBeeper() {
		while (beepersPresent()) {
			pickBeeper();
			move();
			putBeeper();
			stepBack();
		}
	}
	
	// precon: the space behind Karel is not blocked.
	// postcon: Karel stepped back.
	private void stepBack() {
		turnAround();
		move();
		turnAround();
	}
	
	// precon: Karel is standing on a pile of beepers.
	// postcon: Karel pushed them forward, leaving one at each space until he run out of beepers.
	private void pushAndLeaveOne() {
		while (beepersPresent()) {
			if (frontIsClear()) {
				tryToPushBeeper();
				move();
				putOneBack();
			}
		}
		stepBack();
	}
	
	// precon: Karel finished pushing all beepers forward.
	// postcon: Karel put one beeper back to the original space.
	private void putOneBack() {
		turnAround();
		pickBeeper();
		move();
		putBeeper();
		turnAround();
		move();
	}
			
	// precon: There are beepers to be collected, front is clear.
	// postcon: All beepers are picked up, Karel is at the corner.
	private void collectAll() {
		while (frontIsClear()) {
			pickBeeper();
			move();
		}
		pickBeeper();
	}
	
}