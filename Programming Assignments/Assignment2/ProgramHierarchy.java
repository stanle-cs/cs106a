/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 * 
 * Stanle-cs said:
 * This is an interesting problem. I ended up making a full program that
 * can draw any tree diagrams we want. The two methods drawBox() and
 * connect() are general enough to work under any circumstances.
 * Just make sure to keep the label string smaller than the box's width LOL.
 */

import acm.graphics.*;
import acm.program.*;

public class ProgramHierarchy extends GraphicsProgram {	
	
	// The width of the boxes
	public static final double RECT_WIDTH = 146;
	
	// The height of the boxes
	public static final double RECT_HEIGHT = 48;
	
	public void run() {
		
		// Get the coordinate of the center of the window
		double middleX = getWidth() / 2;
		double middleY = getHeight() / 2;
		
		// Decide where to draw the "Program" box
		double offsetProgramX = RECT_WIDTH / 2;
		double offsetProgramY = RECT_HEIGHT * 3 / 2;
		// Draw "Program" Box
		GRect programBox = drawBox(middleX - offsetProgramX, middleY - offsetProgramY, "Program");
		
		//Decide where to draw the "GraphicsProgram" box
		double offsetGraphicsProgramX = RECT_WIDTH * 3.5 / 2;
		double offsetGraphicsProgramY = RECT_HEIGHT / 2;
		// Draw "GraphicsProgram" Box
		GRect graphicsBox = drawBox(middleX - offsetGraphicsProgramX, middleY + offsetGraphicsProgramY, "GraphicsProgram");
		
		//Decide where to draw the "ConsoleProgram" box
		double offsetConsoleProgramX = RECT_WIDTH / 2;
		double offsetConsoleProgramY = RECT_HEIGHT / 2;
		// Draw "ConsoleProgram" Box
		GRect consoleBox = drawBox(middleX - offsetConsoleProgramX, middleY + offsetConsoleProgramY, "ConsoleProgram");
		
		//Decide where to draw the "DialogProgram" box
		double offsetDialogProgramX = RECT_WIDTH * 1.5 / 2;
		double offsetDialogProgramY = RECT_HEIGHT / 2;
		// Draw "DialogProgram" Box
		GRect dialogBox = drawBox(middleX + offsetDialogProgramX, middleY + offsetDialogProgramY, "DialogProgram");
		
		// Make connections
		connect(programBox, graphicsBox);
		connect(programBox, consoleBox);
		connect(programBox, dialogBox);
		
		// Visual reference of window center
		/*
		add(new GLine(middleX, 0, middleX, getHeight()));
		add(new GLine(0,middleY, getWidth(), middleY));
		*/
	}
	
/*		
	Precon: None
	Postcon: A box is drawn with origin at (x, y) displaying the string provided
	Due to the fact that getAscent() returns a value that is higher than most regular Latin 
	letters (the white space above is for irregular glyphs from other languages) the actualSize
	of the label is a bit lower. Thus the actualSize that we care about while displaying English
	words is getAscent() - getDescent().
	Inspired by this thread:
	https://bugs.java.com/bugdatabase/view_bug.do?bug_id=4399887
*/
	private GRect drawBox(double x, double y, String string) {
		
		double labelOffsetX;
		double labelOffsetY;
		double actualSize;
		
		// Draw box
		GRect box = new GRect(x, y, RECT_WIDTH, RECT_HEIGHT);
		add(box);
		
		// Initiate label
		GLabel label = new GLabel(string);
		label.setFont("SansSerif-18");
		
		// Relocate label to the correct place in the middle of the box
		actualSize = label.getAscent() - label.getDescent();
		labelOffsetY = y + actualSize + (RECT_HEIGHT - actualSize) / 2;
		labelOffsetX = x + (RECT_WIDTH - label.getWidth())/2;
		label.setLocation(labelOffsetX, labelOffsetY);
		
		// Draw label
		add(label);
		return box; 
	}
/*
 * 	Connect: connects two boxes to each other using a simple line originates from the
 * 	center of the bottom of the first box to the center of the top of the second box.
 * 	Precon: Two boxes are drawn.
 * 	Postcon: The boxes are connected by a GLine object.
 */
	private void connect(GRect fromBox, GRect toBox) {
		
		// Set origin X and Y: middle of the bottom edge of first box
		double originX = fromBox.getX() + RECT_WIDTH / 2;
		double originY = fromBox.getY() + RECT_HEIGHT;
		
		// Set end X and Y: middle of the top edge of second box
		double endX = toBox.getX() + RECT_WIDTH / 2;
		double endY = toBox.getY();
		
		// Draw line
		add(new GLine(originX, originY, endX, endY));
	}
}

