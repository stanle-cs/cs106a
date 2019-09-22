/*
 * Filename: FibonacciSequence.java
 * --------------------------------
 * 
 * Stanle-cs said:
 * A standard Fibonacci sequence program that requires no
 * input thus simplify a lot of shenanigans.
 */
import acm.program.*;

public class FibonacciSequence extends ConsoleProgram{
	
	// Maximum term value that will be computed
	public final int MAX_TERM_VALUE = 10000;
	
	public void run() {
		
		// Initial condition of Fibonacci sequence
		int a = 0;
		int b = 1;
		
		// A temporary variable to hold sum of a and b
		int temp;
		
		println("This program lists the Fibonacci sequence");
		
		// Main loop
		/*
		 * Explaination: at every loop, the value of a + b is calculated
		 * and held in temp. then, we set a equals b, and b equals temp,
		 * then repeat the calculation again with the now two next values
		 * of the Fibonacci sequence. I hope this make sense? 
		 */
		while (a < MAX_TERM_VALUE) {
			println(a);
			temp = a + b;
			a = b;
			b = temp;
		}
	}
}
