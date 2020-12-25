import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/*******************************************************************
 * generic IO class
 ******************************************************************/
public class UserIO {

	public ArrayList<String> getFileContents(String filename)
	{
		ArrayList<String> input = new ArrayList<String>();

		try 
		{
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
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

	public void appendToFile(String data, String filename)
	{
		try{
			BufferedWriter output = new BufferedWriter(new FileWriter(filename, true));
			output.write(data);
			output.close();
		} catch (FileNotFoundException e) {
			System.out.println("File DNE");
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(String filename)
	{
		File myObj = new File(filename);
		myObj.delete();
	}
}
