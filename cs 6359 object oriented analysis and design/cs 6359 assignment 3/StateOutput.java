
public class StateOutput {
	
	private String message;
	private boolean error;
	
	public StateOutput()
	{
		this.message = "default message, everything is working just fine";
		this.error = false;
	}
	
	public void write(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setError(boolean error)
	{
		this.error = error;
	}
	
	public boolean getError()
	{
		return error;
	}

}
