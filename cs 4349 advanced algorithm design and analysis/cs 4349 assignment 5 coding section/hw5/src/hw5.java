import java.util.ArrayList;
import java.util.Arrays;

public class hw5 {
	
	public static void main(String[] args)
	{
		double old = System.currentTimeMillis();
        double current = System.currentTimeMillis();
		
		int[] increasing = new int[1000000];
		int[] decreasing = new int[1000000];
		int[] random = new int[1000000];
		
		for (int i = 0; i < increasing.length; i++)
		{
			increasing[i] = i;
			decreasing[i] = increasing.length - i - 1;
			random[i] = (int) (Math.random() * increasing.length);
		}
		
		int[] increasing1 = increasing.clone();
		int[] increasing2 = increasing.clone();
		int[] decreasing1 = decreasing.clone();
		int[] decreasing2 = decreasing.clone();
		int[] random1 = random.clone();
		int[] random2 = random.clone();
		
		//actual sorting takes place here
		//counting sort
		old = System.currentTimeMillis();
		int[] counting_increasing = counting_sort(increasing);
		current = System.currentTimeMillis();
        System.out.println("counting: increasing: " + (current - old) / 1000);


        old = System.currentTimeMillis();
		int[] counting_decreasing = counting_sort(decreasing);
		current = System.currentTimeMillis();
        System.out.println("counting: decreasing: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
		int[] counting_random = counting_sort(random);
		current = System.currentTimeMillis();
        System.out.println("counting: random: " + (current - old) / 1000);
		
        //radix sort
        old = System.currentTimeMillis();
		int[] radix_increasing = radix_sort(increasing1);
		current = System.currentTimeMillis();
        System.out.println("radix: increasing: " + (current - old) / 1000);

        old = System.currentTimeMillis();
		int[] radix_decreasing = radix_sort(decreasing1);
		current = System.currentTimeMillis();
        System.out.println("radix: decreasing: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
		int[] radix_random = radix_sort(random1);
		current = System.currentTimeMillis();
        System.out.println("radix: random: " + (current - old) / 1000);
        
		//bucket sort
        old = System.currentTimeMillis();
		int[] bucket_increasing = bucket_sort(increasing2);
		current = System.currentTimeMillis();
        System.out.println("bucket: increasing: " + (current - old) / 1000);

        old = System.currentTimeMillis();
		int[] bucket_decreasing = bucket_sort(decreasing2);
		current = System.currentTimeMillis();
        System.out.println("bucket: decreasing: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
		int[] bucket_random = bucket_sort(random2);
		current = System.currentTimeMillis();
        System.out.println("bucket: random: " + (current - old) / 1000);
		
        /*
		for (int i = 0; i < 1000; i++)
		{
			System.out.println(counting_random[i]);
		}*/
	}
	
	public static int[] counting_sort(int[] arr)
	{
		int[] answer = new int[arr.length];
		int[] tracker = new int[arr.length];
		
		for (int i = 0; i < arr.length; i++)
		{
			tracker[arr[i]]++;
		}
		
		int j = 0;
		for (int i = 0; i < tracker.length; i++)
		{
			if (tracker[i] != 0)
			{
				for (int k = 0; k < tracker[i]; j++, k++)
				{
					answer[j] = i; 
				}
			}
		}
		return answer;
	}
	
	public static int[] radix_sort(int[] arr)
	{
		int[] answer = new int[arr.length];
		int[] holder = arr;
		ArrayList<Integer> list_holder = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
		
		for (int j = 0; j < Math.log10(arr.length); j++)
		{
			for (int i = 0; i < 10; i++)
			{
				buckets.add(new ArrayList<Integer>());
			}
			
			for (int i = 0; i < holder.length; i++)
			{
				buckets.get((holder[i] / (int)(Math.pow(10, j))) % 10).add(holder[i]);
			}
			
			for (int i = 0; i < buckets.size(); i++)
			{
				list_holder.addAll(buckets.get(i));
			}
			holder = toarray(list_holder);
			
			//clears buckets and list
			//System.out.println(list_holder); // print for debug
			list_holder.clear();
			for (int i = 0; i < buckets.size(); i++)
			{
				buckets.get(i).clear();
			}
		}
		
		return answer;
	}
	
	public static int[] bucket_sort(int[] arr)
	{
		int[] answer = new int[arr.length];
		Integer bucket_arrays[][] = new Integer[10][arr.length/10];
		ArrayList<ArrayList<Integer>> buckets = new ArrayList<ArrayList<Integer>>();
		
		for (int i = 0; i < 10; i++)
		{
			buckets.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < arr.length; i++)
		{
			buckets.get(get_mod(arr[i], (int) (Math.log10(arr[i]) - 1))).add(arr[i]);
		}
		
		for (int i = 0; i < buckets.size(); i++)
		{
			bucket_arrays[i] = buckets.get(i).toArray(new Integer[buckets.get(i).size()]);
			Arrays.sort(bucket_arrays[i]);
		}
		
		int k = 0;
		for (int i = 0; i < bucket_arrays.length; i++)
		{
			for (int j = 0; j < bucket_arrays[i].length; j++, k++)
			{
				answer[k] = bucket_arrays[i][j];
			}
		}
		
		return answer;
	}
	
	public static int[] toarray(ArrayList<Integer> list)
	{
		int[] answer = new int[list.size()];
		Integer[] integer = list.toArray(new Integer[list.size()]);

		for (int i = 0; i < integer.length; i++)
		{
			answer[i] = integer[i];
		}
		return answer;
	}
	
	public static int get_mod(int num, int place) // place is from left
	{	
		int newint = (int) (num / Math.pow(10, place));
		return newint % 10;
	}
}
