import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*******************************************************************
 * generic IO class
 ******************************************************************/
public class UserIO {

	public ArrayList<String> get_file_contents(String filename)
	{
		ArrayList<String> input = new ArrayList<String>();

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

		return input;
	}

	private File get_file(String msg)
	{
		//System.out.println(msg);
		File file = new File(msg);
		return file;
	}
}
