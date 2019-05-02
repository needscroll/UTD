
public class LinkedList<T extends IDedObject> {
	
	private LNode<T> first = null; // need change
	
	public void makeEmpty()
	{
		first = null;
	}
	
	public boolean insertAtFront(T x) // inserts element at front of list
	{
		boolean has = false;
		boolean done = false;
		LNode current = first;

		if (first == null && !done) // check if empty
		{
			first = new LNode(x, null);
			done = true;
		}
		else
		{
			while (current.get_next() != null && !has)
			{
				if (current.get_data().equals(x))
				{
					has = true;
				}
				current = current.get_next();
			}
			if (current.get_data().equals(x))
			{
				has = true;
			}
			
		}
		
		if (!has && !done)
		{
			LNode new_first = new LNode(x, first);
			first = new_first;
		}
		
		return !has;
	}
	
	public T deleteFromFront() //deletes first element
	{
		T data;
		if (is_empty()) // check if empty
		{
			return null;
		}
		else
		{
			data = first.get_data();
			if (first.get_next() == null)
			{
				data = first.get_data();
				first = null;
			}
			else
			{
				first = first.get_next();
			}
		}

		return data;
	}
	
	public void printAllRecords() // prints all elements
	{
		boolean empty = false;
		boolean done = false;
		
		if (first == null)
		{
			empty = true;
		}
		else
		{
			LNode current = first;
			while (current.get_data() != null && !done)
			{
				Magazine holder = (Magazine) current.get_data();
				holder.printID();
				if (current.get_next() == null)
				{
					done = true;
				}
				else
				{
					current = current.get_next();
				}
			}
		}
		
		if (empty)
		{
			System.out.println("list is empty");
		}
	}
	
	public T findID(int ID) // finds element with certain id and returns that element
	{
		boolean has = false;
		boolean done = false;
		Magazine holder;
		if (!is_empty())
		{
			LNode current = first;
			holder = (Magazine) first.get_data();
			if (current.get_next() == null && holder.get_id() == ID)
			{
				holder = (Magazine) current.get_data();
				return (T) holder;
			}
			while (current.get_next() != null)
			{
				holder = (Magazine) current.get_data();
				if (holder.get_id() == ID)
				{
					return (T) holder;
				}
				current = current.get_next();
			}
			if (current.get_next() == null && current != null)
			{
				holder = (Magazine) current.get_data();
				if (holder.get_id() == ID)
				{
					return (T) holder;
				}
			}
			
		}
		
		return null;
	}

	public T delete(int i) // deletes an item with a certain id
	{
		boolean done = false;
		Magazine holder = (Magazine) first.get_data();
		
		if (is_empty()) // check if empty
		{
			return null;
		}
		if (first.get_next() == null || holder.get_id() == i) // check if only 1 element
		{
			holder = (Magazine) first.get_data();
			if (holder.get_id() != i)
			{
				return null;
			}
			else
			{
				T data = first.get_data();
				if (first.get_next() == null)
				{
					first = null;
				}
				else
				{
					first = first.get_next();
				}
				return data;
			}
		}
		
		LNode current = first;
		while (current.get_next() != null && !done)
		{
			holder = (Magazine) current.get_next().get_data();
			if (holder.get_id() == i && current.get_next().get_next() != null)
			{
				current.set_next(current.get_next().get_next());
				done = true;
			}
			else if(holder.get_id() == i && current.get_next().get_next() == null)
			{
				current.set_next(null);
				done = true;
			}
			else
			{
				current = current.get_next();
			}
			
		}
		
		return null;
	}
	
	private boolean is_empty() // checks if list is empty
	{
		boolean empty = false;
		if (first == null)
		{
			empty = true;
		}
		return empty;
	}
	
	private boolean has(int x) // checks if list has an element
	{
		boolean has = false;
		boolean done = false;
		
		if (!is_empty())
		{
			LNode current = first;
			
			if (current.get_next() == null && current.get_data().equals(x))
			{
				has = true;
				done = true;
			}
			while (current.get_next() != null)
			{
				if (current.get_data().equals(x))
				{
					has = true;
				}
				current = current.get_next();
			}
			
		}
		
		return has;
	}
}
