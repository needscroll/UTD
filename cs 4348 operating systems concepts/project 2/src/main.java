import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class main {

    public static void main(String[] args) throws InterruptedException
    {
        UserIO io = new UserIO();
        String[] data = io.ask_file_contents("enter the file name: ");
        SharedMovieData.initMovie(data);
        printDebug();

        Semaphore snackTalkSem = new Semaphore(1);
        Semaphore ticketTalkSem = new Semaphore(1);
        Semaphore boxTalkSem = new Semaphore(1);

        ArrayList<Customer> customers = new ArrayList<Customer>();

        for (int i = 0; i < 50; i++)
        {
            customers.add(new Customer("c" + i, boxTalkSem, ticketTalkSem, snackTalkSem));
        }

        BoxOfficeAgent b1 = new BoxOfficeAgent("agent 1", boxTalkSem, 1);
        BoxOfficeAgent b2 = new BoxOfficeAgent("agent 2", boxTalkSem, 2);
        TicketTaker t1 = new TicketTaker("ticketTaker 1", ticketTalkSem);
        SnacksWorker s1 = new SnacksWorker("snack workder 1", snackTalkSem);

        s1.start();
        b1.start();
        b2.start();
        t1.start();
        customers.forEach((i)-> i.start());

        while (running(customers))
        {
            sleep(1000);
        }

        b1.finished = true;
        b2.finished = true;
        t1.finished = true;
        s1.finished = true;

        b1.join();
        b2.join();
        t1.join();
        s1.join();
        customers.forEach((i)-> {
            try {
                i.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("all threads have been joined");
        System.out.println("program has completed");
    }

    public static void printDebug()
    {
        Movie[] result = SharedMovieData.getFinalData();
        Movie[] result1 = SharedMovieData.getActualData();

        System.out.println("number of movies: " + result.length);
        System.out.println("final movie data");
        for(Movie m: result)
        {
            System.out.println(m.toString());
        }
        System.out.println("actual movie data");
        for(Movie m: result1)
        {
            System.out.println(m.toString());
        }
    }

    public static boolean running(ArrayList<Customer> customers)
    {
        for (Customer c: customers)
        {
            if (!c.finished)
            {
                return true;
            }
        }
        return false;
    }
}
