/*james wei
 * cs 3345.003
 * Dr. Chida
 * Spring 2018
 * Project 3 Binary Search Tree
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class project3 {
	
	public static void main(String[] args)
	{
		ArrayList<String> inputs = new ArrayList<String>();
		LazyBinarySearchTree tree = new LazyBinarySearchTree();
		
		if (args.length > 1)
		{
			get_inputs(inputs, args[0]);
			print_output(inputs, args[1], tree);
		}
		else
		{
			System.out.println("please provide the input and output files");
		}
	}

	/*
	 * Description: gets inputs from the input file
	 * Input: String filename, ArrayList inputs
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
	 * Description: prints the interpretation of the input file to the output file
	 *inputs: ArrayList input, String filename, LazyBinarySearchTree tree
	 */
	private static void print_output(ArrayList<String> inputs, String filename, LazyBinarySearchTree tree) 
	{
		File outputfile = new File(filename);
		
		try {
			PrintStream output = new PrintStream(outputfile);
			for (int counter = 0; counter < inputs.size(); counter++)
			{
				interpret(output, inputs.get(counter), tree);
			}

		} catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		
	}

	/*
	 * description: returns the input of the input file commands
	 * input: PrintStream output, String input, LazyBinarySearchTree tree
	 */
	private static void interpret(PrintStream output, String input, LazyBinarySearchTree tree) 
	{
		String[] split = input.split(":");
		if (split.length == 2)
		{
			try
			{
				if (split[0].equals("Insert") && isnum(split[1]))
				{
					output.println(tree.insert(Integer.parseInt(split[1])));
				}
				else if (split[0].equals("Delete") && isnum(split[1]))
				{
					output.println(tree.delete(Integer.parseInt(split[1])));
				}
				else if(split[0].equals("Contains") && isnum(split[1]))
				{
					output.println(tree.contains(Integer.parseInt(split[1])));
				}
			}
			catch (IllegalArgumentException e)
			{
				output.println("Error in insert: IllegalArgumentException raised");
			}
			
		}
		else if (input.equals("FindMin"))
		{
			output.println(tree.findMin());
		}
		else if (input.endsWith("FindMax"))
		{
			output.println(tree.findMax());
		}
		else if (input.equals("Size"))
		{
			output.println(tree.size());
		}
		else if (input.equals("PrintTree"))
		{
			output.println(tree.toString());
		}
		else if (input.equals("Height"))
		{
			output.println(tree.height());
		}
		else
		{
			output.println("Error in line: " + input);
		}
			
			
		
	}

	/*
	 * description: determines if a string is a number
	 * input: String input
	 * output: boolean
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
