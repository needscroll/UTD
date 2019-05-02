
public class StateMap {
	
	private static StateMap onlyOne;
	private State currentState;
	
	private StateMap()
	{
		currentState = new StateStartPlus();
	}
	
	public synchronized static StateMap getInstance()
	{
		if (onlyOne == null)
		{
			onlyOne = new StateMap();	
		}
		return onlyOne;
	}
	
	public StateOutput process(Calculator calculator, char c)
	{
		StateOutput output = currentState.getOutput(calculator, c);
		currentState = currentState.nextState(c);
		
		return output;
	}
	
	
	

}
