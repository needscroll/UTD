
public class StateStartPlus extends State{
	
	//this is s0, it processes only 1-9, alternatively, there can be a state to process 0 = 0
	//this state counts starting on a plus
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
