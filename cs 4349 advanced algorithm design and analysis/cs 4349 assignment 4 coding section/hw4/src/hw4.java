
public class hw4 {
	
	public static void main(String[] args)
	{
		int[] arr1 = new int[100];
		int[] arr2 = new int[1000];
		int[] arr3 = new int[10000];
		int[] arr4 = new int[100000];
		int[] arr5 = new int[1000000];
		int[] best_case = new int[10000000];
		int[] worst_case = new int[10000000];
        double old = System.currentTimeMillis();
        double current = System.currentTimeMillis();
        
        for(int i = 0; i < best_case.length; i++)
        {
        	best_case[i] = i;
        }
        
        for(int i = 0; i < best_case.length; i++)
        {
        	worst_case[i] = worst_case.length - i;
        }
		
		for (int i = 0; i < arr1.length; i++)
		{
			arr1[i] = (int) (Math.random() * 10000);
		}
		
		for (int i = 0; i < arr2.length; i++)
		{
			arr2[i] = (int) (Math.random() * 10000);
		}
		
		for (int i = 0; i < arr3.length; i++)
		{
			arr3[i] = (int) (Math.random() * 10000);
		}
		
		for (int i = 0; i < arr4.length; i++)
		{
			arr4[i] = (int) (Math.random() * 100000);
		}
		
		for (int i = 0; i < arr5.length; i++)
		{
			arr5[i] = (int) (Math.random() * 100000);
		}
		
		old = System.currentTimeMillis();
		quicksort(arr1, 0, arr1.length - 1);
        current = System.currentTimeMillis();
        System.out.println("size: 100: " + (current - old) / 1000);
        
		old = System.currentTimeMillis();
		quicksort(arr2, 0, arr2.length - 1);
        current = System.currentTimeMillis();
        System.out.println("size: 1000: " + (current - old) / 1000);
		
		old = System.currentTimeMillis();
		quicksort(arr3, 0, arr3.length - 1);
        current = System.currentTimeMillis();
        System.out.println("size: 10000: " + (current - old) / 1000);
        
		old = System.currentTimeMillis();
		quicksort(arr4, 0, arr4.length - 1);
        current = System.currentTimeMillis();
        System.out.println("size: 100000: " + (current - old) / 1000);
        
		old = System.currentTimeMillis();
		quicksort(arr5, 0, arr5.length - 1);
        current = System.currentTimeMillis();
        System.out.println("size: 1000000: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
		quicksort(best_case, 0, best_case.length - 1);
        current = System.currentTimeMillis();
        System.out.println("best case 1000000 numbers: " + (current - old) / 1000);
        
        old = System.currentTimeMillis();
		quicksort(worst_case, 0, worst_case.length - 1);
        current = System.currentTimeMillis();
        System.out.println("worst case 1000000 numbers: " + (current - old) / 1000);
	}

	public static void quicksort(int[] list, int left, int right)
	{
		if(left < right) 
		{
			int pivot1 = sort(list, left, right);
			
			quicksort(list, left, pivot1 - 1);
			quicksort(list, pivot1 + 1, right);
		}
	}
	
	public static int sort(int[] list, int left, int right)
	{
		int random = (int) (Math.random() * (right - left)) + left;
		swap(list, random, right);
		
		int pivot = list[right];
		int i = left - 1;
		for (int j = left; j < right; j++)
		{
			if (list[j] <= pivot)
			{
				i++;
				swap(list, i, j);
			}
		}
		
		swap(list, i+1, right);
		return i+1;
	}
	
	public static void swap(int[] list, int x, int y)
	{
		int holder = list[x];
		list[x] = list[y];
		list[y] = holder;
	}

}
