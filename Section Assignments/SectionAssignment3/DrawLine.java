import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import acm.graphics.*;
import acm.program.*;

public class DrawLine extends GraphicsProgram implements MouseMotionListener, MouseListener{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	GLine line = new GLine(0, 0, 0, 0);
	
	
	int width = 300;
	
	int height = 300;
	
	boolean done = false;
	
	boolean reset = false;
	
	public void mouseClicked(MouseEvent e) {
		if (!done) { 
			line.setStartPoint(e.getX(), e.getY());
			done = true;
		}
		else {
			line.setEndPoint(e.getX(), e.getY());
			done = false;
			reset = true;
		}
 
	}
	
	public void mouseMoved(MouseEvent e) {
		if (done) line.setEndPoint(e.getX(), e.getY());
		line.setVisible(true);
	}
	
	public void run() {
		line.setVisible(false);
		add(line);
		GRect detectBox = new GRect(0, 0, width, height);
		detectBox.addMouseMotionListener(this);
		detectBox.addMouseListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		add(detectBox);
	}
}
