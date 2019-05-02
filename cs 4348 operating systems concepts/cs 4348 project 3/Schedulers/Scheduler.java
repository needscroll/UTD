package Schedulers;

import Main.Graph;
import Main.Job;

import java.util.ArrayList;
import java.util.List;

public abstract class Scheduler {

    public abstract Graph getGraph(List<Job> jobs);

    public abstract List<Job> sortList(List<Job> jobs);

    protected Graph generateGraph(List<Job> jobs)
    {
        int columns = calcDuration(jobs) + 1;
        int rows = jobs.size() + 1;
        List<String> titles = new ArrayList<String>();

        /*
        for (Job j : jobs)
        {
            columns += j.getDuration();
        }
        */

        for (Job j : jobs)
        {
            titles.add(j.getTitle());
        }

        Graph graph = new Graph(rows, columns, titles.toArray(new String[1]));
        return graph;
    }

    protected int calcDuration(List<Job> jobs)
    {
        int duration = 0;
        for (Job j : jobs)
        {
            duration += j.getDuration();
        }
        return duration;
    }

    protected boolean jobArriving(List<Job> jobs, int arrivalTime)
    {
        for (Job j : jobs)
        {
            if (j.getStart() == arrivalTime)
            {
                return true;
            }
        }
        return false;
    }

    protected Job getArrivingJob(List<Job> jobs, int arrivalTime)
    {
        Job job = new Job("empty00");
        for (Job j : jobs)
        {
            if (j.getStart() == arrivalTime)
            {
                job = j;
            }
        }
        return job;
    }
}
