/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 * 
 * Stanle-cs said:
 * 
 * Yet again overflow error strikes. We needs to be careful of large
 * numbers to make sure the computer won't hang because of infinite
 * loops. Also, if n is negative the loop will be infinite too. We
 * need to check that edge case.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		
		int n = readInt("Enter a number: ");
		int total = 0;
		boolean success = true;
		
		// Main loop
		while (n!=1) {
			
			// Check if n is negative. If yes stop.
			if (n < 0) {
				success = false;
				println("How come n is negative? Terminating.");
				break;
			}
			
			// Hailstone sequence rules
			if (n % 2 == 0) {
				println(n + " is even, so I take half: " + n/2);
				n = n / 2;
				total++;
			}
			else {
				// Here we can potentially go over max ingeter limit so we have to check first
				if (nIsValid(n)) {
					println(n + " is odd, so I make 3n + 1: " + (3*n + 1));
					n = 3*n + 1;
					total++;
				} else {
					print("3n + 1 too big, will overflow. Terminating.");
					success = false;
					break;
				}
			}
		}
		
		if (success) {
			print("The process took " + total + " steps to reach 1");
		}
	}
	
	// Check if the n given is greater than the maximum n allowed. If yes n is not valid because it will cause overflow.
	// Precon: a value n is present.
	// Postcon: n is checked to make sure it won't causes overflow. nIsValid() returns the answer as true or false.
	private boolean nIsValid(int n) {
		double maxN = (Integer.MAX_VALUE - 1) / 3;
		if (maxN < n) {
			return false;
		} else {
			return true;
		}
	}
	
	
}

