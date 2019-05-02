import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class project5 {
	
	public static void main(String args[])
	{
		int size = get_num(0, Integer.MAX_VALUE, "enter the size of the array");
		int[] list = new int[size];
		init_list(list, size);
		int[] arr_ori = list.clone(); 
		
		int[] we = {6, 3, 1, -4, -7};
		
		if (list.length > 1)
		{
			quicksort_median(list, 0, list.length - 1);
		}
		else
		{
			System.out.println("array is sorted");
		}
		
		for (int counter = 0; counter < list.length; counter++)
		{
			System.out.println(list[counter]);
		}
		
		quicksort_first(we, 0, we.length - 1);
		for (int i = 0; i < we.length; i++)
		{
			//System.out.println(we[i]);
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
	
	public static void quicksort_first(int[] list, int left, int right)
	{
		if(left >= right) 
		{
			return;
		}
		int pivot = left;
		int pivot1 = sort(list, left, right, pivot);
		
		quicksort_first(list, left, pivot1 - 1);
		quicksort_first(list, pivot1 + 1, right);
	}
	
	public static void quicksort_random(int[] list, int left, int right)
	{
		if(left >= right) 
		{
			return;
		}
		
		Random random = new Random();
		int range = right - left + 1;
		int rand = random.nextInt(range) + left;
		
		int pivot = rand;
		int pivot1 = sort(list, left, right, pivot);
		
		quicksort_random(list, left, pivot1 - 1);
		quicksort_random(list, pivot1 + 1, right);
	}
	
	public static void quicksort_median(int[] list, int left, int right)
	{
		if(left >= right) 
		{
			return;
		}
		
		Random random = new Random();
		int range = right - left + 1;
		int rand1 = random.nextInt(range) + left;
		int rand2 = random.nextInt(range) + left;
		int rand3 = random.nextInt(range) + left;

		int pivot1 = sort_rand(list, left, right, rand1, rand2, rand3);
		
		quicksort_median(list, left, pivot1 - 1);
		quicksort_median(list, pivot1 + 1, right);
	}
	
	public static void quicksort_book(int[] list, int left, int right)
	{
		if(left >= right)
		{
			return;
		}
		
		int pivot = (left + right) / 2;
		int pivot1 = sort(list, left, right, pivot);
		
		quicksort_median(list, left, pivot1 - 1);
		quicksort_median(list, pivot1 + 1, right);
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
	
	public static int sort_rand(int[] list, int left, int right, int rand1, int rand2, int rand3)
	{
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
	
	public static void swap(int[] list, int x, int y)
	{
		int holder = list[x];
		list[x] = list[y];
		list[y] = holder;
	}


}
