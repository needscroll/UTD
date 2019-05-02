
public class RedBlackTree<E extends Comparable<E>>{
	
	class Node<E extends Comparable<E>>
	{
		Node(E key)
		{
			element = key;
		}
		
		E element;
		Node leftChild;
		Node rightChild;
		Node parent;
		boolean color;
		
		public int compareTo(E other) {
			return element.compareTo(other);
		}
	}
	
	static boolean RED = false;
	static boolean BLACK = true;
	Node root;
	
	RedBlackTree()
	{
		
	}
	
	/*
	 * insert
	 */
	public boolean insert(E element)
	{
		if (element == null)
		{
			throw new NullPointerException();
		}
		
		if (root == null)
		{
			root = new Node(element);
			root.color = BLACK;
			return true;
		}
		
		Node curr = root;
		while (curr != null)
		{
			if (curr.compareTo(element) < 0)
			{
				if (curr.rightChild == null)
				{
					curr.rightChild = new Node(element);
					curr.rightChild.color = RED; // need rebalance
					balance1(curr.rightChild);
					return true;
				}
				else
				{
					curr = curr.rightChild;
				}
			}
			else if (curr.compareTo(element) > 0)
			{
				if (curr.leftChild == null)
				{
					curr.leftChild = new Node(element);
					curr.leftChild.color = RED; // need rebalance
					balance1(curr.leftChild);
					return true;
				}
				else
				{
					curr = curr.leftChild;
				}
			}
			else
			{
				return false;
			}
		}

		return true;
	}
	
	/*
	 * balances tree
	 */
	private void balance1(Node curr)
	{
		if (curr != null && !curr.equals(root) && curr.parent != null && curr.parent.color == RED)
		{
			Node uncle;
			Node uncle1 = curr.parent.parent.leftChild;
			Node uncle2 = curr.parent.parent.rightChild;
			if (curr.parent.equals(uncle1))
			{
				uncle = uncle2;
			}
			else
			{
				uncle = uncle1;
			}
			
			if (uncle.color == RED)
			{
				curr.parent.color = BLACK;
				uncle.color = BLACK;
				curr.parent.parent.color = RED;
				balance1(curr.parent.parent);
			}
			else if (curr.parent.equals(curr.parent.parent.leftChild))
			{
				if (curr.equals(curr.parent.rightChild))
				{
					rotate_left(curr.parent); // need change to curr.parent?
				}
				curr.parent.color = BLACK;
				curr.parent.parent.color = RED;
				rotate_right(curr.parent.parent);
			}
			else if (curr.parent.equals(curr.parent.parent.rightChild))
			{
				if (curr.equals(curr.parent.leftChild))
				{
					rotate_right(curr.parent); // need change to curr.parent?
				}
				curr.parent.color = BLACK;
				curr.parent.parent.color = RED;
				rotate_left(curr.parent.parent);
			}
		}
		root.color = BLACK;
	}
	
	/*
	 * rotates left
	 */
	private void rotate_left(Node curr)
	{
		Node a = curr.rightChild;
		curr.rightChild = a.leftChild;
		if (a.leftChild != null)
		{
			a.leftChild.parent = curr;
		}
		a.parent = curr.parent;
		if (curr.parent == null)
		{
			root = a;
		}
		else
		{
			if (curr.equals(curr.parent.leftChild))
			{
				curr.parent.leftChild = a;
			}
			else
			{
				curr.parent.rightChild = a;
			}
		}
		a.leftChild = curr;
		curr.parent = a;
	}
	
	/*
	 * rotates right
	 */
	private void rotate_right(Node curr)
	{
		Node a = curr.leftChild;
		curr.leftChild = a.rightChild;
		if (a.rightChild != null)
		{
			a.rightChild.parent = curr;
		}
		a.parent = curr.parent;
		if (curr.parent == null)
		{
			root = a;
		}
		else
		{
			if (curr.equals(curr.parent.rightChild))
			{
				curr.parent.rightChild = a;
			}
			else
			{
				curr.parent.leftChild = a;
			}
		}
		a.rightChild = curr;
		curr.parent = a;
	}
	
	/*
	 * determines if tree is improperly colored
	 */
	public boolean not_colored(Node curr)
	{
		if (curr != null)
		{
			if (root.color == RED)
			{
				return true;
			}
			
			if (curr.color == RED)
			{
				if (curr.parent != null && curr.parent.color == RED)
				{
					return true;
				}
				if (curr.leftChild != null && curr.leftChild.color == RED)
				{
					return true;
				}
				if (curr.rightChild != null && curr.rightChild.color == RED)
				{
					return true;
				}
			}
		}
		else
		{
			return false;
		}
		
		return not_colored(curr.leftChild) || not_colored(curr.rightChild);
	}

	/*
	 * returns if a tree contains an element
	 */
	public boolean contains(Object E)
	{
		
		Node curr = root;
		while (curr != null)
		{
			if (curr.element.compareTo(E) > 0)
			{
				curr = curr.leftChild;
			}
			else if (curr.element.compareTo(E) < 0)
			{
				curr = curr.rightChild;
			}
			else
			{
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * prints preorder tree
	 * 
	 */
	public String toString()
	{
		return toString_rec(root);
	}
	
	/*
	 * description: recursive helper function for toString()
	 * input: TreeNode node
	 * output: String
	 */
	private String toString_rec(Node node)
	{
		if (node == null)
		{
			return "";
		}

		return " " + node.element +  toString_rec(node.leftChild) + toString_rec(node.rightChild);
	}



}
