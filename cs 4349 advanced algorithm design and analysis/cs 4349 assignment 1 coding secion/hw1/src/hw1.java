
public class hw1 {
	
	public static void main(String[] args)
	{
		int[] arr_100_a = get_random_array(100);
		int[] arr_100_b = get_inorder_array(100);
		int[] arr_100_c = get_reverseorder_array(100);
		  
		int[] arr_1000_a = get_random_array(1000);
		int[] arr_1000_b = get_inorder_array(1000);
		int[] arr_1000_c = get_reverseorder_array(1000);
		
		int[] arr_10000_a = get_random_array(10000);
		int[] arr_10000_b = get_inorder_array(10000);
		int[] arr_10000_c = get_reverseorder_array(10000);
		
		int[] arr_100000_a = get_random_array(100000);
		int[] arr_100000_b = get_inorder_array(100000);
		int[] arr_100000_c = get_reverseorder_array(100000);
        
        double old = System.currentTimeMillis();
        double current = System.currentTimeMillis();
        
        //insertion
        
        old = System.currentTimeMillis();
        insertion_sort(arr_100_a);
        current = System.currentTimeMillis();
        System.out.println("size: 100, insertion, sorted random: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        insertion_sort(arr_100_b);
        current = System.currentTimeMillis();
        System.out.println("size: 100, insertion, sorted ascending: " + (current - old) / 1000);

        old = System.currentTimeMillis();
        insertion_sort(arr_100_c);
        current = System.currentTimeMillis();
        System.out.println("size: 100, insertion, sorted decending: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        insertion_sort(arr_1000_a);
        current = System.currentTimeMillis();
        System.out.println("size: 1000, insertion, sorted random: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        insertion_sort(arr_1000_b);
        current = System.currentTimeMillis();
        System.out.println("size: 1000, insertion, sorted ascending: " + (current - old) / 1000);

        old = System.currentTimeMillis();
        insertion_sort(arr_1000_c);
        current = System.currentTimeMillis();
        System.out.println("size: 1000, insertion, sorted decending: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        insertion_sort(arr_10000_a);
        current = System.currentTimeMillis();
        System.out.println("size: 10000, insertion, sorted random: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        insertion_sort(arr_10000_b);
        current = System.currentTimeMillis();
        System.out.println("size: 10000, insertion, sorted ascending: " + (current - old) / 1000);

        old = System.currentTimeMillis();
        insertion_sort(arr_10000_c);
        current = System.currentTimeMillis();
        System.out.println("size: 10000, insertion, sorted decending: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        insertion_sort(arr_100000_a);
        current = System.currentTimeMillis();
        System.out.println("size: 100000, insertion, sorted random: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        insertion_sort(arr_100000_b);
        current = System.currentTimeMillis();
        System.out.println("size: 100000, insertion, sorted ascending: " + (current - old) / 1000);

        old = System.currentTimeMillis();
        insertion_sort(arr_100000_c);
        current = System.currentTimeMillis();
        System.out.println("size: 100000, insertion, sorted decending: " + (current - old) / 1000);
        
        //merge
        
		arr_100_a = get_random_array(100);
		arr_100_b = get_inorder_array(100);
		arr_100_c = get_reverseorder_array(100);
		  
		arr_1000_a = get_random_array(1000);
		arr_1000_b = get_inorder_array(1000);
		arr_1000_c = get_reverseorder_array(1000);
		
		arr_10000_a = get_random_array(10000);
		arr_10000_b = get_inorder_array(10000);
		arr_10000_c = get_reverseorder_array(10000);
		
		arr_100000_a = get_random_array(100000);
		arr_100000_b = get_inorder_array(100000);
		arr_100000_c = get_reverseorder_array(100000);
		
		old = System.currentTimeMillis();
        merge_sort(arr_100_a);
        current = System.currentTimeMillis();
        System.out.println("size: 100, merge, sorted random: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        merge_sort(arr_100_b);
        current = System.currentTimeMillis();
        System.out.println("size: 100, merge, sorted ascending: " + (current - old) / 1000);

        old = System.currentTimeMillis();
        merge_sort(arr_100_c);
        current = System.currentTimeMillis();
        System.out.println("size: 100, merge, sorted decending: " + (current - old) / 1000);
        
		old = System.currentTimeMillis();
        merge_sort(arr_1000_a);
        current = System.currentTimeMillis();
        System.out.println("size: 1000, merge, sorted random: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        merge_sort(arr_1000_b);
        current = System.currentTimeMillis();
        System.out.println("size: 1000, merge, sorted ascending: " + (current - old) / 1000);

        old = System.currentTimeMillis();
        merge_sort(arr_1000_c);
        current = System.currentTimeMillis();
        System.out.println("size: 1000, merge, sorted decending: " + (current - old) / 1000);
        
		old = System.currentTimeMillis();
        merge_sort(arr_10000_a);
        current = System.currentTimeMillis();
        System.out.println("size: 10000, merge, sorted random: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        merge_sort(arr_10000_b);
        current = System.currentTimeMillis();
        System.out.println("size: 10000, merge, sorted ascending: " + (current - old) / 1000);

        old = System.currentTimeMillis();
        merge_sort(arr_10000_c);
        current = System.currentTimeMillis();
        System.out.println("size: 10000, merge, sorted decending: " + (current - old) / 1000);
        
		old = System.currentTimeMillis();
        merge_sort(arr_100000_a);
        current = System.currentTimeMillis();
        System.out.println("size: 100000, merge, sorted random: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
        merge_sort(arr_100000_b);
        current = System.currentTimeMillis();
        System.out.println("size: 100000, merge, sorted ascending: " + (current - old) / 1000);

        old = System.currentTimeMillis();
        merge_sort(arr_100000_c);
        current = System.currentTimeMillis();
        System.out.println("size: 100000, merge, sorted decending: " + (current - old) / 1000);

	}
	
	public static int[] get_random_array(int size)
	{
		int[] rand_arr = new int[size];
		
		for (int i = 0; i < rand_arr.length; i++)
		{
			rand_arr[i] = (int) (Math.random() * 100000);
		}
		
		return rand_arr;
	}
	
	public static int[] get_inorder_array(int size)
	{
		int[] arr = new int[size];
		
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = i;
		}
		
		return arr;
	}
	
	public static int[] get_reverseorder_array(int size)
	{
		int[] arr = new int[size];
		
		for (int i = size; i > arr.length; i++)
		{
			arr[i] = i;
		}
		
		return arr;
	}
	
	public static int[] insertion_sort(int[] arr)
	{
		if (arr.length > 0)
		{
			for (int i = 1; i < arr.length; i++)
			{

				for (int j = i; j > 0; j--)
				{
					if (arr[j] < arr[j - 1])
					{
						int hold = arr[j - 1];
						arr[j - 1] = arr[j];
						arr[j] = hold;
					}
				}
			}
		}
		return arr;
	}
	
	public static int[] merge_sort(int[] arr)
	{
		if (arr.length < 2)
		{
			return arr;
		}
		int half = arr.length / 2;
		int[] array1 = new int[half];
		int[] array2 = new int[arr.length - half];
		
		for (int i = 0; i < half; i++)
		{
			array1[i] = arr[i];
		}
		
		for (int i = 0; i < arr.length - half; i++)
		{
			array2[i] = arr[i + half];
		}
		
		return combine(merge_sort(array1), merge_sort(array2));
	}
	
	public static int[] combine(int[] arr1, int[] arr2)
	{
		int[] new_arr = new int[arr1.length + arr2.length];
		
		for (int i = 0, j = 0; i < arr1.length || j < arr2.length;)
		{
			if (i < arr1.length && j < arr2.length)
			{
				if (arr1[i] < arr2[j])
				{
					new_arr[i + j] = arr1[i];
					i++;
				}
				else if (arr2[j] <= arr1[i])
				{
					new_arr[i + j] = arr2[j];
					j++;
				}
			}
			else if (i >= arr1.length)
			{
				new_arr[i + j] = arr2[j];
				j++;
			}
			else
			{
				new_arr[i + j] = arr1[i];
				i++;
			}
		}
		
		return new_arr;
	}
	
	public static void print_arr(int[] arr)
	{
		for (int i = 0; i < arr.length; i++)
		{
			System.out.println(arr[i]);
		}
		System.out.println("");
	}

}
