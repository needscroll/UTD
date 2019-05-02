import java.util.ArrayList;
import java.util.Scanner;

public class Input implements Acceptable{
	
	static Validator valid = new Validator();
	
	public static String get_position()
	{
		String input = Input.get_string("Please select your role\nA - store manager\nB - Customer\nC - exit store");
		
		while (!input.equals("a") && !input.equals("A") && !input.equals("b") && !input.equals("B") && !input.equals("c") && !input.equals("C"))
		{
			input = Input.get_string("Invalid input. Please try again");
		}
		
		return input;
	}
	
	public static String get_string(String msg) //gets and returns a string
	{
		System.out.println(msg);
		boolean pass = false;
		String input = "";
		Scanner get = new Scanner(System.in);
		
		while (!pass)
		{
			input = get.nextLine();

			if (valid.isNonEmptyString(input))
			{
				pass = true;
			}
			if (!pass)
			{
				System.out.println("Invalid input. Please try again.");
			}
		}
		
		return input;
	}
	
	public static int get_num(int min, int max, String msg) // change function return type for different data types
	{
		String input = "";
		boolean gotnumber = false;
		int input_i = -1;
		Scanner get = new Scanner(System.in);

		while (!gotnumber) {
			System.out.println(msg);
			try {
				input = get.nextLine();

				if (wrong_input(input, min, max)) {
					//System.out.println("invalid input, please try again");
					System.out.println("This option is not acceptable");
				} else 
				{
					input_i = Integer.parseInt(input);
					gotnumber = true;					
				}
			} catch (NumberFormatException nfe) {
				System.out.println("invalid data type, please try again");
			} catch (Exception Ex) {
				System.out.println("general exception");
			}
		}
		
		return input_i;
	}
	
	private static boolean wrong_input(String input, int min, int max)
	{
		String[] holder_arr = input.split("\\s");
		// change this to check for different conditions
		return holder_arr.length > 1 || Integer.parseInt(input) > max || Integer.parseInt(input) < min || Double.parseDouble(input) > max || Double.parseDouble(input) < min;
	}
	
	public static double get_double(String msg) //input validation for doubles
	{
		String input = "";
		boolean gotnumber = false;
		double input_i = -1;
		Scanner get = new Scanner(System.in);

		while (!gotnumber) {
			System.out.println(msg);
			try {
				input = get.nextLine();

				if (!valid.isPositiveInput(Double.parseDouble(input))) {
					System.out.println("This option is not acceptable");
				} else 
				{
					input_i = Double.parseDouble(input);
					gotnumber = true;					
				}
			} catch (NumberFormatException nfe) {
				System.out.println("invalid data type, please try again");
			} catch (Exception Ex) {
				System.out.println("general exception");
			}
		}
		
		return input_i;
	}
	
	public boolean isNonEmptyString(String input)
	{
		boolean pass = false;
		
		for (int counter = 0; counter < input.length(); counter++)
		{
			if (input.charAt(counter) != ' ')
			{
				pass = true;
			}
		}
		
		return pass;
	}
	
	public boolean isPositiveInput(double input)
	{
		boolean pass = false;
		if (input > 0)
		{
			pass = true;
		}
		
		return pass;
	}
}
