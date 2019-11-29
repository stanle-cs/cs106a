import acm.program.*;
import acm.graphics.*;
import acm.util.*;

import java.awt.Color;

public class RandomCircles extends GraphicsProgram{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RandomGenerator rgen = RandomGenerator.getInstance();
	public static final double WIDTH = 100;
	public static final double HEIGHT = 100;
	
	public static void main(String[] args) {
		RandomCircles rc = new RandomCircles();
		rc.start(args);
	}

	public void run() {
		
		for (int i = 1; i < 10; i++) {
			
			double randd = rgen.nextDouble(5.0, 50.0);
			double randx = rgen.nextDouble(randd / 2, WIDTH - randd / 2);
			double randy = rgen.nextDouble(randd / 2, HEIGHT - randd / 2);
			Color color = rgen.nextColor();
			
			add( new GRect(0, 0, WIDTH, HEIGHT));
		
			
			GOval circle = new GOval(randx, randy, randd / 2, randd / 2);
			circle.setColor(color);
			circle.setFilled(true);
			add(circle);
		}
	}
}
