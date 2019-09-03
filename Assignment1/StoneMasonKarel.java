/*
 * File: Problem2.java
 * --------------------------
 * The StoneMasonKarel subclass here will work like this:
 * 	- Work on the current column
 *  - Once done, return to the original position & orientation
 *  - Move to next column
 *  - Check if at the end, if yes stop
 *  - If not, repeat
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		while (frontIsClear()) {
			repairArch();
			getBack();
			moveToNextOne();
		}
		repairArch();  // At last column, the loop exited without working, so we add another work here
		getBack();
	}
	
	/* Repairing arch:
	 *  - Turn left
	 *  - If no beeper present, put one down
	 *  - Move up
	 */
	private void repairArch() {
		turnLeft();
		while (frontIsClear()) {
			workOnBeeper();
			move();
		}
		workOnBeeper();  // At the top, the loop will exit, we add another work here to compensate
	}
	
	// workOnBeeper: if no beeper present, put one down
	private void workOnBeeper() {
		if (noBeepersPresent()) {
			putBeeper();
		}
	}
	
	// getBack: Turn around and walk until hit wall, then turn left to face East again
	private void getBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
	
	//moveToNextOne: just walk foward 4 times
	private void moveToNextOne() {
		for (int i=0; i < 4; i++) {
			move();
		}
	}

}
