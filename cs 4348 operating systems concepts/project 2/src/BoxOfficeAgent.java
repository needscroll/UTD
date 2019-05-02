import java.util.concurrent.Semaphore;

public class BoxOfficeAgent extends Thread{

    private String threadName;
    Semaphore boxTalkSem;
    public boolean finished = false;
    public int agentNumber;

    public static Customer servingCustomer1;
    public static Customer servingCustomer2;

    public BoxOfficeAgent(String threadName, Semaphore boxTalkSem, int agentNumber)
    {
        System.out.println(threadName + " is created");
        this.boxTalkSem = boxTalkSem;
        this.threadName = threadName;
        this.agentNumber = agentNumber;
    }

    @Override
    public void run() {
        System.out.println(threadName + " is active");
        while (!finished)
        {

            try {
                boxTalkSem.acquire();
                //System.out.println(threadName + ": gained permission");

                if (agentNumber == 1)
                {
                    if (servingCustomer1 != null && !servingCustomer1.gotTicket && !servingCustomer1.finished)
                    {
                        serveCustomer();
                    }
                }
                else
                {
                    if (servingCustomer2 != null && !servingCustomer2.gotTicket && !servingCustomer2.finished)
                    {
                        serveCustomer();
                    }
                }

                boxTalkSem.release();
                //System.out.println(threadName + ": released permission");

                //sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(threadName + " is finished");
    }

    private void serveCustomer() throws InterruptedException {
        if (agentNumber == 1)
        {
            String movie = servingCustomer1.wantMovie;

            if (!SharedMovieData.isFull(movie))
            {
                SharedMovieData.incrementMovie(movie);
                servingCustomer1.gotTicket = true;
                System.out.println(servingCustomer1.threadName + " bought a ticket to " + servingCustomer1.wantMovie);
            }
            else
            {
                System.out.println(servingCustomer1.wantMovie + " is full " + servingCustomer1.threadName + " is leaving");
                servingCustomer1.finished = true;
            }
        }
        else
        {
            String movie = servingCustomer2.wantMovie;

            if (!SharedMovieData.isFull(movie))
            {
                SharedMovieData.incrementMovie(movie);
                servingCustomer2.gotTicket = true;
                System.out.println(servingCustomer2.threadName + " bought a ticket to " + servingCustomer2.wantMovie);
            }
            else
            {
                System.out.println(servingCustomer2.wantMovie + " is full " + servingCustomer2.threadName + " is leaving");
                servingCustomer2.finished = true;
            }
        }
        sleep(90000 / 60);
    }
}
