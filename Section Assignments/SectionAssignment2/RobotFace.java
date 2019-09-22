/*
 * Filename: RobotFace.java
 * ------------------------
 * Draw a robot face. That's it.
 * 
 * Stanle-cs said:
 * 
 * Just reuse codes from the Coding Assignment 2 will help us.
 * A new method to draw circles using their center (x, y) will
 * be made to make drawing circle easier.
 */
import acm.program.*;
import acm.graphics.*;
import java.awt.*;

public class RobotFace extends GraphicsProgram{
	
	// The width of the head
	public final int HEAD_WIDTH = 230;
	
	// The height of the head
	public final double HEAD_HEIGHT = 380;
	
	// The width of the mouth
	public final double MOUTH_WIDTH = 120;
	
	// The height of the mouth
	public final double MOUTH_HEIGHT = 50;
	
	// The radius of the eye
	public final double EYE_RADIUS = 30;
	
	public void run() {

		// Get the coordinate of the center of the window
		double middleX = getWidth() / 2;
		double middleY = getHeight() / 2;
		
		// Decide where to draw the "Head" box
		double offsetHeadX = middleX - (HEAD_WIDTH / 2);
		double offsetHeadY = middleY -(HEAD_HEIGHT / 2);
		
		// Draw "Head" box
		GRect head = new GRect(offsetHeadX, offsetHeadY, HEAD_WIDTH, HEAD_HEIGHT);
		head.setFillColor(Color.GRAY);
		head.setFilled(true);
		add(head);
		
		// Decide where to draw the "Left Eye" circle
		double offsetLeftEyeX = middleX - (HEAD_WIDTH / 4);
		double offsetLeftEyeY = middleY -(HEAD_HEIGHT / 4);
		
		// Draw "Left Eye" circle
		drawCircle(offsetLeftEyeX, offsetLeftEyeY, EYE_RADIUS, Color.YELLOW);
		
		//Decide where to draw the "Left Eye" circle
		double offsetRightEyeX = middleX + (HEAD_WIDTH / 4);
		double offsetRightEyeY = middleY -(HEAD_HEIGHT / 4);
		
		// Draw "Left Eye" circle
		drawCircle(offsetRightEyeX, offsetRightEyeY, EYE_RADIUS, Color.YELLOW);
		
		// Decide where to draw "Mouth" box
		double offsetMouthX = middleX - (MOUTH_WIDTH / 2);
		double offsetMouthY = middleY + (HEAD_HEIGHT / 4) - (MOUTH_HEIGHT / 2);
		
		// Draw "Mouth" box
		GRect mouth = new GRect(offsetMouthX, offsetMouthY, MOUTH_WIDTH, MOUTH_HEIGHT);
		mouth.setColor(Color.WHITE);
		mouth.setFilled(true);
		add(mouth);
	}
	
	// Precon: None
	// Postcon: A circle with radius r and center at (x,y) is drawn and filled with the color provided
	private void drawCircle(double x, double y, double r, Color color) {
		GOval circle = new GOval(x-r, y-r, r*2, r*2);
		circle.setColor(color);
		circle.setFilled(true);
		add(circle);
	}
}
