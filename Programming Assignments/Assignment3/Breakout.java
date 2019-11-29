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

/**
 * Breakout is a game invented by Steve Wozniak before he founded Apple.
 * The colored rectangles in the top part of the screen are bricks, and 
 * the slightly larger rectangle at the bottom is the  paddle. The paddle
 * is in a fixed position in the vertical dimension, but moves back and
 * forth across the screen along with the mouse until it reaches the edge
 * of its space. A complete game consists of three turns. On each turn,
 * a ball is launched from the center of the window toward the bottom of
 * the screen at a random angle. That ball bounces off the paddle and the
 * walls of the world, in accordance with the physical principle generally
 * expressed as �the angle of incidence equals the angle of reflection�.
 * The play on a turn continues in this way until one of two conditions
 * occurs:
 * 1. The ball hits the lower wall, which means that the player must have
 * missed it with the paddle. In this case, the turn ends and the next ball
 * is served if the player has any turns left. If not, the game ends in a
 * loss for the player.
 * 2. The last brick is eliminated. In this case, the player wins, and the
 * game ends immediately. 
 * (Citation: Mehran Sahami, CS106A, Handout #19, October 15, 2007)
 * @author trung
 *
 */
public class Breakout extends GraphicsProgram implements MouseMotionListener{
/*
 * ---------------------------------------------------------
 * Constants
 */
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
	
/*----------------------------------------------------------------------------- 
 * Instance variables*/
	
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
	
	/** NorthWest corner of the ball collider*/
	private GPoint ballNW = new GPoint(ball.getX(), ball.getY());
	
	/** SouthWest corner of the ball collider*/
	private GPoint ballSW = new GPoint(ball.getX(), ball.getY() + BALL_DIAMETER);
	
	/** NorthEast corner of the ball collider*/
	private GPoint ballNE = new GPoint(ball.getX() + BALL_DIAMETER, ball.getY());
	
	/** SouthEast corner of the ball collider*/
	private GPoint ballSE = new GPoint(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER);
	
	/** NorthWest corner of the paddle collider*/
	private GPoint paddleNW = new GPoint(paddle.getX(), paddle.getY());
	
	/** NorthEast corner of the paddle collider*/
	private GPoint paddleNE = new GPoint(paddle.getX() + PADDLE_WIDTH, paddle.getY());
	
	/** The scoreboard displaying the current score*/
	private GLabel scoreBoard = new GLabel("");
	
	/** The sound clip that contain the bounce sound*/
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	
	/** Current difficulty level*/
	private int difficulty = 0;
	
	/** Current score*/
	private int score = 0;
	
	/** Current life*/
	private int life = NTURNS;
	

	public static void main(String[] args) {
		Breakout bo = new Breakout();
		bo.start(args);
	}

