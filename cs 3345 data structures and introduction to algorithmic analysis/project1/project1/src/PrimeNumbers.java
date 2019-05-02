/*
james wei
cs3345.003
spring 2018
project 1
description: This program takes in a number from the user and uses the sieve of erotosthenes to
print all primes less than or equal to that number
*/
import java.util.ArrayList;
import java.util.Scanner;

public class PrimeNumbers {
	
	public static void main(String args[])
	{
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		int input = get_num(1, Integer.MAX_VALUE, "Please enter a number greater than or equal to 1");
		
		for (int counter = 1; counter <= input; counter++)
		{
			numbers.add(counter); // makes number table
		}

		for (int counter = 2; counter < Math.sqrt(input); counter++)
		{
			if (numbers.get(counter - 1) > 0)
			{
				sieve(numbers, counter); // sieves through table for multiples of a prime
			}
		}
		System.out.println("all prime numbers less than or equal to the input");
		
		for (int counter = 1; counter < numbers.size(); counter++)
		{
			if (numbers.get(counter) > 0)
			{
				System.out.println(numbers.get(counter)); // print final result
			}
		}
	}
	
	public static int get_num(int min, int max, String msg) // change function return type for different data types
	{
		String input = "";
		boolean gotnumber = false;
		int input_i = -1;
		Scanner get = new Scanner(System.in);

		while (!gotnumber) {
			System.out.println(msg);
			try {
				input = get.nextLine();

				if (wrong_input(input, min, max)) {
					System.out.println("invalid input, please try again");
				} else 
				{
					input_i = Integer.parseInt(input);
					gotnumber = true;					
				}
			} catch (NumberFormatException nfe) {
				System.out.println("invalid data type, please try again");
			} catch (Exception Ex) {
				System.out.println("general exception");
			}
		}
		
		return input_i;
	}
	
	private static boolean wrong_input(String input, int min, int max)
	{
		String[] holder_arr = input.split("\\s");
		// change this to check for different conditions
		return holder_arr.length > 1 || Integer.parseInt(input) > max || Integer.parseInt(input) < min;
	}
	
	//sieves through the number table
	private static void sieve(ArrayList<Integer> numbers, int filter)
	{
		for (int counter = filter * filter; counter <= numbers.size();)
		{
			numbers.set(counter - 1, -1);
			counter += filter;
		}
	}

}
