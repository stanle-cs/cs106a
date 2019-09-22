/*
 * File: DefendDemocracy.java
 * --------------------------
 * 
 * Stanle-cs said:
 * This class work like this:
 *  1. Move to the next ballot rectangle.
 *  2. If beeper present, pick all but one up then
 *  move on.
 *  3. If no beeper present in middle, put one down
 *  as a flag.
 *  4. Clean up the top half of the ballot.
 *  5. Clean up the bottom half of the ballot.
 *  6. Pick up the flag beeper and move on.
 *  7. Continue until facing wall.
 */

import stanford.karel.*;

public class DefendDemocracy extends SuperKarel{
	public void run() {
		while (frontIsClear()) {
			move();
			work();
		}
	}
	
	// Precon: already at the ballon rectangle
	// Postcon: the rectangle is all cleaned up
	private void work() {
		if (noBeepersPresent()) {
			putBeeper();	// This is a flag for Karel to know where is the middle
			
			// Clean up top half
			turnLeft();
			cleanUp();
			
			turnAround();
			
			// Clean up bottom half
			cleanUp();
			turnLeft();
			
			pickBeeper();	// Remove flag when done
			move();
		}
		else {
			
			// Clean up all beeper if present
			while (beepersPresent()) {
				pickBeeper();
			}
			
			// Put one back after cleaning all
			putBeeper();
			
			move();
		}
	}
	
	// Precon: already facing either the top or bottom of the rectangle
	// Postcon: all beepers cleaned up, facing original orientation
	private void cleanUp() {
		while (frontIsClear()) {
			move();
			while (beepersPresent()) {
				pickBeeper();
			}
		}
		// OBOB
		while (beepersPresent()) {
			pickBeeper();
		}
		
		turnAround();
		
		// Return to middle by looking for the flag
		while (noBeepersPresent()) {
			move();
		}
		turnAround();	// Revert original orientation
	}
}