	/* Method: run() */
/** 
 * Runs the Breakout program. 
 * */	
	public void run() {

		difficulty = 0;
		score = 0;

		initScene();
		initRGen();
		initScoreBoard();

		while (life > 0){
			reset();
			while ((ball.getY() + BALL_DIAMETER)< HEIGHT) { 
				
				// Increase difficulty every 7 paddle bounces
				if ((difficulty % 7 == 0) && (difficulty !=0)) {
					vX = vX*1.0005;
					vY = vY*1.0005;
				}
				
				// Let the ball move
				ball.move(vX,vY);

				// Update the ball's collider
				updateBallCollider();
				
				// Cheat
				//paddle.move((ball.getX() - paddle.getX())/2, 0);
				
				//  Check for collision
				checkCollision();
				
				// Win condition
				if (totalBricks == 0) break;
				
				// Pause time
				pause(5);
			}
			
			if (totalBricks ==0) {
				win();
				break;
			}
			else { 
				life--;
				updateScore();
			}
		}
		if (life == 0) lose();
	}
	
/**
 * Draw a big red message in the middle of the screen.
 */
	private void drawMessage(String s) {
		GLabel msg = new GLabel(s);
		msg.setFont("Arial-bold-30");
		msg.setColor(Color.RED);
		msg.setLocation(WIDTH / 2 - msg.getWidth() / 2, HEIGHT / 2 - msg.getAscent() / 2);
		add(msg);
	}
	
/**
 * Execute the wining congratulation
 * Precon: All bricks are removed.
 * Postcon: Wining message appeared.
 */
	private void win() {
		drawMessage("YOU WIN!");
	}
	
/**
 * Execute the losing notice
 * Precon: All life is lost.
 * Postcon: Losing message appeared.
 */
	private void lose() {
		drawMessage("YOU LOSE!");
	}

/** 
 * Reset the game back to the original state. The removed bricks won't be re-initialized to decrease
 * difficulty.
 * Precon: The ball hit the lower wall.
 * Postcon: The ball and paddle are re-initialized.
 */
	private void reset() {
		remove(ball);
		remove(paddle);
		initBall();
		initPaddle();
	}
	
/**
 * Move the paddle whenever the mouse is moved in the blankArea.
 * Precon: Mouse motion is detected.
 * Postcon: The paddle's X corrdinate is set to the mouse's X coordinate plus half of {@link #PADDLE_WIDTH}.
 */
    public void mouseMoved(MouseEvent e) {
    	if (((e.getX() + (PADDLE_WIDTH / 2)) <= WIDTH) && (e.getX() - (PADDLE_WIDTH / 2) >= 0)) {
	        paddle.setLocation(e.getX()- PADDLE_WIDTH / 2, paddle.getY());
	        updatePaddleCollider();
    	}
    	// Handles edge case when paddle hit right wall
    	else if ((e.getX() + (PADDLE_WIDTH / 2)) > WIDTH) {
    		paddle.setLocation(WIDTH- PADDLE_WIDTH, paddle.getY());
	        updatePaddleCollider();
    	}
    	// Handles edge case when paddle hit left wall
    	else if (e.getX() - (PADDLE_WIDTH / 2) < 0) {
    		paddle.setLocation(0, paddle.getY());
	        updatePaddleCollider();
    	}
    }
    
/**
 * Check for collision between the ball and other object and act accordingly.
 * Precon: None.
 * Postcon: if collision detected with brick, the brick is removed and totalbricks is decreased.
 * If collided with wall or paddle then bounce back. 
 */
	private void checkCollision() {

		checkWallCollision();
		if (collided()) {
			checkPaddleCollision();
			checkBrickCollision();
		}
	}

/**
 * Check for collision between the ball and the paddle. Bounce the wall if detected.
 * The ball won't bounce if it is travelling up and hit the paddle to prevent rapid
 * bouncing edge cases.
 */
	private void checkPaddleCollision() {

		
		// if paddleNW is collided then invert both vX and vY, but only for ball coming from the left
		if ((paddleNW.getX() < ballSE.getX()) 
			&& 
			(paddleNW.getX() > ballSW.getX())
			&&
			(paddleNW.getY() > ballNE.getY())
			&&
			(paddleNW.getY() < ballSE.getY())
			&&
			(vX > 0)) {
			
			vX = -vX;
			vY = -vY;
			bounceClip.play();
			difficulty++;
			
		}
		// same for paddleNE, only for ball comming from the right
		else if ((paddleNE.getX() < ballSE.getX()) 
			&& 
			(paddleNE.getX() > ballSW.getX())
			&&
			(paddleNE.getY() > ballNW.getY())
			&&
			(paddleNE.getY() < ballSW.getY())
			&&
			(vX < 0)) {
				
			vX = -vX;
			vY = -vY;
			bounceClip.play();
			difficulty++;
			
		}
		// if BallSE or BallSW detect paddle then bounce
		else if (isPaddle(ballSE) || isPaddle(ballSW)) {
			if (vY > 0) {
				bounce();
				difficulty++;
			}
		}
		
		
	}
	
/**
 * Check for wall collision. Ball will bounce if wall detected.
 */
	private void checkWallCollision() {
		// Check for wall collision
		if (ball.getX() < 0 || (ball.getX() + BALL_DIAMETER) > WIDTH) {	
			vX = -vX;
		}
		else if (ball.getY() < 0 || (ball.getY() + BALL_DIAMETER) > HEIGHT) {
			vY = -vY;
		}
	}
	
/**
 * Check for brick collision. If collided, ball will bounce and brick
 * will be destroyed.
 */
	private void checkBrickCollision() {
		// Check for brick collision
		if (isBrick(ballSW)){
			removeBrick(ballSW);
		}
		else if (isBrick(ballSE)) { 
			removeBrick(ballSE);
		}
		else if (isBrick(ballNW)) { 
			removeBrick(ballNW);
		}
		else if (isBrick(ballNE)) {
			removeBrick(ballNE);
		}
	}

/**
 * Invert the {@link #vY} of the ball and play the bouncing sound.
 */
	private void bounce() {
		vY = -vY;
		bounceClip.play();
	}
	
/**
 * Check if the ball is colliding with anything.
 * @return true if one of the corners detects collision.
 */
	private boolean collided() {
	return ((getElementAt(ballNW.getLocation()) != null) || (getElementAt(ballNE.getLocation()) != null) || (getElementAt(ballSW.getLocation()) != null) || (getElementAt(ballSE.getLocation()) != null));
}

/**
 * Update the ball collider set of points.
 */
	private void updateBallCollider() {
		ballNW.setLocation(new GPoint(ball.getX(), ball.getY()));
		ballSW.setLocation(new GPoint(ball.getX(), ball.getY() + BALL_DIAMETER));
		ballNE.setLocation(new GPoint(ball.getX() + BALL_DIAMETER, ball.getY()));
		ballSE.setLocation(new GPoint(ball.getX() + BALL_DIAMETER, ball.getY() + BALL_DIAMETER));
	}
	
/**
 * Update the paddle collider set of points.
 */
	private void updatePaddleCollider() {
		paddleNE.setLocation(new GPoint(paddle.getX() + PADDLE_WIDTH, paddle.getY()));
		paddleNW.setLocation(new GPoint(paddle.getX(), paddle.getY()));
	}

/**
 * Update the score on the scoreboard.
 *
 */
	private void updateScore() {
	scoreBoard.setLabel("Life: " + life + " Score: " + score);		
}


/**
 * Check if the element at the {@link GPoint} point is a brick.
 * If yes, remove it and decrease {@link #totalBricks}.
 */
	private void removeBrick(GPoint point) {
		
		GObject brick = getElementAt(point);
		
		// Checks for score counting
		if (brick.getColor() == Color.CYAN) {
			score = score + 1;
		}
		else if (brick.getColor() == Color.GREEN) {
			score = score + 2;
		}
		else if (brick.getColor() == Color.YELLOW) {
			score = score + 3;
		}
		else if (brick.getColor() == Color.ORANGE) {
			score = score + 4;
		}
		else if (brick.getColor() == Color.RED) {
			score = score + 5;
		}
		
		remove(brick);
		totalBricks--;
		bounce();
		updateScore();
	}
	
/**
 * Check if the element currently at this point is a brick.
 * @param point GPoint object that have x and y coordinates.
 * @return true if element is a brick, false otherwise.
 */
	private boolean isBrick(GPoint point) {
		GObject object = getElementAt(point);
		return (object != paddle && object != null && object != blankArea && object != scoreBoard);
	}
	
/**
 * Check if the element currently at this point is the paddle.
 * @param point GPoint object with x and y coordinates.
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
		vX = rgen.nextDouble(1.0, 2.0);
		if (rgen.nextBoolean(0.5)) vX = -vX;
		vY = 2.0;
	}
	
/**
 * Initialize a {@link GRect} object "blank area" in which the mouse motion is detected.
 * Precon: class need to implements {@link MouseMotionListener} interface.
 * Postcon: blankArea initialized with MouseMotionListener added.
 */
	private void initBlankArea() {
		
		blankArea = new GRect(0, 0, WIDTH, HEIGHT);
		
        blankArea.addMouseMotionListener(this);
		addMouseMotionListener(this);
		blankArea.setVisible(false);
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
 * Initialize a {@link GLabel} object that display the current score of the player.
 *
 */
	private void initScoreBoard() {
		scoreBoard = new GLabel("Life: 3 Score: 0");
		scoreBoard.setFont("Arial-bold-20");
		scoreBoard.setColor(Color.BLUE);
		scoreBoard.setLocation(4, HEIGHT - 4);
		add(scoreBoard);
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
