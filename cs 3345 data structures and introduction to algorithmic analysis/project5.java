import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class project5 {
	
	public static void main(String args[]) throws FileNotFoundException
	{
		int size = get_num(0, Integer.MAX_VALUE, "enter the size of the array");
		int[] list = new int[size];
		init_list(list, size);
		int[] list1 = list.clone();
		int[] list2 = list.clone();
		int[] list3 = list.clone();
		int[] list4 = list.clone();
		Duration first = Duration.ofNanos(0);
		Duration random = Duration.ofNanos(0);
		Duration median = Duration.ofNanos(0);
		Duration book = Duration.ofNanos(0);
		
		String sortedfile = "sorted.txt";
		String unsortedfile = "unsorted.txt";
		File sorted = new File(sortedfile);
		File unsorted = new File(unsortedfile);
		PrintStream sort = new PrintStream(sorted);
		PrintStream unsort = new PrintStream(unsorted);
		
		unsort.println("the original array is");
		
		for (int counter = 0; counter < list.length; counter++)
		{
			unsort.println(list[counter]);
		}
		
		if (list.length > 1)
		{
			first = quicksort_first(list1, 0, list.length - 1);
			random = quicksort_random(list2, 0, list.length - 1);
			median = quicksort_median(list3, 0, list.length - 1);
			book = quicksort_book(list4, 0, list.length - 1);

		}
		else
		{
			sort.println("array is sorted");
		}
		
		sort.println("--------");
		sort.println("sorted using first element as pivot. Time taken: " + first);
		for (int counter = 0; counter < list.length; counter++)
		{
			sort.println(list1[counter]);
		}
		
		sort.println("--------");
		sort.println("sorted using random element as pivot. Time taken: " + random);
		for (int counter = 0; counter < list.length; counter++)
		{
			sort.println(list2[counter]);
		}
		
		sort.println("--------");
		sort.println("sorted using median element as pivot. Time taken: " + median);
		for (int counter = 0; counter < list.length; counter++)
		{
			sort.println(list3[counter]);
		}
		
		sort.println("--------");
		sort.println("sorted using book method as pivot. Time taken: " + book);
		for (int counter = 0; counter < list.length; counter++)
		{
			sort.println(list4[counter]);
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
					//System.out.println("invalid input, please try again");
					System.out.println("This option is not acceptable");
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

	
	private static void init_list(int[] list, int size) {
		Random random = new Random();

        for (int i = 0; i < size; ++i) {
        	list[i] = random.nextInt();
        }
	}
	
	public static Duration quicksort_first(int[] list, int left, int right)
	{
		long startTime = System.nanoTime();
		if(left >= right) 
		{
			long finishTime = System.nanoTime();
	        Duration elapsedTime = Duration.ofNanos(finishTime - startTime);
	        return elapsedTime;
		}
		
		int pivot = left;
		int pivot1 = sort(list, left, right, pivot);
		
		quicksort_first(list, left, pivot1 - 1);
		quicksort_first(list, pivot1 + 1, right);
		
		long finishTime = System.nanoTime();
        Duration elapsedTime = Duration.ofNanos(finishTime - startTime);

        return elapsedTime;
	}
	
	public static Duration quicksort_random(int[] list, int left, int right)
	{
		long startTime = System.nanoTime();
		if(left >= right) 
		{
			long finishTime = System.nanoTime();
	        Duration elapsedTime = Duration.ofNanos(finishTime - startTime);
	        return elapsedTime;
		}
		
		Random random = new Random();
		int range = right - left + 1;
		int rand = random.nextInt(range) + left;
		
		int pivot = rand;
		int pivot1 = sort(list, left, right, pivot);
		
		quicksort_random(list, left, pivot1 - 1);
		quicksort_random(list, pivot1 + 1, right);
		
		long finishTime = System.nanoTime();
        Duration elapsedTime = Duration.ofNanos(finishTime - startTime);

        return elapsedTime;
	}
	
	public static Duration quicksort_median(int[] list, int left, int right)
	{
		long startTime = System.nanoTime();
		if(left >= right) 
		{
			long finishTime = System.nanoTime();
	        Duration elapsedTime = Duration.ofNanos(finishTime - startTime);
	        return elapsedTime;
		}
		
		int pivot1 = sort_rand(list, left, right);
		
		quicksort_median(list, left, pivot1 - 1);
		quicksort_median(list, pivot1 + 1, right);
		
		long finishTime = System.nanoTime();
        Duration elapsedTime = Duration.ofNanos(finishTime - startTime);

        return elapsedTime;
	}
	
	public static Duration quicksort_book(int[] list, int left, int right)
	{
		long startTime = System.nanoTime();
		if(left >= right) 
		{
			long finishTime = System.nanoTime();
	        Duration elapsedTime = Duration.ofNanos(finishTime - startTime);
	        return elapsedTime;
		}
		
		int pivot1 = sort_book(list, left, right);
		
		quicksort_book(list, left, pivot1 - 1);
		quicksort_book(list, pivot1 + 1, right);
		
		long finishTime = System.nanoTime();
        Duration elapsedTime = Duration.ofNanos(finishTime - startTime);

        return elapsedTime;
	}
	
	public static int sort(int[] list, int left, int right, int pivot)
	{
		int left_side = left;
		int flip = list[pivot];
		swap(list, pivot, right); // WOW
		for(int counter = left; counter <= right - 1; counter++) 
		{
            if(list[counter] < flip) 
            {
                swap(list, counter, left_side);
                left_side++;
            }
        }
		swap(list, left_side, right);
		return left_side;
	}
	
	public static int sort_rand(int[] list, int left, int right)
	{
		
		Random random = new Random();
		int range = right - left + 1;
		int rand1 = random.nextInt(range) + left;
		int rand2 = random.nextInt(range) + left;
		int rand3 = random.nextInt(range) + left;
		
		int left_side = left;
		int flip = (list[rand1] + list[rand2] + list[rand3]) / 3;
		for(int counter = left; counter <= right - 1; counter++) 
		{
            if(list[counter] < flip) 
            {
                swap(list, counter, left_side);
                left_side++;
            }
        }
		return left_side;
	}
	
	public static int sort_book(int[] list, int left, int right)
	{
		int left_side = left;
		int flip = (list[left] + list[right] + list[(left + right) / 2]) / 3;
		for(int counter = left; counter <= right - 1; counter++) 
		{
            if(list[counter] < flip) 
            {
                swap(list, counter, left_side);
                left_side++;
            }
        }
		return left_side;
	}
	
	
	public static void swap(int[] list, int x, int y)
	{
		int holder = list[x];
		list[x] = list[y];
		list[y] = holder;
	}

}
