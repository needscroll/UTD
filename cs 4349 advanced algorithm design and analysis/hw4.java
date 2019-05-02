
public class hw4 {
	
	public static void main(String[] args)
	{
		int[] test = new int[100];
		for (int i = 0; i < test.length; i++)
		{
			test[i] = (int) (Math.random() * 10000);
			System.out.println(test[i]);
		}
		
		quicksort(test, 0, test.length - 1);
		System.out.println("---------------");
		for (int i = 0; i < test.length; i++)
		{
			System.out.println(test[i]);
		}
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
