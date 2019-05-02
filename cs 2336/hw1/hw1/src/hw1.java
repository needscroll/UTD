/* Name: James Wei
 * Class: CS 2336.503
 * Professor: Dr. Vidroha Debroy
 * Assignment: HW1
 * Date: 5 September 2017
 * A program that takes swimming information and does calculations
 */

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.DoubleStream;

public class hw1 
{

	public static void main(String[] args)
	{
		int[] year = {1974, 1975, 1978, 1979, 2013};
		int[] distance = new int[year.length];
		int[] time = new int[year.length];
		double[] speed = new double[year.length];
		Arrays.fill(distance, -1);
		Arrays.fill(time, -1);
		Arrays.fill(speed,  -1);
		
		get_info(year, distance, time);
		calc_speed(distance, time, speed);
		print_info(year, distance, time, speed);
	}
	
	//gets swimming info
	private static void get_info(int[] year, int[] distance, int[] time)
	{
		Scanner input = new Scanner(System.in);
		
		for (int counter = 0; counter < year.length; counter++)
		{
			if (distance[counter] < 0)
			{
				System.out.println("enter the distance swam for " + year[counter]);
				distance[counter] = clean(input.nextLine());
			}
			if (time[counter] < 0)
			{
				System.out.println("enter the time it took to swim for " + year[counter]);
				time[counter] = clean(input.nextLine());
			}
		}
		
	}
	
	//cleans swimming input info
	private static int clean(String in)
	{
		int out = -1;
		boolean pass = true;
		
		for (int counter = 0; counter < in.length(); counter++)
		{
			if (!Character.isDigit(in.charAt(counter)) || Character.getNumericValue(in.charAt(counter)) < 0)
			{
				pass = false;
			}
		}
		
		if (pass)
		{
			out = Integer.parseInt(in);
		}
		else // requests new input if bad input
		{
			System.out.println("Invalid input. Please enter an integer and an integer only with no other characters");
			Scanner input_new = new Scanner(System.in);
			out = clean(input_new.nextLine());
		}
		
		return out;
	}
	
	//calcs speed
	private static void calc_speed(int[] distance, int[] time, double[] speed)
	{
		for(int counter = 0; counter < distance.length; counter++)
		{
			speed[counter] = (double)distance[counter] / time[counter];
		}
	}
	
	//prints info
	private static void print_info(int[] year, int[] distance, int[] time, double[] speed)
	{
		double average = DoubleStream.of(speed).sum() / 5;
		
		System.out.println("Information is printed in the form: (year) (distance) (time) (speed)");
		for (int counter = 0; counter < year.length; counter++)
		{
			System.out.format("%-14d", year[counter]);
			System.out.format("%-14d", distance[counter]);
			System.out.format("%-14d", time[counter]);
			System.out.format("%-14.7f", speed[counter]);
			System.out.println();
		}
		System.out.println("the average speed is: " + average);

	}

}
