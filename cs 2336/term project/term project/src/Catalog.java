import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Catalog {
	
	static ArrayList<Book> books = new ArrayList <Book>();
	static ArrayList<Dvd> dvds = new ArrayList <Dvd>();
	static ArrayList<CatalogItem> cart = new ArrayList <CatalogItem>();

	public static void main(String[] args) 
	{
		String role = Input.get_position();
		
		while (!role.equals("c") && !role.equals("C"))
		{
			if (role.equals("a") || role.equals("A"))
			{
				if (FileIO.login())
				{
					admin_choice();
				}
			}
			if (role.equals("b") || role.equals("B"))
			{
				user_choice();
			}
			role = Input.get_position();
		}
	}
	
	private static void admin_choice()
	{
		int selection = 0;
		while (selection != 9)
		{
			Admin.displaymenu();
			selection = Input.get_num(1, 9, "");
			
			if (selection == 1) 
			{
				Admin.add_book(books);
			}
			if (selection == 2)
			{
				Admin.add_audiobook(books);
			}
			if (selection == 3)
			{
				Admin.add_dvd(dvds);
			}
			if (selection == 4) 
			{
				Admin.remove_book(books);
			}
			if (selection == 5) 
			{
				Admin.remove_dvd(dvds);
			}
			if (selection == 6)
			{
				Admin.display(books, dvds);
			}
			if (selection == 7)
			{
				FileIO.backup(books, dvds);
			}
			if (selection > 7 && selection < 9)
			{
				System.out.println("This option is not acceptable");
			}
		}
	}
	
	private static void user_choice()
	{
		int selection = 0;
		while (selection != 9)
		{
			User.displaymenu_b();
			selection = Input.get_num(1, 9, "");
			
			if (selection == 1)
			{
				User.display_books(books);
			}
			if (selection == 2)
			{
				User.display_dvds(dvds);
			}
			if (selection == 3)
			{
				User.add_book(books, cart);
			}
			if (selection == 4)
			{
				User.add_dvd(dvds, cart);
			}
			if (selection == 5)
			{
				User.remove_book(cart);
			}
			if (selection == 6)
			{
				User.remove_dvd(cart);
			}
			if (selection == 7)
			{
				User.display(cart);
			}
			if (selection == 8)
			{
				User.checkout(cart);
			}
		}
	}
}
