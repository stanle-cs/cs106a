/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 * 
 * Stanle-cs said:
 * 
 * This program start at the top, draw the top brick, then
 * move down, then draw two bricks, then repeate until it
 * reaches the base of the pyramid.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 33;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	public void run() {

		// First we need to know where is the middle of the window by getting the windowWidth value
		int windowWidth = getWidth();
		// Next we need to know the height of the window to calculate position of the bricks
		int windowHeight = getHeight();

		double middleOfWindowWidth = (double)windowWidth / 2;
		int row = BRICKS_IN_BASE;
		
		// Start drawing bricks
		for (int i = 1; i < (row + 1); i++){

			// Every loop we moved the start position by half a brick width outward (we starts at the top of the pyramid)
			double startPosition = middleOfWindowWidth - (double)BRICK_WIDTH/2.0*(i);
			
			// We also start the Y cordination at the top of the pyramid and move down every loop 
			double brickCordY = windowHeight - BRICK_HEIGHT*(row + 1 - i);

			for (int j = 0; j < (i); j++) {

				// Every loop we moved the X cordination by one brick's width to the right
				double brickCordX = startPosition + BRICK_WIDTH*j;

				// Set up the current brick and add to screen
				GRect brick = new GRect(brickCordX, brickCordY, BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFillColor(Color.yellow);
				brick.setFilled(true);
				add(brick);
			}
		}
	}
}

