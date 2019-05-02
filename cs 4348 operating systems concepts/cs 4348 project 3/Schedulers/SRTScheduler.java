package Schedulers;

import Main.Graph;
import Main.Job;

import java.util.ArrayList;
import java.util.List;

public class SRTScheduler extends Scheduler {
    @Override
    public Graph getGraph(List<Job> jobs) {
        Graph graph = generateGraph(jobs);
        List sortedJobs = sortList(jobs);
        graph.writeGraph(sortedJobs);
        graph.writeTitle("SRT");
        return graph;
    }

    @Override
    public List<Job> sortList(List<Job> jobs)
    {
        List<Job> sortedList = new ArrayList<>();
        List<Job> listCopy = new ArrayList<>(jobs);

        for (int i = 0; i < calcDuration(jobs); i++)
        {
            int duration = findSmallest(listCopy, i);
            //System.out.println(smallest);

            Job smallest = getSmallestJob(listCopy, duration);
            //System.out.println(smallest.toString());
            Job iteration = new Job(smallest.getTitle(), smallest.getStart(), 1);
            Job decremented = new Job(smallest.getTitle(), smallest.getStart(), smallest.getDuration() - 1);

            sortedList.add(iteration);
            listCopy.remove(smallest);

            if (decremented.getDuration() > 0)
            {
                listCopy.add(decremented);
            }
        }
        return sortedList;
    }

    private int findSmallest(List<Job> jobs, int limit)
    {
        int[] array = getDurations(jobs, limit);
        int smallest = 9999;
        for (int i : array)
        {
            if (i != -1 && i < smallest)
            {
                smallest = i;
            }
        }
        return smallest;
    }

    private int[] getDurations(List<Job> jobs, int limit)
    {
        int[] durations = new int[jobs.size()];

        for (int i = 0; i < jobs.size(); i++)
        {
            Job current = jobs.get(i);
            if (current.getStart() > limit)
            {
                durations[i] = -1;
            }
            else
            {
                durations[i] = current.getDuration();
            }
        }

        return durations;
    }

    private Job getSmallestJob(List<Job> jobs, int duration)
    {
        Job smallest = new Job("wa", -1, -1);

        for (Job j : jobs)
        {
            if (j.getDuration() == duration)
            {
                smallest = j;
            }
        }

        return smallest;
    }


}
