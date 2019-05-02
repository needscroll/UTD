package Schedulers;

import Main.Graph;
import Main.Job;

import java.util.ArrayList;
import java.util.List;

public class HRRNScheduler extends Scheduler {
    @Override
    public Graph getGraph(List<Job> jobs) {
        Graph graph = generateGraph(jobs);
        List sortedJobs = sortList(jobs);
        graph.writeGraph(sortedJobs);
        graph.writeTitle("HRRN");
        return graph;
    }

    @Override
    public List<Job> sortList(List<Job> jobs) {
        List<Job> sortedList = new ArrayList<>();
        List<Job> listCopy = new ArrayList<>(jobs);
        int[] waiting = new int[listCopy.size()];

        for (int i = 0; i < waiting.length; i++) {
            waiting[i] = 0;
        }

        for (int i = 0; i < calcDuration(jobs); i++)
        {
            int index = getLargestIndex(listCopy, waiting, i);
            //System.out.println(index);

            Job largest = listCopy.get(index);
            Job increment = new Job(largest.getTitle(), largest.getStart(), 1);
            Job decremented = new Job(largest.getTitle(), largest.getStart(), largest.getDuration() - 1);

            sortedList.add(increment);
            listCopy.remove(largest);

            if (decremented.getDuration() > 0)
            {
                listCopy.add(decremented);
            }

            for (int j = 0; j < waiting.length; j++)
            {
                if (j != index)
                {
                    waiting[j]++;
                }
            }
            //System.out.println(i);
        }


        return sortedList;
    }

    private double[] calcRTimes(List<Job> jobs, int[] waiting, int limit)
    {
        double[] times = new double[jobs.size()];

        for (int i = 0; i < jobs.size(); i++)
        {
            Job job = jobs.get(i);

            if (job.getStart() > limit)
            {
                times[i] = -1;
            }
            else
            {
                times[i] = calcRTime(job, waiting[i]);

            }
        }

        return times;
    }

    private double calcRTime(Job job, int waiting)
    {
        double time = 0;

        time = (waiting + job.getDuration()) / job.getDuration();

        return time;
    }

    private int getLargestIndex(List<Job> jobs, int[] waiting, int limit)
    {
        double[] array = calcRTimes(jobs, waiting, limit);

        int index = 0;
        double smallest = -1;

        for (int i = 0; i < array.length; i++)
        {
            if (array[i] > smallest)
            {
                index = i;
                smallest = array[i];
            }
        }

        return index;
    }
}
