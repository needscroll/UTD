
public class StateFinal extends State{
	
	//this is the final state

	@Override
	public State nextState(char c) {
		return new StateError();
	}

	@Override
	public StateOutput getOutput(Calculator calculator, char c) {
		StateOutput output = new StateOutput();
		output.write("final state reached");
		output.setError(true);
		return output;
	}

	@Override
	protected boolean inputValid(char c) {
		// TODO Auto-generated method stub
		return false;
	}

}
