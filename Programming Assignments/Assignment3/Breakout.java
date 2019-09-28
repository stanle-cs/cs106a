/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram implements MouseMotionListener{

/** Width of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	
/** Height of application window in pixels */
	public static final int APPLICATION_HEIGHT = 600;

/** Width of game board (usually the same as app width) */
	private static final int WIDTH = APPLICATION_WIDTH;
	
/** Height of game board (usually the same as app height) */
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Width of the paddle */
	private static final int PADDLE_WIDTH = 60;
	
/** Height of the paddle */
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;
	
/** Number of bricks total */
	private static final int NBRICKS = 100;
	
/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Actual paddle Y coordinate */
	private static final int PADDLE_Y = HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;

/** Ball diameter */
	private static final int BALL_DIAMETER = BALL_RADIUS*2;
	
	
/** Instance variables*/
	
	/** X component of the ball's velocity */
	private double vX;
	/** Y component of the ball's velocity */
	private double vY;
	
	/** Total amount of bricks remained in play */
	private int totalBricks = NBRICKS;
	
	/** RandomGenerator instance used for vX randomization*/
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	/** The paddle at the bottom, controlled by mouse movement by the player*/
	private GRect paddle = new GRect(0,0,0,0);
	
	/** The ball that bounce around the play field*/
	private GOval ball = new GOval(0,0,0,0);
	
	/** The blankArea used to detect mouse movement*/
	private GRect blankArea = new GRect(0, 0, WIDTH, HEIGHT);
	
	
	/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */
		int life = NTURNS;
		
		while (life > 0){
			reset();
			while ((ball.getY() + BALL_DIAMETER)< HEIGHT) { 
				ball.move(vX,vY);
				checkCollision();
				if (totalBricks == 0) break;
				pause(7);
			}
			life--;
		}
		exit();
		//add(new GLine((WIDTH) / 2, 0, (WIDTH) / 2, HEIGHT));
	}
	
/** 
 * Reset the game back to the original state.
 * Precon: The ball hit the lower wall.
 * Postcon: The player's life is decrased by one, the scene is redrawn, totalBricks is set to 100.
 * 
 * */
	private void reset() {
		removeAll();
		totalBricks = 100;
		initScene();
		initRGen();
	}
	
/**
 * Move the paddle whenever the mouse is moved in the blankArea.
 * Precon: Mouse motion is detected.
 * Postcon: The paddle's X corrdinate is set to the mouse's X coordinate plus half of {@link #PADDLE_WIDTH}.
 */
	
    public void mouseMoved(MouseEvent e) {
        paddle.setLocation(e.getX()- PADDLE_WIDTH / 2, paddle.getY());
    }
    
/**
 * Check for collision between the ball and other object and act accordingly.
 * Precon: None.
 * Postcon: if collision detected with brick, the brick is removed and totalbricks is decreased.
 * If collided with wall or paddle then bounce back. 
 */
    
	private void checkCollision() {
		
		double ballX = ball.getX();
		double ballY = ball.getY();
		
		// The four corner of the ball used for checking
		GPoint ballNW = new GPoint(ballX, ballY);
		GPoint ballSW = new GPoint(ballX, ballY + BALL_DIAMETER);
		GPoint ballNE = new GPoint(ballX + BALL_DIAMETER, ballY);
		GPoint ballSE = new GPoint(ballX + BALL_DIAMETER, ballY + BALL_DIAMETER);
		
		// Check for wall collision
		if (ballX < 0 || (ballX + BALL_DIAMETER) > WIDTH) vX = -vX;
		if (ballY < 0 || (ballY + BALL_DIAMETER) > HEIGHT) vY = -vY;
		
		// Check for paddle collision
		if (isPaddle(ballSW) && !isPaddle(ballSE)) {
			vX= -vX;
			vY = -vY;
		}
		else if (isPaddle(ballSE) && !isPaddle(ballSW)) {
			vX= -vX;
			vY = -vY;
		}
		else if (isPaddle(ballSE) && isPaddle(ballSW)) {
			vY = -vY;
		}
		
		// Check for brick collision
		if (isBrick(ballSW) || isBrick(ballSE) || isBrick(ballNW) || isBrick(ballNE)){
			checkBrick(ballSW);
			checkBrick(ballSE);
			checkBrick(ballNW);
			checkBrick(ballNE);
			vY = -vY;
		}
		/*
		if (isBrick(ballSW)) {
			remove(getElementAt(ballSW));
			totalBricks--;
			vY = -vY;
			ball.setLocation(ball.getX(), ball.getY());
		}
		else if (isBrick(ballSE)) {
			remove(getElementAt(ballSE));
			totalBricks--;
			vY = -vY;
		}
		else if (isBrick(ballNW)) {
			remove(getElementAt(ballNW));
			totalBricks--;
			vY = -vY;
		}
		else if (isBrick(ballNE)) {
			remove(getElementAt(ballNE));
			totalBricks--;
			vY = -vY;
		}
		*/
	}
	
