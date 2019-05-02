
public class StateStartMinus extends State{
	
	//this is s2, acts as s0 but counts starting operator as minus
	//this state starts counting on a minus
	char startingZero = '0';


	@Override
	public State nextState(char c) {
		State nextstate = null;
		
		switch(getType(c))
		{
			case ZERO:
				nextstate = new StateOnStartingZero();
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
		
		if (!inputValid(c))
		{
			output.write("invalid input on <" + c + ">");
			output.setError(true);
		}
		else
		{
			calculator.appendNewNum(c);
		}
		
		return output;
	}

	@Override
	protected boolean inputValid(char c) {
		if (Character.isDigit(c))
		{
			return true;
		}
		return false;
	}





}
