import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Memory {
	
	static int[] data = new int[2000];
	//static Object pipeline;
	static boolean running = true;
	
	public static int get_data(int address)
	{
		if (address >= 0 && address < data.length)
		{
			return data[address];
		}

		System.out.println("invalid memory address, overflow on address" + address);
		return Integer.MIN_VALUE;
	}
	
	public static void set_data(int address, int value)
	{
		if (address >= 0 && address < data.length)
		{
			data[address] = value;
		}
		else
		{
			System.out.println("invalid memory address, overflow on address " + value);
		}
	}
	
	public static void init_memory(String[] program)
	{
		for (int i = 0, j = 0; i < program.length; i++, j++)
		{
			String current_line = program[i];
			try
			{
				set_data(j, Integer.parseInt(current_line));
			}
			catch (NumberFormatException nfe)
			{
				System.out.println("nfe on: " + j + " processing string: " + "<" + program[i] + ">");
				//System.out.println(current_line.length() == 0);

				if (current_line.charAt(0) == '.');
				{
					//increments i to the number following the decimal
					String[] parse = current_line.split("\\.");
					j = Integer.parseInt(parse[parse.length - 1]) - 1;
				}
			}
		}
	}
	
	public static String[] clean_input(String[] input)
	{
		List<String> cleaned_input = new ArrayList<String>();
		
		for (String string: input)
		{
			//List<Character> charlist = new ArrayList<Character>();
			String num = "";
			for (int i = 0; i < string.length(); i++)
			{
				if (Character.isDigit(string.charAt(i)))
				{
					num += string.charAt(i);
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
