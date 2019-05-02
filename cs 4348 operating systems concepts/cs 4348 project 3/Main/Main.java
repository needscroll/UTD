package Main;

import Schedulers.Scheduler;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args)
    {
        //System.out.println(5);
        UserIO io = new UserIO();
        List<Job> jobs = new ArrayList<Job>();
        List<Graph> graphs = new ArrayList<Graph>();

        String command = args[0];
        if (command == null)
        {
            command = "ALL";
        }
        else
        {
            command = command.toUpperCase();
        }

        SchedulerFactory factory = new SchedulerFactory();
        Scheduler[] schedulers = factory.getSchedulers(command);

        //String[] raw_jobs = io.ask_file_contents("enter the name of the file: ");
        String[] raw_jobs = io.ask_file_contents("please enter the name of the txt file: ");

        for (String s: raw_jobs)
        {
            Job job = new Job(s);
            jobs.add(job);
            //System.out.println(job.toString());
        }

        for (Scheduler s: schedulers)
        {
            graphs.add(s.getGraph(jobs));
        }

        for (Graph g: graphs)
        {
            System.out.println(g.toString());
        }
    }
}
