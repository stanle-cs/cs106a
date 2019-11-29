import	acm.program.*;

public class AddCommas extends ConsoleProgram {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void run() {
		while (true) {
			String digits = readLine("Enter a numeric string: ");
			if (digits.length() == 0) {
				println("Done? Exiting...");
				break;
			}
			println(addCommasToNumericString(digits));
		}
		println("Exited");
	}
	
	/**
	 * Add one comma every three digit starting from the least significant figure.
	 * */
	private String addCommasToNumericString(String digits) {
		
		//Initiatial values, starting with null is not recommended
		String	result = "";
		int		count = 0;
		
		//Scan each digit starting from last to first
		for (int i = digits.length() - 1; i >= 0; i--) {
			result = digits.charAt(i) + result;
			count++;
			
			/*
			 * count != 0: not last digit
			 * count % 3 == 0: every 3 digit
			 * i != 0: not first digit
			 */
			if ((count !=0) && (count % 3 == 0) && (i !=0)) {
				result = "," + result;
			}
		}
		
		return result;
	}
}
