package Schedulers;

import Main.Graph;
import Main.Job;

import java.util.ArrayList;
import java.util.List;

public class SPNScheduler extends Scheduler {

    @Override
    public Graph getGraph(List<Job> jobs) {
        Graph graph = generateGraph(jobs);
        List sortedJobs = sortList(jobs);
        graph.writeGraph(sortedJobs);
        graph.writeTitle("SPN");
        return graph;
    }

    @Override
    public List<Job> sortList(List<Job> jobs) {
        List<Job> sortedList = new ArrayList<>();
        List<Job> listCopy = new ArrayList<>(jobs);

        for (int i = 0, counter = 0; i < 5; i++)
        {
            Job shortest = findShortest(listCopy, counter);
            sortedList.add(shortest);
            listCopy.remove(shortest);
            counter += shortest.getDuration();
        }

        return sortedList;
    }

    private Job findShortest(List<Job> jobs, int limit)
    {
        Job shortest = new Job("default", 9999, 9999);
        for (Job j : jobs)
        {
            if (j.getStart() <= limit && j.getDuration() <= shortest.getDuration())
            {
                shortest = j;
            }
        }
        return shortest;
    }
}
