import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class launcher {
	
	static boolean running = true;
	static Runtime r;
	static Process p;
	static InputStream input;
	static OutputStream output;
    static BufferedWriter writer;
	static Scanner scan;
	static UserIO io = new UserIO();
	static CPU cpu;
    
	public static void main(String[] args)
	{
		System.out.println("the program has started from the launcher");
		int time = io.ask_num(0, Integer.MAX_VALUE, "time interval between interrupts?: ");
		String file = io.ask_string("Enter program file: ");
		
		try 
		{
			r = Runtime.getRuntime();
			p = r.exec("java MemoryManager " + file);
			input = p.getInputStream();
			output = p.getOutputStream();
	        writer = new BufferedWriter(new OutputStreamWriter(output));
			scan = new Scanner(input);
			
			p.waitFor(100, TimeUnit.MILLISECONDS);
			String in = "";
			while (!in.contains("memory is now fully initialized and started"))
			{
				if (scan.hasNextLine())
				{
					in = scan.nextLine();
					if (in != null)
					{
						System.out.println(in);
					}
				}
			}
			
			cpu = new CPU(time, p, writer, scan);
			cpu.cpu_start();
		}catch(Exception e)
		{
			//e.printStackTrace();
		}
		finally
		{
			System.out.println("program has stopped");
			p.destroy();
			r.exit(0);
		}
		
	}

}
