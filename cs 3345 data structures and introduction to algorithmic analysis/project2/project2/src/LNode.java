
public class LNode<T extends IDedObject> {
	
	private LNode<T> next;
	private T data;
	
	public LNode(T data, LNode<T> next)
	{
		set_data(data);
		set_next(next);
	}
	
	public void set_data(T data)
	{
		this.data = data;
	}
	
	public void set_next(LNode<T> next)
	{
		this.next = next;
	}
	
	public LNode<T> get_next()
	{
		return next;
	}
	
	public T get_data()
	{
		return data;
	}

}
