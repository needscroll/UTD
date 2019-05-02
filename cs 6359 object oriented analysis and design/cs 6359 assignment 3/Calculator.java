
public class Calculator {
	
	private int total;
	private int newNum;
	private static String equation;
	static StateMap stateMap;
	private char whitespace = ' ';
	
	public Calculator(String equation)
	{
		this.equation = cleanInput(equation);
		stateMap = StateMap.getInstance();
		total = 0;
		newNum = 0;
	}
	
	public void start()
	{
		for (char c: equation.toCharArray())
		{
			StateOutput output = stateMap.process(this, c);
			if (output.getError())
			{
				System.out.println(output.getMessage());
			}
		}
		System.out.println(total);
	}
	
	public void appendNewNum(char c)
	{
		newNum *= 10;
		newNum += Character.getNumericValue(c);
	}
	
	public void sumTotal()
	{
		total += newNum;
		newNum = 0;
	}
	
	
	public void subTotal()
	{
		total -= newNum;
		newNum = 0;
	}
	
	private String cleanInput(String input)
	{
		String result = "";
		
		for (char c: input.toCharArray())
		{
			if (c != whitespace)
			{
				result += c;
			}
		}
		
		return result;
	}

}
