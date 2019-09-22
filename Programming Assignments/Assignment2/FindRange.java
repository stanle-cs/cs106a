/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 * 
 * Stanle-cs said:
 * 
 * This is a very simple program without any hoops. It probably
 * won't work for large amount of input?
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	
	// The sentinel value used to end input
	public static final int SENTINEL = 0;
	
	public void run() {
		
		int a;
		int c = 0;
		
		// Set max to smallest possible integer
		int max = Integer.MIN_VALUE;
		// Set min to largest possible integer
		int min = Integer.MAX_VALUE;
		
		while (true) {
			a = readInt(" ? ");
			if (a == SENTINEL) {
				break;
			}
			if ( a > max) {
				max = a;
			}
			if (a < min) {
				min = a;
			}
			// Increase c every time we have a good number that is not sentinel
			c++;
		}
		
		// Only when sentinel is the first input can c be equal to 0. I  that case the min and max are wrong so we should just not output them.
		if (c !=0) {
			println("smallest: " + min);
			println("largest: " + max);
		} else {
			print("No value have been entered.");
		}
	}
}

