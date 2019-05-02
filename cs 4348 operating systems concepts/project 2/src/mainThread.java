import java.util.concurrent.Semaphore;

public class mainThread extends Thread {

    Semaphore sem;
    String threadName;
    public mainThread(Semaphore sem, String threadName)
    {
        super(threadName);
        this.sem = sem;
        this.threadName = threadName;
    }


    /*
    @Override
    public void run() {
        if (this.getName().equals("a"))
        {
            System.out.println(threadName + " is waiting for ");
            try {
                sem.acquire();
                System.out.println(threadName + "gets a permission");
                for (int i = 0; i < 5; i++)
                {
                    SharedMovieData.count++;
                    System.out.println(threadName + SharedMovieData.count);
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + " release perm");
            sem.release();

        }
        else
        {
            System.out.println("starting " + threadName);
            try
            {
                System.out.println(threadName + " is waiting for perm");
                sem.acquire();
                System.out.println(threadName + "gets a permission");
                for (int i = 0; i < 5; i++)
                {
                    SharedMovieData.count--;
                    System.out.println(threadName + SharedMovieData.count);
                    Thread.sleep(10);
                }

            } catch (InterruptedException ex)
            {
                System.out.print(ex);
            }
            System.out.println(threadName + " release perm");
            sem.release();
        }
    }
    */

}
