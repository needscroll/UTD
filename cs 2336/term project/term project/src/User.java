import java.util.ArrayList;
import java.util.List;

public class User{

	public static void displaymenu_b(){
		System.out.println("Choose from the following options:");
		System.out.println("1 - Browse books inventory (price low to high)");
		System.out.println("2 - Browse DVD inventory (price low to high)");
		System.out.println("3 - Add Book to cart");
		System.out.println("4 - Add DVD to cart");
		System.out.println("5 - Remove Book from cart");
		System.out.println("6 - Remove DVD from cart");
		System.out.println("7 - Display cart");
		System.out.println("8 - Checkout");
		System.out.println("9 - Exit Store");
	}
	
	public static void display_books(ArrayList<Book> books) //displays catalog
	{
		User.sort_books(books);
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
	}
	
	public static void display_dvds(List<Dvd> dvds) //displays catalog
	{
		//prints books
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
	
	public static void display(List<CatalogItem> cart) //displays catalog
	{
		//prints books
		System.out.println("Items in the cart");
		System.out.println("---------------");
		for (int counter = 0; counter < cart.size(); counter++)
		{
			System.out.println(cart.get(counter).get_info());
		}
		if (cart.size() == 0)
		{
			System.out.println("none");
		}
		System.out.println("---------------");
	}
	
	public static void add_book(List<Book> books, List<CatalogItem> cart) //adds a book
	{
		int isbn = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the isbn");
		String title = "";
		String author = "";
		double price = 0;
		double discount = 1;
		boolean found = false;
		
		for (int counter = 0; counter < books.size(); counter++)
		{
			if (books.get(counter).get_isbn() == isbn)
			{
				found = true;
				title = books.get(counter).get_name();
				author = books.get(counter).get_author();
				price = books.get(counter).get_price();
			}
		}
		
		if (found)
		{
			cart.add(new Book(title, price, author, isbn, discount));
		}
		else
		{
			System.out.println("That book was not found in the catalog. Returning to menu");
		}
	}
	
	public static void add_dvd(List<Dvd> dvds, List<CatalogItem> cart) //adds a dvd (needs change)
	{
		int dvdcode = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the dvd code");
		String title = "";
		String director = "";
		double price = 0;
		int year = 0;
		double discount = 1;
		boolean found = false;
		
		for (int counter = 0; counter < dvds.size(); counter++)
		{
			if (dvds.get(counter).get_dvdcode() == dvdcode)
			{
				found = true;
				title = dvds.get(counter).get_name();
				director = dvds.get(counter).get_director();
				price = dvds.get(counter).get_price();
				price = dvds.get(counter).get_year();
			}
		}
		if (found)
		{
			cart.add(new Dvd(title, price, year, dvdcode, director, discount));
		}
		else
		{
			System.out.println("That dvd was not found in the catalog. Returning to menu");
		}
	}
	
	public static void remove_book(List<CatalogItem> cart)
	{
		int isbn = 0;
		boolean done = false;
		System.out.println("This is the list of books in the catalog");
		System.out.println("---------------");
		for (int counter = 0; counter < cart.size(); counter++) //prints books
		{
			if (cart.get(counter) instanceof Book)
			{
				System.out.println(cart.get(counter).get_info());
			}
		}
		System.out.println("---------------");
		
		//gets isbn and removes book
		isbn = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the ISBN number of the book you wish to remove");
		for (int counter = 0; counter < cart.size() && !done ; counter++)
		{
			if (cart.get(counter) instanceof Book)
			{
				Book holder = (Book) cart.get(counter);
				
				if (isbn == holder.get_isbn())
				{
					cart.remove(counter);
					done = true;
				}
			}
		}
		
		//conformation to user
		if (done)
		{
			System.out.println("All books with the isbn of " + isbn + " have been removed");
			System.out.println("The books catalog is now:");
			for (int counter = 0; counter < cart.size(); counter++)
			{
				System.out.println(cart.get(counter).get_info());
			}
			if (cart.size() == 0)
			{
				System.out.println("Empty");
			}
		}
		else
		{
			System.out.println("No books with that isbn were found in the catalog");
		}
	}
	
	public static void remove_dvd(List<CatalogItem> cart)
	{
		int dvdcode = 0;
		boolean done = false;
		System.out.println("This is the list of Dvds in the catalog");
		System.out.println("---------------");
		for (int counter = 0; counter < cart.size(); counter++)
		{
			if (cart.get(counter) instanceof Dvd)
			{
				System.out.println(cart.get(counter).get_info());	
			}
		}
		System.out.println("---------------");
		
		//gets dvdcode and removes book
		dvdcode = Input.get_num(Integer.MIN_VALUE, Integer.MAX_VALUE, "Please enter the dvd code of the dvd you wish to remove");
		for (int counter = 0; counter < cart.size() && !done ; counter++)
		{
			if (cart.get(counter) instanceof Dvd)
			{
				Dvd holder = (Dvd) cart.get(counter);
		
				if (dvdcode == holder.get_dvdcode())
				{
					cart.remove(counter);
					done = true;
				}
			}
		}
		
		//conformation to user
		if (done)
		{
			System.out.println("All dvds with the code of " + dvdcode + " have been removed");
			System.out.println("The dvds catalog is now:");
			for (int counter = 0; counter < cart.size(); counter++)
			{
				System.out.println(cart.get(counter).get_info());
			}
			if (cart.size() == 0)
			{
				System.out.println("Empty");
			}
		}
		else
		{
			System.out.println("No books with that code were found in the catalog");
		}
	}
	
	public static void checkout(List<CatalogItem> cart)
	{
		int total = 0;
		
		System.out.printf("%1$-10s", "price");
		System.out.println("name");
		System.out.println("---------------");
		if (cart.size() > 0)
		{
			for (int counter = 0; counter < cart.size(); counter++)
			{
				total += cart.get(counter).get_price();
				System.out.printf("%1$-10s", cart.get(counter).get_price());
				System.out.println(cart.get(counter).get_name());
			}
			System.out.println("---------------");
			System.out.println("Total Amount: " + total);
			cart.clear();
		}
		else
		{
			System.out.println("Cart is empty. Returning to menu");
		}
		System.out.println("---------------");
	}
	
	public static void sort_books(ArrayList<Book> books)
	{
		boolean done = false;
		Book holder = new Book();
		
		while (!done)
		{
			done = true;
			for (int counter = 0; counter < books.size() - 1; counter++)
			{
				if ((books.get(counter).compareTo(books.get(counter + 1))) > 0)
				{
					done = false;
					holder = books.get(counter);
					books.set(counter, books.get(counter + 1));
					books.set(counter + 1, holder);
				}						
			}
		}
	}
	
	public static void sort_dvds(ArrayList<Dvd> dvds)
	{
		boolean done = false;
		Dvd holder = new Dvd();
		
		while (!done)
		{
			done = true;
			for (int counter = 0; counter < dvds.size() - 1; counter++)
			{
				if (dvds.get(counter).compareTo(dvds.get(counter + 1)) > 0)
				{
					done = false;
					holder = dvds.get(counter);
					dvds.set(counter, dvds.get(counter + 1));
					dvds.set(counter + 1, holder);
				}
			}
		}
	}
}
