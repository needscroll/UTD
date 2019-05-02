import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserIO {
	
	public String ask_string(String msg) //gets and returns a string
	{
		System.out.println(msg);
		boolean pass = false;
		String input = "";
		Scanner get = new Scanner(System.in);
		
		while (!pass)
		{
			input = get.nextLine();

			if (input != null && input.length() > 0)
			{
				pass = true;
			}
			if (!pass)
			{
				System.out.println("No input detected");
			}
		}
		
		return input;
	}
	
	public int ask_num(int min, int max, String msg) // change function return type for different data types
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
	
	private boolean wrong_input(String input, int min, int max)
	{
		String[] holder_arr = input.split("\\s");
		// change this to check for different conditions
		return holder_arr.length > 1 || Integer.parseInt(input) > max || Integer.parseInt(input) < min;
	}
	
	private File ask_file(String msg)
	{
		//System.out.println(msg);
		File file = new File(ask_string(msg));
		return file;
	}
	
	private File get_file(String msg)
	{
		//System.out.println(msg);
		File file = new File(msg);
		return file;
	}
	
	public String[] ask_file_contents(String msg)
	{
		List<String> input = new ArrayList<String>();

		File file = ask_file(msg);
		Scanner scanner;
		try 
		{
			scanner = new Scanner(file);
			while(scanner.hasNext())
			{
				input.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File DNE");
			//e.printStackTrace();
		}

		return input.toArray(new String[1]);
	}
	
	public String[] get_file_contents(String filename)
	{
		List<String> input = new ArrayList<String>();

		File file = get_file(filename);
		Scanner scanner;
		try 
		{
			scanner = new Scanner(file);
			while(scanner.hasNext())
			{
				input.add(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("File DNE");
			//e.printStackTrace();
		}

		return input.toArray(new String[1]);
	}

}
