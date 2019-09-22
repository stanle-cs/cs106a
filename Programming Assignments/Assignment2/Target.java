/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 * 
 * Stanle-cs said:
 * 
 * This class is easy enough. Using the methods provided to draw three different circles with various colors.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	
	// How many pixel per inch
	public final int oneInch = 72;
	
	public void run() {
		
		// Get the X and Y cordinates of the middle of the window
		final int middleX = getWidth() / 2;
		final int middleY = getHeight() / 2;
		
		// Draw big circle
		int r = oneInch;
		add(drawCircle(middleX, middleY, r, Color.RED));
		
		// Draw medium circle
		r = 65*r/100;
		add(drawCircle(middleX, middleY, r, Color.WHITE));
		
		// Draw small circle
		r = 3*r/10;
		add(drawCircle(middleX, middleY, r, Color.RED));

	}
	
	// Precon: None
	// Postcon: A circle with radius r and center at (x,y) is drawn and filled with the color provided
	private GOval drawCircle(int x, int y, int r, Color color) {
		GOval circle = new GOval(x-r, y-r, r*2, r*2);
		circle.setColor(color);
		circle.setFilled(true);
		add(circle);
		return circle;
	}
}
