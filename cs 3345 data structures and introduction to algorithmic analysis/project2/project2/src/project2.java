import java.util.Scanner;

public class project2 {
	
	public static void main(String args[])
	{
		LinkedList<Magazine> list = new LinkedList<Magazine>();
		print_menu();
		int choice = get_num(1, 7, "select from options");
		while (choice != 7)
		{
			if (choice == 1)
			{
				list.makeEmpty();
			}
			if (choice == 2)
			{
				Magazine holder = list.findID(get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "enter the magazine ID"));
				if (holder == null)
				{
					System.out.println("not found"); // checking if empty
				}
				else
				{
					holder.printID();
				}
			}
			if (choice == 3)
			{
				list.insertAtFront(get_mag());
			}
			if (choice == 4)
			{
				list.deleteFromFront();
			}
			if (choice == 5)
			{
				list.delete(get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "enter the magazine ID"));
			}
			if (choice == 6)
			{
				list.printAllRecords();
			}
			
			print_menu();
			choice = get_num(1, 7, "select from options");
		}
		
	}
	
	public static Magazine get_mag() // returns a magazine
	{
		int id = get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Enter magazine id: ");
		String name = get_string("Enter magazine name: ");
		String publisher = get_string("Enter publisher name: ");
		
		return new Magazine(id, name, publisher);
	}
	
	public static void print_menu() // print menu
	{
		System.out.println("");
		System.out.println("Operations on list");
		System.out.println("1. make empty");
		System.out.println("2. find id");
		System.out.println("3. insert at front");
		System.out.println("4. delete from front");
		System.out.println("5. delete id");
		System.out.println("6. Print all records");
		System.out.println("7. quit");
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
	
	public static String get_string(String msg) //gets and returns a string
	{
		System.out.println(msg);
		boolean pass = false;
		String input = "";
		Scanner get = new Scanner(System.in);
		
		while (!pass)
		{
			input = get.nextLine();

			if (isNonEmptyString(input))
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
	
	public static boolean isNonEmptyString(String input) // check if empty string
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

}
