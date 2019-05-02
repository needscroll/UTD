import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class project4 {
	
	public static void main(String args[])
	{
		RedBlackTree a = new RedBlackTree<Integer>();
		RedBlackTree b = new RedBlackTree<String>();
		ArrayList<String> inputs = new ArrayList<String>();


		if (args.length > 1)
		{
			get_inputs(inputs, args[0]);
			
			if (inputs.get(0).equals("Integer"))
			{
				print_output(inputs, args[1], a, true);
			}
			else
			{
				print_output(inputs, args[1], b, false);
			}
			
		}
		else
		{
			System.out.println("please provide the input and output files");
		}
		
	}
	
	/*
	 * gets input
	 */
	public static void get_inputs(ArrayList<String> inputs, String filename)
	{
		File inputfile = new File(filename);

		try {
			Scanner input = new Scanner(inputfile);
			while (input.hasNextLine())
			{
				inputs.add(input.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.out.println("inputfile not found");
		}
	}
	
	/*
	 * prints output for integers
	 */
	private static void print_output(ArrayList<String> inputs, String filename, RedBlackTree tree, boolean type) 
	{
		File outputfile = new File(filename);
		
		try {
			PrintStream output = new PrintStream(outputfile);
			for (int counter = 1; counter < inputs.size(); counter++)
			{
				if (type)
				{
					interpret(output, inputs.get(counter), tree);
				}
				else
				{
					interpret2(output, inputs.get(counter), tree);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
	}
	
	/*
	 * prints output for strings
	 */
	private static void interpret2(PrintStream output, String input, RedBlackTree tree) {
		String[] split = input.split(":");
		
		if (split.length >= 2)
		{
			try
			{
				if (split[0].equals("Insert"))
				{
					output.println(tree.insert((split[1])));
					return;
					
				}
				else if(split[0].equals("Contains"))
				{
					output.println(tree.contains((split[1])));
					return;
					
				}
			}
			catch (IllegalArgumentException e)
			{
				output.println("Error in insert: IllegalArgumentException raised");
			}
		}
		else if (split[0].equals("PrintTree"))
		{
			output.println(tree.toString());
			return;
		}
		else
		{
			output.println("Error in line: " + input);
		}
		
	}

	/*
	 * interprets the input from file and prints in output
	 */
	private static void interpret(PrintStream output, String input, RedBlackTree tree) 
	{
		String[] split = input.split(":");
		
		if (split.length >= 2)
		{
			try
			{
				if (split[0].equals("Insert") && isnum(split[1]))
				{
					output.println(tree.insert(Integer.parseInt(split[1])));
					return;
				}
				else if(split[0].equals("Contains") && isnum(split[1]))
				{
					output.println(tree.contains(Integer.parseInt(split[1])));
					return;
				}
			}
			catch (IllegalArgumentException e)
			{
				output.println("Error in insert: IllegalArgumentException raised");
			}
		}
		if (split[0].equals("PrintTree"))
		{
			output.println(tree.toString());
			return;
		}
		else
		{
			output.println("Error in line: " + input);
		}
	}
	
	/*
	 * determines if an input is a integer
	 */
	private static boolean isnum(String input)
	{
		for (int counter = 0; counter < input.length(); counter++)
		{
			if (!Character.isDigit(input.charAt(counter)))
			{
				return false;
			}
		}
		return true;
	}


}
