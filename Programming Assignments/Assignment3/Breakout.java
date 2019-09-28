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

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

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
	
/** Actual paddle Y coordinate*/
	private static final int PADDLE_Y = HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;

	
	
/** Instance variables*/
	private double vX, vY;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private GRect paddle = new GRect(0,0,0,0);
	private GOval ball = new GOval(0,0,0,0);
	/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		/* You fill this in, along with any subsidiary methods */

		initScene();
		ball = initBall();
		paddle = initPaddle();
		
		vX = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vX = -vX;
		vY = -3.0;	
		
		while (ball.getX() < WIDTH) { 
			ball.setLocation(ball.getX() + vX,ball.getY() + vY);
			
			pause(25);
			println(ball.getX());
		}
		
		add(new GLine((WIDTH) / 2, 0, (WIDTH) / 2, HEIGHT));
	}
	
	private void initScene() {
		drawAllBrick();
	}
	
	private GOval initBall() {
		
		int ballX = (WIDTH) / 2 - BALL_RADIUS;
		int ballDiameter = BALL_RADIUS*2;
		int ballY = PADDLE_Y - ballDiameter;
				
		GOval ball = new GOval(ballX, ballY, ballDiameter, ballDiameter);
		ball.setFilled(true);
		ball.setColor(Color.BLACK);
		add(ball);
		return ball;
	}
	
	
	private void paddleMove(GRect paddle, int x) {
		paddle.setLocation(x, PADDLE_Y);
	}
	
	private GRect initPaddle() {
		GRect paddle = new GRect((WIDTH - PADDLE_WIDTH) / 2, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setColor(Color.BLACK);
		paddle.setFilled(true);
		add(paddle);
		return paddle;
	}
	
	
	
	
	private void drawAllBrick() {
		Color color = Color.RED;
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			
			for (int j = 0; j < NBRICK_ROWS; j++) {
			
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
				drawBrick(2 + (BRICK_WIDTH + BRICK_SEP)*j, BRICK_Y_OFFSET + (BRICK_HEIGHT + BRICK_SEP)*i, color);
			}
		}
	}
	
	
	private void drawBrick(int x, int y, Color color) {
		GRect brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
		brick.setLocation(x, y);
		brick.setColor(color);
		brick.setFilled(true);
		add(brick);
	}

}
