import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catalog {

	public static void main(String args[]) {

		int selection = -1;
		List<Book> books = new ArrayList <Book>();
		List<Dvd> dvds = new ArrayList <Dvd>();

		while (selection != 9) 
		{
			selection = get_num(1, 9, "");

			if (selection == 1) 
			{
				add_book(books);
			}
			if (selection == 2)
			{
				add_audiobook(books);
			}
			if (selection == 3)
			{
				add_dvd(dvds);
			}
			if (selection == 4) 
			{
				remove_book(books);
			}
			if (selection == 5) 
			{
				remove_dvd(dvds);
			}
			if (selection == 6)
			{
				display(books, dvds);
			}
			if (selection > 6 && selection < 9)
			{
				System.out.println("This option is not acceptable");
			}
		}
	}

	private static void displaymenu() {
		System.out.println("**Welcome to the Coments Books and DVDs Store (Now with the Catalog Edition!)**");
		System.out.println("Choose from the following options:");
		System.out.println("1 - Add Book");
		System.out.println("2 - Add AudioBook");
		System.out.println("3 - Add DVD");
		System.out.println("4 - Remove Book");
		System.out.println("5 - Remove DVD");
		System.out.println("6 - Display Catalog");
		System.out.println("9 - Exit Store");
	}

	private static int get_num(int min, int max, String msg) // change function return type for different data types
	{
		String input = "";
		boolean gotnumber = false;
		int input_i = -1;
		Scanner get = new Scanner(System.in);

		while (!gotnumber) {
			System.out.println(msg);
			if (msg == "")
			{
				displaymenu();
			}

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
	
	private static double get_double(String msg) //input validation for doubles
	{
		String input = "";
		boolean gotnumber = false;
		double input_i = -1;
		Scanner get = new Scanner(System.in);

		while (!gotnumber) {
			System.out.println(msg);
			try {
				input = get.nextLine();

				if (Double.parseDouble(input) < 0) {
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
	
	private static boolean wrong_input(String input, int min, int max)
	{
		String[] holder_arr = input.split("\\s");
		// change this to check for different conditions
		return holder_arr.length > 1 || Integer.parseInt(input) > max || Integer.parseInt(input) < min || Double.parseDouble(input) > max || Double.parseDouble(input) < min;
	}
	
	private static String get_string(String msg) //gets and returns a string
	{
		System.out.println(msg);
		boolean pass = true;
		boolean bad = false;
		String input = "";
		Scanner get = new Scanner(System.in);
		
		while (pass)
		{
			input = get.nextLine();
			if (input.length() == 0)
			{
				bad = true;
			}
			for (int counter = 0; counter < input.length(); counter++)
			{
				if (input.charAt(counter) != ' ')
				{
					pass = false;
					bad = false;
				}
				else
				{
					bad = true;
				}
			}
			if (bad)
			{
				System.out.println("please enter a valid non blank input");
			}
		}
		
		return input;
	}
	
	private static void add_book(List<Book> books) //adds a book
	{
		int isbn = get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the isbn");
		
		for (int counter = 0; counter < books.size(); counter++)
		{
			if (books.get(counter).get_isbn() == isbn)
			{
				System.out.println("A book with that isbn already exists. Returning to menu.");
				return;
			}
		}
		
		String title = get_string("Please enter the title");
		String author = get_string("Please enter the author");
		double price = get_double("Please enter the price");
		
		books.add(new Book(title, price, author, isbn));
	}
	
	private static void add_audiobook(List<Book> books) //adds an audiobook
	{
		int isbn = get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the isbn");
		
		for (int counter = 0; counter < books.size(); counter++)
		{
			if (books.get(counter).get_isbn() == isbn)
			{
				System.out.println("A book with that isbn already exists. Returning to menu.");
				return;
			}
		}
		
		String title = get_string("Please enter the title");
		String author = get_string("Please enter the author");
		double price = get_double("Please enter the price");
		double runtime = get_double("Please enter the running time");
		
		books.add(new AudioBook(title, price, author, isbn, runtime));
	}
	
	private static void add_dvd(List<Dvd> dvds) //adds a dvd
	{
		int dvdcode = get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the dvd code");
		
		for (int counter = 0; counter < dvds.size(); counter++)
		{
			if (dvds.get(counter).get_dvdcode() == dvdcode)
			{
				System.out.println("A book with that isbn already exists. Returning to menu.");
				return;
			}
		}
		
		String title = get_string("Please enter the title");
		String director = get_string("Please enter the director");
		double price = get_double("Please enter the price");
		int year = get_num(0, Integer.MAX_VALUE, "Please enter the year");
		
		dvds.add(new Dvd(title, price, year, dvdcode, director));
	}
	
	private static void display(List<Book> books, List<Dvd> dvds) //displays catalog
	{
		//prints books
		System.out.println("Books in the catalog");
		System.out.println("---------------");
		for (int counter = 0; counter < books.size(); counter++)
		{
			if (books.get(counter) instanceof Book && !(books.get(counter) instanceof AudioBook))
			{
				System.out.println(books.get(counter).get_info());
			}
		}
		if (books.size() == 0)
		{
			System.out.println("none");
		}
		System.out.println("---------------");
		
		//prints audiobooks
		System.out.println("AudioBooks in the catalog");
		System.out.println("---------------");
		for (int counter = 0; counter < books.size(); counter++)
		{
			if (books.get(counter) instanceof Book && (books.get(counter) instanceof AudioBook))
			{
				System.out.println(books.get(counter).get_info());
			}
		}
		if (books.size() == 0)
		{
			System.out.println("none");
		}
		System.out.println("---------------");
		
		//prints dvds
		System.out.println("Dvds in the catalog");
		System.out.println("---------------");
		for (int counter = 0; counter < dvds.size(); counter++)
		{
			System.out.println(dvds.get(counter).get_info());
		}
		if (dvds.size() == 0)
		{
			System.out.println("none");
		}
		System.out.println("---------------");
	}
	
	private static void remove_book(List<Book> books) //removes a book
	{
		int isbn = 0;
		boolean done = false;
		boolean pass = true;
		System.out.println("This is the list of books in the catalog");
		System.out.println("---------------");
		for (int counter = 0; counter < books.size(); counter++) //prints books
		{
			System.out.println(books.get(counter).get_info());
		}
		System.out.println("---------------");
		
		//gets isbn and removes book
		isbn = get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the ISBN number of the book you wish to remove");
		while (pass)
		{
			pass = false;
			for (int counter = 0; counter < books.size(); counter++)
			{
				if (isbn == books.get(counter).get_isbn())
				{
					books.remove(counter);
					done = true;
					pass = true;
				}
			}
		}
		
		//conformation to user
		if (done)
		{
			System.out.println("All books with the isbn of " + isbn + " have been removed");
			System.out.println("The books catalog is now:");
			for (int counter = 0; counter < books.size(); counter++)
			{
				System.out.println(books.get(counter).get_info());
			}
			if (books.size() == 0)
			{
				System.out.println("Empty");
			}
		}
		else
		{
			System.out.println("No books with that isbn were found in the catalog");
		}
	}
	
	private static void remove_dvd(List<Dvd> dvds) //removes a dvd
	{
		int dvdcode = 0;
		boolean done = false;
		boolean pass = true;
		System.out.println("This is the list of Dvds in the catalog");
		System.out.println("---------------");
		for (int counter = 0; counter < dvds.size(); counter++)
		{
			System.out.println(dvds.get(counter).get_info());
		}
		System.out.println("---------------");
		
		//gets dvdcode and removes dvd
		dvdcode = get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the dvd code of the dvd you wish to remove");
		while (pass)
		{
			pass = false;
			for (int counter = 0; counter < dvds.size(); counter++)
			{
				if (dvdcode == dvds.get(counter).get_dvdcode())
				{
					dvds.remove(counter);
					done = true;
					pass = true;
				}
			}
		}
		
		//conformation to user
		if (done)
		{
			System.out.println("All dvds with the dvd code of " + dvdcode + " have been removed");
			System.out.println("The dvd catalog is now:");
			for (int counter = 0; counter < dvds.size(); counter++)
			{
				System.out.println(dvds.get(counter).get_info());
			}
			if (dvds.size() == 0)
			{
				System.out.println("Empty");
			}
		}
		else
		{
			System.out.println("No dvds with that code were found in the catalog");
		}
	}
}
