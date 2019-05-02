package Schedulers;

import Main.Graph;
import Main.Job;

import java.util.ArrayList;
import java.util.List;

public class FBScheduler extends Scheduler {
    @Override
    public Graph getGraph(List<Job> jobs) {
        Graph graph = generateGraph(jobs);
        List<Job> sortedJobs = sortList(jobs);
        graph.writeGraph(sortedJobs);
        graph.writeTitle("FB");
        return graph;
    }

    @Override
    public List<Job> sortList(List<Job> jobs)
    {
        List<Job> sortedList = new ArrayList<>();
        List<Job> listCopy = new ArrayList<>(jobs);
        List<Job> que1 = new ArrayList<>();
        List<Job> que2 = new ArrayList<>();
        List<Job> que3 = new ArrayList<>();

        for (int i = 0; i < calcDuration(jobs); i++) {
            if (jobArriving(jobs, i)) {
                Job arrivingJob = getArrivingJob(listCopy, i);
                que1.add(arrivingJob);
                //System.out.println(arrivingJob.toString());
            }

            if (que1.size() > 0)
            {
                Job j = que1.remove(0);
                Job increment = new Job(j.getTitle(), j.getStart(), 1);
                Job decrement = new Job(j.getTitle(), j.getStart(), j.getDuration() - 1);
                sortedList.add(increment);
                if (decrement.getDuration() > 0)
                {
                    que2.add(decrement);
                }
            }
            else if(que2.size() > 0)
            {
                Job j = que2.remove(0);
                Job increment = new Job(j.getTitle(), j.getStart(), 1);
                Job decrement = new Job(j.getTitle(), j.getStart(), j.getDuration() - 1);
                sortedList.add(increment);
                if (decrement.getDuration() > 0)
                {
                    que3.add(decrement);
                }
            }
            else if (que3.size() > 0)
            {
                Job j = que3.remove(0);
                Job increment = new Job(j.getTitle(), j.getStart(), 1);
                Job decrement = new Job(j.getTitle(), j.getStart(), j.getDuration() - 1);
                sortedList.add(increment);
                if (decrement.getDuration() > 0)
                {
                    que3.add(decrement);
                }
            }
        }
        return sortedList;
    }
}
