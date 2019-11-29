/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

//import acm.graphics.*;
import acm.program.*;
//import acm.util.*;
import acm.util.RandomGenerator;

//import java.awt.*;
import java.io.*;

public class Hangman extends ConsoleProgram {

	File inputFile = new File("HangmanLexicon.txt");
	RandomGenerator rgen = new RandomGenerator();

	private String getLexicon(File inputfile) {
		try {
			FileReader reader = new FileReader(inputFile);
			BufferedReader bufRead = new BufferedReader(reader);
			String[] output = new String[10000];
			int i = 0;
			while (true) {
				try {
					output[i] = bufRead.readLine();
				}
				catch (IOException e) {

					break;
				}
				i++;
			}
			bufRead.close();
			return output[rgen.nextInt(0, output.length - 1)];
		}
		catch (IOException e) {
			println("Oh no! File not exist");
		}
		return "";
	}

    public void run() {
		/* You fill this in */
		println(getLexicon(inputFile));
	}

	public static void main(String[] args) {
		Hangman hm = new Hangman();
		hm.start(args);
	}

}
