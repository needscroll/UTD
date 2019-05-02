
public class Validator implements Acceptable{
	
	public boolean isNonEmptyString(String input)
	{
		boolean pass = false;
		
		for (int counter = 0; counter < input.length(); counter++)
		{
			if (input.charAt(counter) != ' ')
			{
				pass = true;
			}
		}
		
		return pass;
	}
	
	public boolean isPositiveInput(double input)
	{
		boolean pass = false;
		if (input >= 0)
		{
			pass = true;
		}
		
		return pass;
	}

}
