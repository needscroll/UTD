package Main;

import Schedulers.*;

import java.util.ArrayList;
import java.util.List;

public class SchedulerFactory {

    public Scheduler[] getSchedulers(String command)
    {
        List<Scheduler> schedulers = new ArrayList<Scheduler>();

        switch (command)
        {
            case "FB":
                schedulers.add(new FBScheduler());
                break;
            case "FCFS":
                schedulers.add(new FCFSScheduler());
                break;
            case "HRRN":
                schedulers.add(new HRRNScheduler());
                break;
            case "RR":
                schedulers.add(new RRScheduler());
                break;
            case "SPN":
                schedulers.add(new SPNScheduler());
                break;
            case "SRT":
                schedulers.add(new SRTScheduler());
                break;
            case "ALL":
                schedulers.add(new FBScheduler());
                schedulers.add(new FCFSScheduler());
                schedulers.add(new HRRNScheduler());
                schedulers.add(new RRScheduler());
                schedulers.add(new SPNScheduler());
                schedulers.add(new SRTScheduler());
                break;
            default:
                System.out.println("command not recognized. defaulting to ALL");
                schedulers.add(new FBScheduler());
                schedulers.add(new FCFSScheduler());
                schedulers.add(new HRRNScheduler());
                schedulers.add(new RRScheduler());
                schedulers.add(new SPNScheduler());
                schedulers.add(new SRTScheduler());
        }

        return schedulers.toArray(new Scheduler[1]);
    }
}
