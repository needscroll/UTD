
public class StatePlusAppend extends State{
	
	//this is s1
	//this state appends to the read in number on a plus

	@Override
	public State nextState(char c) {
		State nextstate = null;

		switch(getType(c))
		{
			case EQUAL:
				nextstate = new StateFinal();
				break;
			case PLUS:
				nextstate = new StateStartPlus();
				break;
			case MINUS:
				nextstate = new StateStartMinus();
				break;
			case ZERO:
				nextstate = new StatePlusAppend();
				break;
			case DIGIT:
				nextstate = new StatePlusAppend();
				break;
			default:
				nextstate = new StateError();
		}
		
		return nextstate;
	}

	@Override
	public StateOutput getOutput(Calculator calculator, char c) {
		StateOutput output = new StateOutput();
		
		switch(getType(c))
		{
			case EQUAL:
				calculator.sumTotal();
				break;
			case PLUS:
				calculator.sumTotal();
				break;
			case MINUS:
				calculator.sumTotal();
				break;
			case ZERO:
				calculator.appendNewNum(c);
				break;
			case DIGIT:
				calculator.appendNewNum(c);
				break;
			default:
				output.write("invalid input on <" + c + ">");
				output.setError(true);
		}
		
		return output;
	}

	@Override
	protected boolean inputValid(char c) {
		if (Character.isDigit(c))
		{
			return true;
		}
		return isOperator(c);
	}

}
