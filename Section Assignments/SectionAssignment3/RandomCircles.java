/**
 * Filename: RandomCircles.java
 * 
 * stanle-cs said:
 * A GraphicsProgram that draws a set of ten circles with different sizes, positions and colors.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.Color;

public class RandomCircles extends GraphicsProgram{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Random Generator*/
	RandomGenerator rgen = RandomGenerator.getInstance();
	
	/** Width of the window*/
	public static final double WIDTH = 600;
	
	/** Height of the window*/
	public static final double HEIGHT = 600;
	
	/** Number of circles*/
	public static final int NUMBER_OF_CIRCLES = 10;
	
	public static void main(String[] args) {
		RandomCircles rc = new RandomCircles();
		rc.start(args);
	}

	public void run() {
		
		for (int i = 1; i < NUMBER_OF_CIRCLES; i++) {
			
			// Randomize size, color and location of new circle
			double randd = rgen.nextDouble(5.0, 300.0);
			double randx = rgen.nextDouble(randd / 2, WIDTH - randd / 2);
			double randy = rgen.nextDouble(randd / 2, HEIGHT - randd / 2);
			Color color = rgen.nextColor();
			
			// Draw the new circle
			GOval circle = new GOval(randx, randy, randd / 2, randd / 2);
			circle.setColor(color);
			circle.setFilled(true);
			add(circle);
		}
	}
}
