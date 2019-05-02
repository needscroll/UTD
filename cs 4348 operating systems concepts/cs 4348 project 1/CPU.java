import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CPU {
	
	int PC;
	int SP;
	int IR;
	int AC;
	int X;
	int Y;
	
	Map<Integer, Operation> opmap;
	boolean kernal_mode;
	boolean interrupt;
	boolean running;
	MyTimer timer;
	int time;
	
	static Process p;
    static BufferedWriter writer;
	static Scanner scan;
	static List<String> port = new ArrayList<String>();

	int counter;
	
	CPU(int time, Process p, BufferedWriter writer, Scanner scan)
	{
		this.opmap = new HashMap<>();
		this.timer = new MyTimer(time);
		this.kernal_mode = false;
		this.interrupt = false;
		this.running = true;
		this.PC = 0;
		this.SP = 999;
		
		this.time = time;
		this.p = p;
		this.writer = writer;
		this.scan = scan;
		this.counter = 0;
		
		init_opmap();
	}
	
	public void cpu_start() throws IOException, InterruptedException
	{
		while (running)
		{
			run_step();
		}
		
		System.out.println("program done");
		System.out.println("output of program is: ");

		port.add("\n");
		port.forEach((n) -> System.out.print(n));
		
		
		p.destroy();
		System.out.println("Process exited: " + p.exitValue());
	}

	public void run_step()
	{
		/*
		 * 1. fetch from memory into IR
		 * 2. run IR
		 * 3. repeat
		 * will print out the values in the registers and what is going on for debugging purposes
		 */
		
		if (counter % time == 0)
		{
			timer_interrupt();
		}
		
		System.out.println("cpu is on step: " + counter + " ================================");
		print_registers();
		System.out.println("PC is: " + PC + " fetching instruction from memory");
		IR = fetch_memory(PC);
		PC++;
		System.out.println("IR now contains: " + IR + " running instruction with opcode: " + IR);
		
		opmap.get(IR).run();
		
		System.out.println("==================================================");
		System.out.println("");
		
		counter++;
	}
	
	public void init_opmap()
	{
		opmap.put(1, ()-> load_value());
		opmap.put(2, ()-> load_address());
		opmap.put(3, ()-> load_ind_address());
		opmap.put(4, ()-> load_idx_x_address());
		opmap.put(5, ()-> load_idx_y_address());
		opmap.put(6, ()-> load_sp_x());
		opmap.put(7, ()-> store_address());
		opmap.put(8, ()-> AC = (int) (Math.random() * 99 + 1));
		opmap.put(9, ()-> put_port());
		opmap.put(10, ()-> AC += X);
		opmap.put(11, ()-> AC += Y);
		opmap.put(12, ()-> AC -= X);
		opmap.put(13, ()-> AC -= Y);
		opmap.put(14, ()-> X = AC);
		opmap.put(15, ()-> AC = X);
		opmap.put(16, ()-> Y = AC);
		opmap.put(17, ()-> AC = Y);
		opmap.put(18, ()-> SP = AC);
		opmap.put(19, ()-> AC = SP);
		opmap.put(20, ()-> jump_address());
		opmap.put(21, ()-> jump_on_zero());
		opmap.put(22, ()-> jump_not_on_zero());
		opmap.put(23, ()-> call_address());
		opmap.put(24, ()-> ret());
		opmap.put(25, ()-> X++);
		opmap.put(26, ()-> X--);
		opmap.put(27, ()-> push());
		opmap.put(28, ()-> pop());
		opmap.put(29, ()-> syscall());
		opmap.put(30, ()-> return_syscall());
		opmap.put(50, ()-> end());
		
		opmap.put(999, ()-> System.out.println("debugging"));
	}

	private void load_value() 
	{
		//loads the next value in memory into AC
		IR = fetch_memory(PC);
		AC = IR;
		System.out.println(IR + " is now loaded into the AC");
		PC++;
	}

	private void load_address() 
	{
		//loads into AC the value at the next address in memory
		IR = fetch_memory(PC);
		AC = fetch_memory(IR);
		PC++;
	}

	private void load_ind_address() 
	{
		//loads into AC the value at the address of the next address in memory
		IR = fetch_memory(PC);
		AC = fetch_memory(fetch_memory(PC));
		PC++;
	}

	private void load_idx_x_address() {
		//loads into AC the value at the next address in memory plus x
		IR = fetch_memory(PC);
		AC = fetch_memory(IR + X);
		PC++;
	}

	private void load_idx_y_address() {
		//loads into AC the value at the next address in memory plus y
		IR = fetch_memory(PC);
		AC = fetch_memory(IR + Y);
		PC++;
	}

	private void load_sp_x()
	{
		//loads into ac the SP + X
		AC = fetch_memory(SP + X);
	}

	private void store_address() {
		//stores AC into the address found next in memory
		IR = fetch_memory(PC);
		write_memory(PC, AC);
		PC++;
	}

	private void put_port() {
		IR = fetch_memory(PC);
		System.out.println("put port called outputting " + AC);
		if (IR == 1)
		{
			port.add("" + AC);
		}
		if (IR == 2)
		{
			port.add(Character.toString((char) AC));
		}
		PC++;
	}

	private void jump_address() 
	{
		IR = fetch_memory(PC);
		PC = IR;
		//PC++; //does not need to increment
		//MAYBE NEEDS TO MINUS ONE?????
		//NOPE, ON TESTING IT REVEALS THAT IT DOESN'T
		//IF YOU DO IT, IT THROWS THE PC OFF BY ONE
	}

	private void jump_on_zero() {
		//need to add jump
		
		if (AC == 0)
		{
			System.out.println("jump occured");
			IR = fetch_memory(PC);
			PC = IR - 1;
			PC++;
		}
		else
		{
			System.out.println("Jump did not occur");
			PC++;
		}
	}

	private void jump_not_on_zero() {
		//need to add jump
		if (AC != 0)
		{
			IR = fetch_memory(PC);
			PC = IR - 1;
			PC++;
		}
		PC++;
	}

	private void call_address() {
		// push return address onto stack, jump to the new address
		write_memory(SP, PC);
		SP--;
		IR = fetch_memory(PC);
		//System.out.println("function not yet implemented!!!");

	}

	private void ret() {
		//pops return address from stack, jumps to address
		//System.out.println("function not yet implemented!!!");
		SP++;
		IR = fetch_memory(SP);
		PC = IR;
		PC++;

	}

	private void push() {
		//pushes AC onto the stack
		//System.out.println("function not yet implemented!!!");
		write_memory(SP, AC);
		SP--;
	}

	private Object pop() {
		//pops from stack onto AC
		//System.out.println("function not yet implemented!!!");
		SP++;
		AC = fetch_memory(SP);
		return null;
	}

	private void syscall() {
		this.kernal_mode = true;
		System.out.println("SYSTEM CALL CPU HAS ENTERED KERNAL MODE");
		//PC++;
		//save registers?
		write_memory(SP, PC);
		SP--;
		write_memory(SP, IR);
		SP--;
		write_memory(SP, AC);
		SP--;
		write_memory(SP, X);
		SP--;
		write_memory(SP, Y);
		SP--;
		
		PC = 1500;
		this.kernal_mode = true;
	}

	private void return_syscall() {
		//System.out.println("function not yet implemented!!!");
		
		SP++;
		Y = fetch_memory(SP);
		SP++;
		X = fetch_memory(SP);
		SP++;
		AC = fetch_memory(SP);
		SP++;
		IR = fetch_memory(SP);
		SP++;
		PC = fetch_memory(SP);
		
		this.kernal_mode = false;
	}

	private void end() {
		this.running = false;
	}
	
	private void timer_interrupt()
	{
		this.kernal_mode = true;
		System.out.println("TIMER INTERRUPT CPU HAS ENTERED KERNAL MODE");
		
		//save registers?
		write_memory(SP, PC);
		SP--;
		write_memory(SP, IR);
		SP--;
		write_memory(SP, AC);
		SP--;
		write_memory(SP, X);
		SP--;
		write_memory(SP, Y);
		SP--;
		
		PC = 1000;
	}
	
	/*
	 * this method is used to print out the information in the registers for debugging
	 */
	private void print_registers()
	{
		System.out.println("===information in the registers===");
		System.out.println("PC: " + PC);
		System.out.println("SP: " + SP);
		System.out.println("IR: " + IR);
		System.out.println("AC: " + AC);
		System.out.println("X: " + X);
		System.out.println("Y: " + Y);
		System.out.println("==================================");
	}
	
	
	//=========================================================================

	/*
	 * method used to read from a memory block
	 * this function shoudl be called instead of read()
	 * logic needs to be added to check if in user or kernal mode
	 * 
	 */
	private int fetch_memory(int i) {
		if (!kernal_mode && i > 999)
		{
			System.out.println("User tried to acess system memory. program will now exit");
			running = false;
			return 0;
		}
		
		//add logic to check if in user or kernal mode here
		
    	try {
			write("r" + Integer.toString(i));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String in = read();
    	System.out.println(in);
    	String split[] = in.split("<");
    	String split1[] = split[split.length - 1].split(">");
    	
    	//debugging print
    	//System.out.println("this is the memory retrieved: " + split1[0]);
		
		return Integer.parseInt(split1[0]);
	}
    
	/*
	 * method used to write to the memory
	 * this function should be called instead of write()
	 */
    private void write_memory(int address, int value)
    {
		if (!kernal_mode && address > 999)
		{
			System.out.println("User tried to acess system memory. program will now exit");
			running = false;
			return;
		}
		
    	try {
			write("w:" + address + ":" + value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String in = read();
    	System.out.println(in);
    }
    
	/*
	 * writes to the child's input
	 * should not directly call this function
	 * function should only be called indirectly through write/fetch memory
	 */	
    public static void write(String msg) throws IOException, InterruptedException
	{
		writer.write(msg +  "\n");
		p.waitFor(10, TimeUnit.MILLISECONDS);
		writer.flush();
	}
	
	/*
	 * reads from the child's output
	 * should not directly call this function
	 * function should only be called indirectly through write/fetch memory
	 */
	public static String read()
	{
		if (scan.hasNextLine())
		{
			String in = scan.nextLine();
			if (in != null)
			{
				return in;
			}
		}
		return "";
	}

}
