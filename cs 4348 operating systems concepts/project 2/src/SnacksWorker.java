import java.util.concurrent.Semaphore;

public class SnacksWorker extends Thread{

    public static String customerOrder = "";
    public static String servedOrder = "";
    private String threadName = "SnacksWorker: ";
    Semaphore talkSnackSem;
    public boolean finished = false;

    public static Customer servingCustomer;

    public static enum SNACK
    {
        POPCORN, SODA, BOTH, NONE;
    }

    public SnacksWorker(String threadName, Semaphore talkSnackSem)
    {
        this.threadName = threadName;
        this.talkSnackSem = talkSnackSem;
    }

    @Override
    public void run() {
        System.out.println(threadName + " is active");
        while (!finished)
        {
            try {
                talkSnackSem.acquire();
                //System.out.println(threadName + ": gained permission");

                if (servingCustomer != null && servingCustomer.inLobby && !servingCustomer.gotSnacks)
                {
                    serveCustomer();
                }

                talkSnackSem.release();
                //System.out.println(threadName + ": released permission");

                //sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(threadName + " is finished");
    }

    private void serveCustomer() throws InterruptedException {
        servingCustomer.setSnack(SNACK.values()[servingCustomer.wantedSnack]);
        servingCustomer.gotSnacks = true;
        servingCustomer.finished = true;
        System.out.println(servingCustomer.threadName + " got snack: " + servingCustomer.snack);
        sleep(180000 / 60);
    }

}
