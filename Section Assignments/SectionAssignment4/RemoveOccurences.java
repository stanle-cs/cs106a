import	acm.program.*;
public class RemoveOccurences extends ConsoleProgram {
	
	public void run() {
		while (true) {
			String str = readLine("Enter a string: ");
			if (str.length() == 0) {
				println("Done? Exiting...");
				break;
			}
			println(removeAllOccurrences(str, 'a'));
		}
		println("Exited");
	}
	
	private String removeAllOccurrences( String str, char ch) {
		String result = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ch) {
			result = result + str.charAt(i);
			}
		}
		
		return result;
	}
}
