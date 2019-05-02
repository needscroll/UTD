import java.util.Arrays;
import java.util.Scanner;

public class hw2 {
	
	public static void main(String[] args)
	{
		String[] books = {"Intro to Java", "Intro to c++", "Python", "Perl", "C#"};
		double[] book_prices = {45.99, 89.34, 100.00, 25.0 , 49.99};
		
		String[] dvds = {"Snow White", "Cinderella", "Dumbo", "Bambi", "Frozen"};
		double[] dvd_prices = {19.99, 24.99, 17.99, 21.99, 24.99};
		
		String[] cart_items = new String[5];
		double[] cart_prices = new double[5];
		
		clearArrays(cart_items, cart_prices);
		int selection = -1;
		
		while (selection != 8)
		{
			selection = get_num(1, 8, "menu");
			
			if (selection == 1)
			{
				displayArrays(books, book_prices, "books");
			}
			if (selection == 2)
			{
				displayArrays(dvds, dvd_prices, "dvds");
			}
			if (selection == 3)
			{
				getInventoryNumber(cart_items, cart_prices, books, book_prices);
			}
			if (selection == 4)
			{
				getInventoryNumber(cart_items,cart_prices, dvds, dvd_prices);
			}
			if (selection == 5)
			{
				displayArrays(cart_items, cart_prices);
			}
			if (selection == 6)
			{
				getTotal(cart_items, cart_prices);
			}
			if (selection == 7)
			{
				clearArrays(cart_items, cart_prices);
			}
		}
	}
	
	private static void displaymenu()
	{
		System.out.println("**Welcome to the Coments Books and DVDs Store**");
		System.out.println("Choose from the following options:");
		System.out.println("1 - Brows books inventory (price low to high)");
		System.out.println("2 - Browse DVDs inventory (price low to high)");
		System.out.println("3- Add a book to the cart");
		System.out.println("4- Add a DVD to the cart");
		System.out.println("5- View cart");
		System.out.println("6 - Checkout");
		System.out.println("7 - Cancel Order");
		System.out.println("8 - Exit store");
	}
	
	private static int get_num(int min, int max, String choice) // change function return type for different data types
	{
		String input = "";
		boolean gotnumber = false;
		int input_i = -1; // change for different data types
		Scanner get = new Scanner(System.in);
		
		while (!gotnumber)
		{
			if (choice == "menu")
			{
				displaymenu();
			}
			
			try
			{
				input = get.nextLine();
				
				if (wrong_input(input, min, max))
				{
					System.out.println("invalid input, please try again");
				}
				else
				{
					input_i = Integer.parseInt(input); // change for different data types
					gotnumber = true;
				}
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("invalid data type, please try again");
			}
			catch (Exception Ex)
			{
				System.out.println("general exception");
			}
		}
		
		return input_i;
	}
	
	private static boolean wrong_input(String input, int min, int max)
	{
		String[] holder_arr = input.split("\\s");
		
		return holder_arr.length > 1 || Integer.parseInt(input) > max || Integer.parseInt(input) < min; // change this to check for different conditions
	}
	
	private static void displayArrays(String[] items, double[] prices, String item_type) // for option 1 and 2
	{
		int[] order = new int[items.length];
		double[] copy = Arrays.copyOf(prices, prices.length);
		double min = 999;
		
		//sorts array
		for (int counter = 0; counter < prices.length; counter++)
		{
			if (copy[counter] < min)
			{
				min = copy[counter];
				order[0] = counter;
			}
		}
		copy[order[0]] = 0;

		for (int counter = 1; counter < prices.length; counter++)
		{
			double suspect = 999;
			for (int counter1 = 0; counter1 < prices.length; counter1++)
			{
				if (copy[counter1] >= prices[order[counter - 1]] && copy[counter1] < suspect)
				{
					suspect = copy[counter1];
					order[counter] = counter1;
				}
			}
			copy[order[counter]] = 0;
		}
		
		System.out.printf("%-20s", "Inventory Number");
		System.out.printf("%-20s", item_type);
		System.out.printf("%-20s", "Prices");
		System.out.println();
		System.out.println("----------------------------------------------");
		
		for (int counter = 0; counter < order.length; counter++)
		{
			System.out.printf("%-20s", order[counter] + 1);
			System.out.printf("%-20s", items[order[counter]]);
			System.out.printf("%-20s", prices[order[counter]]);
			System.out.println();
		}
	}
	
	private static void getInventoryNumber(String[] cart_items, double[] cart_prices, String[] items, double[] item_prices) // for option 3 and 4
	{
		System.out.println("enter the inventory number to add or -1 to return to menu");
		int selection = 0;
		selection = get_num(-1, 5, "");
		
		while (selection == 0)
		{
			System.out.println("invalid input. Enter a number from 1 to 5 or -1 to return to menu");
			selection = get_num(-1, 5, "");
		}
		
		//adds items to cart
		if (selection > 0 && selection < 6)
		{
			boolean notpass = true;
			for (int counter = 0; counter < cart_items.length && notpass; counter++)
			{
				if (cart_items[counter].length() == 0 && cart_prices[counter] == -1)
				{
					cart_items[counter] = items[selection - 1];
					cart_prices[counter] = item_prices[selection - 1];
					notpass = false;
				}
			}
			
			if (notpass)
			{
				System.out.println("Cart is full. Item was not added. Program will now return to menu");
			}
		}
	}
	
	private static void displayArrays(String[] cart_items, double[] cart_prices) // for option 5
	{
		System.out.printf("%-20s", "Items");
		System.out.printf("%-20s", "Prices");
		System.out.println();
		System.out.println("--------------------------");
		
		//displays items
		if (!empty(cart_items))
		{
			for (int counter = 0; counter < cart_items.length; counter++)
			{
				if (cart_items[counter].length() > 0 && cart_prices[counter] != -1)
				{
					System.out.printf("%-20s", cart_items[counter]);
					System.out.printf("%-20s", cart_prices[counter]);
					System.out.println();
				}
			}
			System.out.println("--------------------------");
		}
		else
		{
			System.out.println("--------------------------");
			System.out.println("Cart is empty. Returning to menu");

		}
		
		
	}
	
	private static void getTotal(String[] cart_items, double[] cart_prices) // option 6
	{
		double total = 0;
		double tax = 0;
		
		displayArrays(cart_items, cart_prices);
		
		if (!empty(cart_items))
		{
			
			for (int counter = 0; counter < cart_items.length; counter++)
			{
				if (cart_prices[counter] != -1)
				{
					total += cart_prices[counter];
				}
				
			}
			
			tax = total * .0825;
			System.out.printf("%-20s", "Total + Tax");
			System.out.printf("%-20.5s", total + tax);
			System.out.println();
		}
		clearArrays(cart_items, cart_prices);
	}
	
	private static boolean empty(String[] items) 
	{
		boolean empty = true;
		//is cart emtpy
		for (int counter = 0; counter < items.length; counter++)
		{
			if (items[counter].length() > 0)
			{
				empty = false;
			}
		}
		
		return empty;
	}
	
	private static void clearArrays(String[] cart_items, double[] cart_prices)
	{
		Arrays.fill(cart_items, "");
		Arrays.fill(cart_prices, -1);
	}
}