/**
 * Check if the element at the {@link GPoint} point is a brick. If yes, removes it and decrease {@link #totalBricks}.
 */
	private void checkBrick(GPoint point) {
		if (isBrick(point)) {
			remove(getElementAt(point));
			totalBricks--;
		}
	}
/**
 * Check if the element currently at this point is a brick.
 * @param point GPoint object that have the x and y coordinate.
 * @return true if element is a brick, false otherwise.
 */
	
	private boolean isBrick(GPoint point) {
		return (getElementAt(point) != paddle && getElementAt(point) != null && getElementAt(point) != blankArea);
	}
	
/**
 * Check if the element currently at this point is the paddle.
 * @param point GPoint object with x and y coordinate.
 * @return true if element is the paddle, false otherwise.
 */
	
	private boolean isPaddle(GPoint point) {
		return (getElementAt(point) == paddle);
	}
	
/**
 * Initialize the scene, including the blankArea, the bricks, the ball and the paddle.
 * Precon: None.
 * Postcon: The blankArea, the ball, and the paddle are initilized. Bricks are drawn.
 */
	
	private void initScene() {
		
		initBlankArea();
		drawAllBrick();
		initBall();
		initPaddle();
		
	}
	
	
/**
 * Initialize the {@link RandomGenerator} needed to randomize vX.
 * Precon: None.
 * Postcon: vX, vY are initialized.
 */
	private void initRGen() {
		vX = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vX = -vX;
		vY = 3.0;	
	}
	
/**
 * Initialize a {@link GRect} object "blank area" in which the mouse motion is detected.
 * Precon: class need to implements {@link MouseMotionListener} interface.
 * Postcon: blankArea initialized with MouseMotionListener added.
 */
	
	private void initBlankArea() {
		
		blankArea = new GRect(0, 0, WIDTH, HEIGHT);
		
		//blankArea.setColor(Color.GRAY);
        blankArea.addMouseMotionListener(this);
		addMouseMotionListener(this);
		//blankArea.setFilled(true);
		add(blankArea);
	}
	
/**
 * Initialize the ball as a new {@link GOval} object.
 * Precon: None.
 * Postcon: The ball is initialized at the middle of the screen, falling downward with a randomized vX and a vY of 3.0.
 */
	
	private void initBall() {
		
		int ballX = (WIDTH) / 2 - BALL_RADIUS;
		int ballY = (HEIGHT) / 2 - BALL_RADIUS;
				
		ball = new GOval(ballX, ballY, BALL_DIAMETER, BALL_DIAMETER);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		add(ball);
	}
	
/**
 * Initialize the paddle as a new {@link GRect} object.
 * Precon: None.
 * Postcon: The paddle is initialized 30 pixel from the bottom of the screen.
 *
 */

	private void initPaddle() {
		
		paddle = new GRect((WIDTH - PADDLE_WIDTH) / 2, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);
		
		paddle.setColor(Color.BLACK);
		paddle.setFilled(true);
		add(paddle);
	}
	
/**
 * Draw all the bricks as new {@link GRect} with their corresponding colors.
 * Precon: None.
 * Postcon: A total of {@link #NBRICKS_PER_ROW}*{@link #NBRICK_ROWS} is drawn on the play field with specified color.
 */
	
	private void drawAllBrick() {
		Color color = Color.RED;
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			
			for (int j = 0; j < NBRICK_ROWS; j++) {
				
				// Decide which color the bricks of current row will be:
				switch (i / 2) {
					case 1:
						color = Color.ORANGE;						
						break;
					case 2:
						color = Color.YELLOW;
						break;
					case 3:
						color = Color.GREEN;
						break;
					case 4:
						color = Color.CYAN;
						break;
				}
				
				// Draw brick:
				drawBrick(2 + (BRICK_WIDTH + BRICK_SEP)*j, BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP)*i, color);
			}
		}
	}
	
/**
 * Draw a brick with dimensions {@link #BRICK_WIDTH}, {@link #BRICK_HEIGHT} at the specified location with the specified color.
 * @param x The X coordinate of the brick.
 * @param y The Y coordinate of the brick.
 * @param color The color of the brick.
 */
	
	private void drawBrick(int x, int y, Color color) {
		GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
		brick.setLocation(x, y);
		brick.setColor(color);
		brick.setFilled(true);
		add(brick);
	}

}
