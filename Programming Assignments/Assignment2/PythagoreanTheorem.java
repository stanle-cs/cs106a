/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 * 
 * Stanle-cs said:
 * simple program but got many catches due to wierd values. Hopefully
 * resolved using many different control structures.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Pythagorean theorem.");
		int a = readInt("a: ");
		int b = readInt("b: ");
		if (a > 0 && b > 0) {
			double sqrtA = a*a;
			double sqrtB = b*b;
			double sqrtC = sqrtA + sqrtB;
			if (sqrtA < 0 || sqrtB <0 || sqrtC < 0) {
				
				// If the squares are negative, we knows that we have hit overflow.
				println ("a^2: " + sqrtA + " b^2: " + sqrtB + " c^2: " + sqrtC);
				println("Got negative values, possibly due to overflow. Unable to compute c.");
				
			} else {
				
				// If the squares looks good, then compute c
				double c = Math.sqrt(sqrtC);
				
				// But if c is unfortunately smaller than a or b (which is impossible for Pythagorean theorem) then we got a bogus value
				if (c < a || c < b) {
					println ("a^2: " + sqrtA + " b^2: " + sqrtB + " c^2: " + sqrtC + " ==> c: " + c);
					println("Result is wrong: c is smaller than a or b, possibly due to overflow.");
				} else {
					// Only when c is larger than a and b that we can be sure c is legit
					println("c = " + c);
				}
			}
		} else {
			
			// Catching negative inputs (impossible for triangle edges)
			println("Invalid values: a or b is negative.");
		}
	}
}