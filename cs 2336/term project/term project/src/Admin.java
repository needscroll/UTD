import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class Admin {
	
	public static void displaymenu()
	{
		System.out.println("Choose from the following options:");
		System.out.println("1 - Add Book");
		System.out.println("2 - Add AudioBook");
		System.out.println("3 - Add DVD");
		System.out.println("4 - Remove Book");
		System.out.println("5 - Remove DVD");
		System.out.println("6 - Display catalog");
		System.out.println("7 - Create backup file");
		System.out.println("9 - Exit catalog");
	}
	
	public static void add_book(List<Book> books) //adds a book
	{
		int isbn = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the isbn");
		
		for (int counter = 0; counter < books.size(); counter++)
		{
			if (books.get(counter).get_isbn() == isbn)
			{
				System.out.println("A book with that isbn already exists. Returning to menu.");
				return;
			}
		}
		
		String title = Input.get_string("Please enter the title");
		String author = Input.get_string("Please enter the author");
		double price = Input.get_double("Please enter the price");
		double discount = Input.get_double("Please enter the discount value (default is set to .9)");
		
		books.add(new Book(title, price, author, isbn, discount));
	}
	
	public static void add_audiobook(List<Book> books) //adds an audiobook
	{
		int isbn = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the isbn");
		
		for (int counter = 0; counter < books.size(); counter++)
		{
			if (books.get(counter).get_isbn() == isbn)
			{
				System.out.println("A book with that isbn already exists. Returning to menu.");
				return;
			}
		}
		
		String title = Input.get_string("Please enter the title");
		String author = Input.get_string("Please enter the author");
		double price = Input.get_double("Please enter the price");
		double runtime = Input.get_double("Please enter the running time");
		double discount = Input.get_double("Please enter the discount value (the default is set to .5)");
		
		books.add(new AudioBook(title, price, author, isbn, runtime, discount));
	}
	
	public static void add_dvd(List<Dvd> dvds) //adds a dvd
	{
		int dvdcode = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the dvd code");
		
		for (int counter = 0; counter < dvds.size(); counter++)
		{
			if (dvds.get(counter).get_dvdcode() == dvdcode)
			{
				System.out.println("A book with that isbn already exists. Returning to menu.");
				return;
			}
		}
		
		String title = Input.get_string("Please enter the title");
		String director = Input.get_string("Please enter the director");
		double price = Input.get_double("Please enter the price");
		int year = Input.get_num(0, Integer.MAX_VALUE, "Please enter the year");
		double discount = Input.get_double("Please enter the discount value (the default is set to .8)");
		
		dvds.add(new Dvd(title, price, year, dvdcode, director, discount));
	}
	
	public static void display(List<Book> books, List<Dvd> dvds) //displays catalog
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
	
	public static void remove_book(List<Book> books) //removes a book
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
		isbn = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the ISBN number of the book you wish to remove");
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
	
	public static void remove_dvd(List<Dvd> dvds) //removes a dvd
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
		dvdcode = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the dvd code of the dvd you wish to remove");
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
