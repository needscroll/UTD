
public class StateOnStartingZero extends State{
	
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
		
		return output;
	}

	@Override
	protected boolean inputValid(char c) {
		return isOperator(c);
	}

}
