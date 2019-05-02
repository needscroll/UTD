
public class MyTimer {
	
	int count;
	int max_time;
	
	MyTimer()
	{
		this.count = 0;
		this.max_time = 1000;
	}
	
	MyTimer(int time)
	{
		this.count = 0;
		this.max_time = time;
	}
	
	public void set_timer(int time)
	{
		this.max_time = time;
	}
	
	public void increment()
	{
		this.count++;
	}
	
	public boolean running()
	{
		return this.count < this.max_time;
	}
	
	public void reset()
	{
		this.count = 0;
	}

}
