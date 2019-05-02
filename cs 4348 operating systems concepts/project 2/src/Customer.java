import java.util.concurrent.Semaphore;

public class Customer extends Thread{

    String wantMovie;
    int wantedSnack;

    boolean wantSnacks;
    boolean gotSnacks;
    boolean inLobby;
    boolean gotTicket;
    String threadName;
    SnacksWorker.SNACK snack;

    Semaphore boxTalkSem;
    Semaphore ticketTalkSem;
    Semaphore talkSnackSem;

    boolean finished;

    public Customer(String threadName, Semaphore boxTalkSem, Semaphore ticketTalkSem, Semaphore snackTalkSem)
    {
        this.wantMovie = selectMovie();
        wantSnacks = decideSnack();
        wantedSnack = selectSnacks();
        gotSnacks = false;
        inLobby = false;
        finished = false;
        gotTicket = false;

        this.threadName = threadName;
        this.boxTalkSem = boxTalkSem;
        this.ticketTalkSem = ticketTalkSem;
        this.talkSnackSem = snackTalkSem;
        System.out.println(threadName + " is created wants ticket to " + wantMovie + " and wants snack " + SnacksWorker.SNACK.values()[wantedSnack]);
    }

    @Override
    public void run() {
        System.out.println(threadName + " is active");

        while (!finished)
        {
            if (!gotTicket)
            {
                getTicket();
            }
            if (gotTicket && !inLobby)
            {
                enterLobby();
            }
            if (inLobby && !gotSnacks && wantSnacks)
            {
                getSnacks();
            }
            if (wantSnacks && gotSnacks)
            {
                finished = true;
            }
            if (!wantSnacks && inLobby)
            {
                finished = true;
            }
        }
        if (inLobby)
        {
            System.out.println(threadName + " is in theaters watching " + wantMovie + " with snack " + snack);

        }
        else
        {
            System.out.println(threadName + " did not get a ticket and has left the theater");
        }
        System.out.println(threadName + " is finished");
    }

    private String selectMovie()
    {
        return SharedMovieData.getRandomMovie().getTitle();
    }

    private boolean decideSnack()
    {
        int random = (int) (Math.random() * 100);
        return random % 2 == 0;
    }

    private int selectSnacks()
    {
        int result = 3;

        if (wantSnacks)
        {
            result = (int) (Math.random() * 3);
        }
        return  result;
    }

    private void getTicket()
    {
        try {
            boxTalkSem.acquire();
            //System.out.println(threadName + ": gained permission");
            int rand = (int) (Math.random() * 100);

            if (!gotTicket)
            {
                if (rand % 2 == 0)
                {
                    BoxOfficeAgent.servingCustomer1 = this;
                }
                else
                {
                    BoxOfficeAgent.servingCustomer2 = this;
                }
            }

            boxTalkSem.release();
            //System.out.println(threadName + ": released permission");
            //sleep(1000); //debug
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("wrong");
        }
    }

    private void enterLobby()
    {
        try {
            ticketTalkSem.acquire();
            //System.out.println(threadName + ": gained permission");

            if (!inLobby)
            {
                TicketTaker.servingCustomer = this;
            }

            ticketTalkSem.release();
            //System.out.println(threadName + ": released permission");
            //sleep(1000); //debug
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("wrong");
        }
    }

    public void getSnacks()
    {
        try {
            talkSnackSem.acquire();
            //System.out.println(threadName + ": gained permission");

            if (inLobby)
            {
                SnacksWorker.servingCustomer = this;
            }

            talkSnackSem.release();
            //System.out.println(threadName + ": released permission");
            //sleep(1000); //debug
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("wrong");
        }
    }

    public void setSnack(SnacksWorker.SNACK snack)
    {
        this.snack = snack;
    }
}
