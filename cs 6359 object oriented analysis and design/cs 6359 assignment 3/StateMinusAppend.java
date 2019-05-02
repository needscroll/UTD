
public class StateMinusAppend extends State{
	
	//this is s3
	//this state appends onto the read in number on a minus

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
				nextstate = new StateMinusAppend();
				break;
			case DIGIT:
				nextstate = new StateMinusAppend();
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
				calculator.subTotal();
				break;
			case PLUS:
				calculator.subTotal();
				break;
			case MINUS:
				calculator.subTotal();
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
