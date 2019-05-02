import java.util.Scanner;

public class main {
	
	public static void main(String[] args)
	{
		//System.out.println(5);
		String equation = "";
		
		System.out.println("enter an equation: ");
		Scanner input = new Scanner(System.in);
		
		equation += input.nextLine();
		System.out.println(equation);
		
		Calculator myCalc = new Calculator(equation);
		myCalc.start();
	}

}
