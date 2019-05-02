
public class StateError extends State{

	@Override
	public State nextState(char c) {
		return new StateError();
	}

	@Override
	public StateOutput getOutput(Calculator calculator, char c) {
		StateOutput output = new StateOutput();
		output.write("error state reached");
		output.setError(true);
		return output;
	}

	@Override
	protected boolean inputValid(char c) {
		// TODO Auto-generated method stub
		return false;
	}

}
