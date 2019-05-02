import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class hw6 {
	
	public static void main(String[] args)
	{
		String input = get_string("please enter a string");
		int[] letter_count = new int[27];
		ArrayList<node> nodes = new ArrayList<node>();
		
		for (int i = 0; i < input.length(); i++)
		{
			char point = input.charAt(i);
			if (point == ' ')
			{
				letter_count[26]++;
			}
			else
			{
				letter_count[input.charAt(i) - 'a']++;
			}
		}
		
		for (int i = 0; i < letter_count.length; i++)
		{
			if (letter_count[i] > 0)
			{
				nodes.add(new node(Character.toString((char)(i + 'a')), letter_count[i]));
			}
		}
		
		for (;nodes.size() > 1;)
		{
			sort_nodes(nodes);
			node new_node = new node(nodes.get(0).title + nodes.get(1).title, nodes.get(0).sum + nodes.get(1).sum);
			new_node.Lnode = nodes.get(0);
			new_node.Rnode = nodes.get(1);
			nodes.remove(0);
			nodes.remove(0);
			nodes.add(new_node);
		}
		
		print_nodes(nodes.get(0), "");
		
		/*
		for (int i = 0; i < nodes.size(); i++)
		{
			System.out.println(nodes.get(i).title);
			System.out.println(nodes.get(i).sum);
		}
		
		
		System.out.println(nodes.size());*/
	}
	
	public static void sort_nodes(ArrayList<node> nodes)
	{
		for (int i = 0; i < nodes.size(); i++)
		{
			for (int j = 0; j < nodes.size() - 1; j++)
			{
				if(nodes.get(j).sum > nodes.get(j+1).sum)
				{
					node holder = nodes.get(j);
					nodes.set(j, nodes.get(j+1));
					nodes.set(j+1, holder);
				}
			}
		}
	}
	
	public static void print_nodes(node root, String code)
	{
		if (root.Lnode != null)
		{
			print_nodes(root.Lnode, code + "0");
		}
		if (root.Rnode != null)
		{
			print_nodes(root.Rnode, code + "1");
		}
		if (root.Lnode == null && root.Rnode == null)
		{
			System.out.println(root.title);
			System.out.println(root.sum);
			System.out.println(code);
			System.out.println("------------");
		}
	}

	public static String get_string(String msg) //gets and returns a string
	{
		System.out.println(msg);
		String input = "";
		Scanner get = new Scanner(System.in);
		
		input = get.nextLine();
		
		while (input.length() == 0)
		{
			System.out.println("Invalid input. Please try again.");
			input = get.nextLine();
		}
		
		return input;
	}

}

class node {
	
	int sum;
	String title;
	node Lnode;
	node Rnode;
	
	node (String title, int sum)
	{
		this.title = title;
		this.sum = sum;
	}
}
