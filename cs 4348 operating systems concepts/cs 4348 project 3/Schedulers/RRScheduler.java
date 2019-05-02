package Schedulers;

import Main.Graph;
import Main.Job;

import java.util.ArrayList;
import java.util.List;

public class RRScheduler extends Scheduler {
    @Override
    public Graph getGraph(List<Job> jobs) {
        Graph graph = generateGraph(jobs);
        List sortedJobs = sortList(jobs);
        graph.writeTitle("RR");
        graph.writeGraph(sortedJobs);
        return graph;
    }

    @Override
    public List<Job> sortList(List<Job> jobs)
    {
        List<Job> sortedList = new ArrayList<>();
        List<Job> que = new ArrayList<>();

        for (int i = 0; i < calcDuration(jobs); i++)
        {
            if (jobArriving(jobs, i))
            {
                Job firstJob = new Job("default", -1, -1);
                if (que.size() > 0)
                {
                    firstJob = que.remove(que.size() - 1);
                }
                que.add(getArrivingJob(jobs, i));

                if (firstJob.getDuration() > 0)
                {
                    que.add(firstJob);
                }

            }
            String title = que.get(0).getTitle();
            int duration = que.get(0).getDuration();
            int start = que.get(0).getStart();
            Job currentJob = new Job(title, start, 1);

            sortedList.add(currentJob);

            Job reducedJob = new Job(title, start, duration - 1);
            if (reducedJob.getDuration() > 0)
            {
                que.add(reducedJob);
            }
            que.remove(0);
        }
        return sortedList;
    }
}
