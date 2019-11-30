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
import java.util.*;
public class Hangman extends ConsoleProgram {

	/** Random generator used for choosing lexicon */
	private RandomGenerator rgen = new RandomGenerator();

	/** String array holding all possible lexicons */
	private String[] lexiconLibrary = new String[INPUTSIZE];

	/** String containing the secret word to be gueassed */
	private String secretWord = "";

	/** StringBuilder containing the guessing word */
	private StringBuilder guessingWord = new StringBuilder("");

	/** Set of char already guessed by player */
	private Set<Character> guessedChar = new HashSet<Character>();

	/** Indicate the state of the game, finished or not */
	private	boolean done = false;

	/** Number of life player has */
	private int life = 8;
	
	/**
	 * Fill the lexiconLibrary with data from the inputFile and
	 * return one random lexicon from the library
	 * @param inputFile The string name of input file
	 * @return String containing the lexicon
	 */
	private String getLexicon(String inputFile) {
		
		/** BufferedReader used to read from file */
		BufferedReader bufRead = null;
		
		//Open file, throw error if does not exist
		try {
			bufRead = new BufferedReader(new FileReader(inputFile));
			
		}
		catch (IOException e) {
			println("File does not exist\n");
		}

		//Read each line until read null
		int iterator = 0;
		while (true) {
			try {
				String line = bufRead.readLine();
				if (line != null) {
					lexiconLibrary[iterator] = line;
				}
				else break;
			}
			catch (IOException e) {
				println("No line to read!\n");
				break;
			}
			iterator++;
		}

		//Close file
		try {		
		bufRead.close();
		}
		catch (IOException ex) {
			println("Can't close file\n");
		}

		//Return a random lexicon from the library
		return lexiconLibrary[rgen.nextInt(0, lexiconLibrary.length - 1)];
	}

	/** Entry of an acm.jar ConsoleProgram */
	public void run() {
		/* You fill this in */
		secretWord = getLexicon(INPUTFILENAME);
		for (int i = 0; i < secretWord.length(); i++) {
			guessingWord.append('-');
		}
		println("Welcome to Hangman!\nThe word now looks like this " + guessingWord);
		while (true) {
			
			String inputChar = "";
			inputChar = readLine("Your guess: ");
			while (inputChar.length() > 1) {
			inputChar = readLine("Illegal guess.\nYour new guess: ");
			}
			String upercaseInput = inputChar.toUpperCase();
			char ch = upercaseInput.charAt(0);
			guessChar(ch);
			println("The word now looks like this: " + guessingWord);

			checkIfDone();
			if (done) break;
		}
	}

	/**
	 * Check if the secret word has been guessed correctly.
	 * If player run out of life, game results in a lost.
	 */
	private void checkIfDone() {
		if (guessingWord.toString().equals(secretWord)) {
			done = true;
			println("You guessed the word: "+ guessingWord.toString() + "\nYou win.");
		}
		else if (life == 0) {
			done = true;
			println("You're completely hung.\nThe word was: " + secretWord + "\nYou lose.");
		}
	}

	private void guessChar(char guess) {
		guessedChar.add(guess);
		checkSecret(guess);
	}

	private void checkSecret(char guess) {
		boolean found = false;
		for (int i = 0; i < secretWord.length(); i++) {
			if (guess == secretWord.charAt(i)) {
				guessingWord.setCharAt(i, guess);
				found = true;
			}
		}
		if (found) println("That guess is correct.");
		else {
			life--;
			println("There are no " + guess + "'s in the word.\nYou have " + life + " guesses left.");
		}
	}
	/**
	 * Main method used to start the whole program
	 * @param args Not used but neat to have anyways
	 */
	public static void main(String[] args) {
		Hangman hm = new Hangman();
		hm.start(args);
	}

	/** Number of line in the input file */
	private static final int INPUTSIZE = 121806;
	
	/** Name of the input file */
	private static final String INPUTFILENAME = "HangmanLexicon.txt";
	
}
