/**
 * Filename: DrawLines.java
 * 
 * stanle-cs said:
 * This is a program that draw a line using the rubber-banding method.
 * Program should be run in a window 600x600. This can be changed through
 * the constants WIDTH and HEIGHT.
 * 	1. Mouse pressed: new start point for the line is set.
 * 	2. Mouse dragged: new end point for the line is set.
 * 	3. Mouse released: the line is finalized, new line is initiated.
 * */


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import acm.graphics.*;
import acm.program.*;

public class DrawLines extends GraphicsProgram implements MouseMotionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** Width of the window*/
	private static final int WIDTH = 600;
	
	/** Height of the window*/
	private static final int HEIGHT = 600;
	
	/** Instance variable line*/
	GLine line = new GLine(0,0,0,0);
	
	/** 
	 * Override of the mousePressed method. Set line start point to the location of the mouse pressed event.
	 * */
	public void mousePressed(MouseEvent e) {
		line.setLocation(e.getX(), e.getY());
	}
	/**
	 * Override of the mouseDragged method. Set line end point to the current location of the mouse dragged event.
	 */
	public void mouseDragged(MouseEvent e) {
		line.setEndPoint(e.getX(), e.getY());
		add(line);
	}
	
	/**
	 * Override of the mouseReleased method. Initiate a new GLine object.
	 */
	public void mouseReleased(MouseEvent e) {
		line = new GLine(0, 0, 0, 0);
	}
	
	public void run() {
		GRect detectBox = new GRect(0, 0, WIDTH, HEIGHT);
		detectBox.addMouseMotionListener(this);
		detectBox.addMouseListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		add(detectBox);
	}
}
