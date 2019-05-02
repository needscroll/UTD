import java.util.concurrent.Semaphore;

public class TicketTaker extends Thread{

    private String threadName;
    Semaphore ticketTalkSem;
    public boolean finished = false;

    public static Customer servingCustomer;

    public TicketTaker(String threadName, Semaphore ticketTalkSem)
    {
        System.out.println(threadName + " is created");
        this.ticketTalkSem = ticketTalkSem;
        this.threadName = threadName;
    }

    @Override
    public void run() {
        System.out.println(threadName + " is active");
        while (!finished)
        {

            try {
                ticketTalkSem.acquire();
                //System.out.println(threadName + ": gained permission");

                if (servingCustomer != null && servingCustomer.gotTicket && !servingCustomer.inLobby && !servingCustomer.finished)
                {
                    serveCustomer();
                }

                ticketTalkSem.release();
                //System.out.println(threadName + ": released permission");

                //sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(threadName + " is finished");
    }

    private void serveCustomer() throws InterruptedException {
        if (!servingCustomer.inLobby && servingCustomer.gotTicket)
        {
            servingCustomer.inLobby = true;
            System.out.println(servingCustomer.threadName + " entered the lobby");
        }
        sleep(15000/60);
    }
}
