import java.util.ArrayList;
import java.util.Scanner;

public class hw4 {
	
	public static void main (String[] args)
	{
		ArrayList<String> single_nodes = new ArrayList<String>();
		ArrayList<String> characters = new ArrayList<String>();
		ArrayList<Integer> frequency = new ArrayList<Integer>();
		String input = get_string("Please enter a nonempty string");
		
		get_nodes(single_nodes, input);
		get_characters(characters, single_nodes);
		get_frequency(frequency, characters, single_nodes);
		merge(characters, frequency);
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
	
	private static void get_nodes(ArrayList<String> single_nodes, String input)
	{
		for (int counter = 0; counter < input.length(); counter++)
		{
			single_nodes.add(Character.toString(input.charAt(counter)));
		}
	}
	
	private static void get_characters(ArrayList<String> characters, ArrayList<String> single_nodes)
	{
		boolean same = false;
		characters.add(single_nodes.get(0));
		
		for (int counter = 0; counter < single_nodes.size(); counter++)
		{
			same = false;
			for (int counter1 = 0; counter1 < characters.size(); counter1++)
			{
				if (single_nodes.get(counter).charAt(0) == characters.get(counter1).charAt(0))
				{
					same = true;
				}
			}
			
			if (!same)
			{
				characters.add(single_nodes.get(counter));
			}
		}
	}
	
	private static void get_frequency(ArrayList<Integer> frequency, ArrayList<String> characters, ArrayList<String> single_nodes)
	{
		for(int counter = 0; counter < characters.size(); counter++)
		{
			frequency.add(0);
			for (int counter1 = 0; counter1 < single_nodes.size(); counter1++)
			{
				if (single_nodes.get(counter1).charAt(0) == characters.get(counter).charAt(0))
				{
					frequency.set(counter, frequency.get(counter) + 1);
				}
			}
		}
	}
	
	private static void display_table(ArrayList<String> characters, ArrayList<Integer> frequency) 
	{
		if (characters.size() > 1 && frequency.size() > 1)
		{
			sort_list(characters, frequency);	
		}
		
		System.out.println("This is the list of all characters sorted by frequency from highest to lowerst:");
		System.out.printf("%1$-12s", "frequency");
		System.out.printf("%1$-12s", "character");
		System.out.println();
		for (int counter = 0; counter < characters.size(); counter++)
		{
			System.out.printf("%1$-12s", frequency.get(counter));
			System.out.printf("%1$-12s", characters.get(counter));
			System.out.println();
			//System.out.print(frequency.get(counter) + "   ");
			//System.out.println(characters.get(counter));
			//System.out.println(frequency.get(counter));
		}
	}
	
	private static void sort_list(ArrayList<String> characters, ArrayList<Integer> frequency)
	{
		boolean done = false;
		String holder = "";
		int holder1 = 0;
		
		while (!done)
		{
			done = true;
			for (int counter = 0; counter < characters.size() - 1; counter++)
			{
				if (frequency.get(counter) < frequency.get(counter + 1))
				{
					done = false;
					holder = characters.get(counter);
					characters.set(counter, characters.get(counter + 1));
					characters.set(counter + 1, holder);
					
					holder1 = frequency.get(counter);
					frequency.set(counter, frequency.get(counter + 1));
					frequency.set(counter + 1, holder1);
				}
			}
		}
	}
	
	private static void merge(ArrayList<String> characters, ArrayList<Integer> frequency)
	{
		boolean done = false;
		Scanner get = new Scanner(System.in);
		String input = "";
		
		while (!done)
		{
			display_table(characters, frequency);
			System.out.println("Press enter to continue");
			input = get.nextLine();
			
			if (characters.size() != 1 && frequency.size() != 1)
			{
				System.out.print("Combined node " + characters.get(characters.size() - 1) + " with weight " + frequency.get(frequency.size() - 1));
				System.out.print(" with node " + characters.get(characters.size() - 2) + " with wight " + frequency.get(frequency.size() - 2));
				
				characters.set(characters.size() - 2, characters.get(characters.size() - 1) + characters.get(characters.size() - 2));
				frequency.set(frequency.size() - 2, frequency.get(frequency.size() - 1) + frequency.get(frequency.size() - 2));
				characters.remove(characters.size() -1);
				frequency.remove(frequency.size() - 1);
				
				System.out.println(" to produce node " + characters.get(characters.size() - 1) + " with weight " + frequency.get(frequency.size() - 1));
			}
			else
			{
				System.out.println("The conbination process is done");
				System.out.println("The final root node is " + characters.get(0) + " with weight of " + frequency.get(0));
				done = true;
			}
		}
	}

}
