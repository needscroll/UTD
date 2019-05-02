import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FileIO {

	public static boolean login()
	{
		boolean pass = false;
		String username = Input.get_string("Please enter your username");
		String password = Input.get_string("Please enter your password");
		String filename = "credentials.txt";
		File file = new File(filename);
		ArrayList<String> info = new ArrayList<String>();
		
		try
		{
			Scanner inputFile = new Scanner(file);
			for (int counter = 0; inputFile.hasNextLine(); counter++)
			{
				info.add(inputFile.nextLine());
			}
		}
		catch(FileNotFoundException notfound)
		{
			System.out.println("File not found");
		}
		catch(Exception ex)
		{
			System.out.println("General exception");
		}
		
		for (int counter = 0; counter < info.size(); counter++)
		{
			if (username.equals(info.get(counter).split(",")[0]) && password.equals(info.get(counter).split(",")[1]))
			{
				pass = true;
			}
		}

		if (pass)
		{
			System.out.println("Login successful. Proceeding to next menu.");
		}
		else
		{
			System.out.println("Login failed. Invalid credentials.");
		}
		
		return pass;
	}
	
	public static void backup(ArrayList<Book> books, ArrayList<Dvd> dvds)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date now = new Date();
		String filename = "backup" + dateFormat.format(now) + ".txt";
		//filename = "a.txt";
		
		try {
			//File file = new File(filename);
			PrintWriter pw = new PrintWriter(filename);
			for (int counter = 0; counter < books.size(); counter++)
			{
				pw.println(books.get(counter).get_info());
			}
			for (int counter = 0; counter < dvds.size(); counter++)
			{
				pw.println(dvds.get(counter).get_info());
			}
			pw.close();
		}
		catch (FileNotFoundException fnfx) {
			System.out.println("File: " + fnfx + " was not found.");
		}
		System.out.println("Backup made. Returning to menu");
	}
}
