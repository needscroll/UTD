
public abstract class State {
	
	enum DATATYPE
	{
		PLUS, MINUS, EQUAL, ZERO, DIGIT, ERROR;
	}
	
	protected StateOutput output;
	protected final char[] opChars = {'=','+','-'};
	protected final char equalsign = '=';
	protected final char minussign = '-';
	protected final char plussign = '+';

	public abstract State nextState(char c);
	public abstract StateOutput getOutput(Calculator calculator, char c);
	protected abstract boolean inputValid(char c);
	
	protected boolean isOperator(char c)
	{
		for (char character: opChars)
		{
			if (c == character)
			{
				return true;
			}
		}
		return false;
	}
	
	protected DATATYPE getType(char c)
	{
		if (c == plussign)
			return DATATYPE.PLUS;
		if (c == minussign)
			return DATATYPE.MINUS;
		if (c == equalsign)
			return DATATYPE.EQUAL;
		
		if (Character.isDigit(c))
		{
			if (c == '0')
				return DATATYPE.ZERO;
			else
				return DATATYPE.DIGIT;
		}
		
		return DATATYPE.ERROR;
	}


}
