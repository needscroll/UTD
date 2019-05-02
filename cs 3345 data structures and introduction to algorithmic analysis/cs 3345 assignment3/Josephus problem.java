import java.util.ArrayList;

public class hot_potato {
	
	public static void main(String[] args)
	{
		
		int people = 5;
		int passes = 1;
		ArrayList<Integer> persons = new ArrayList<Integer>();
		
		for (int counter = 0; counter < people; counter++)
		{
			persons.add(counter + 1);
		}
		
		for (int counter = 0;persons.size() > 0;)
		{
			counter += passes;
			int holding = counter % persons.size();
			System.out.println("person removed is: " + persons.get(holding));
			persons.remove(holding);
			counter = holding;
		}
	}
	
	

}
