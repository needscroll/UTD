import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemoryManager {
	
	static Memory memory;
	static UserIO io = new UserIO();

	static boolean running = true;
	static int i = 0;
	
	public static void main(String[] args)
	{
		System.out.println("child process has started");
		for (String argument: args)
		{
			System.out.println("This is the argument: " + argument);
		}
		
		String filename;
		String[] result = new String[100]; //this contains the input program file code by lines
		if (args.length > 0)
		{
			filename = args[0];
			result = io.get_file_contents(filename);
			result = clean_input(result);
			
			System.out.println("this is the program:");
			for (String string: result)
			{
				System.out.println(string);
			}
			
			memory.init_memory(result);
		}
		
		
		System.out.println("memory is now fully initialized and started");
        Scanner console = new Scanner(System.in);
		while(running)
		{
			if (console.hasNextLine())
			{
				String in = console.nextLine();
				if (in != null)
				{
					System.out.println(getProperValue(in));
				}
			}
			i++;
		}
	}
	
	//THIS NEEDS LOGIC FOR GETTING FROM MEMORY AND WRITING TO MEMORY
	/*
	 * read requests are in the form of r3298 where the number is the address
	 * write requests are in the form of w324:324 where the fisrt number is the address and the second number is the value 
	 * so far, it is assumed that all inputs are of the valid type
	 */
	public static String getProperValue(String in)
	{		
		String result = "child recieved:<" + in + ">" + " child is on rotation: " + Integer.toString(i);
		
		
		if (in.length() >= 2)
		{
			if (in.charAt(0) == 'r')
			{
				String[] split = in.split("r");
				//need see if split is actually numeric
				result += " memory request detected, returning:<" + memory.data[Integer.parseInt(split[1])] + ">";
			}
			if (in.charAt(0) == 'w')
			{
				String[] split = in.split(":");
				result += " write request detected, writing address " + split[1] + " with " + split[2];
				//need overwrite memory here
				memory.set_data(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
			}
		}
		
		return result;
	}
	
	public static String[] clean_input(String[] input)
	{
		List<String> cleaned_input = new ArrayList<String>();
		
		for (String string: input)
		{
			String num = "";
			for (int i = 0; i < string.length(); i++)
			{
				char point = string.charAt(i);
				
				
				
				if (i == 0 && point == '.')
				{
					num += string.charAt(i);
				}
				
				if (Character.isDigit(point))
				{
					num += string.charAt(i);
				}
				
				if (point == '/')
				{
					break;
				}
			}
			
			if (num.length() > 0)
			{
				cleaned_input.add(num);
			}
			
			/*
			if (string.length() > 0 && Character.isDigit(string.charAt(0)))
			{
				cleaned_input.add(string.split(" ")[0]);
			}*/
		}	

		return cleaned_input.toArray(new String[1]);
		
	}
}
