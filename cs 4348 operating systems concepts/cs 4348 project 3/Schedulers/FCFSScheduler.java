package Schedulers;

import Main.Graph;
import Main.Job;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FCFSScheduler extends Scheduler {
    @Override
    public Graph getGraph(List<Job> jobs) {
        Graph graph = generateGraph(jobs);
        List<Job> sortedJobs = sortList(jobs);
        graph.writeGraph(sortedJobs);
        graph.writeTitle("FCFS");
        return graph;
    }

    @Override
    public List<Job> sortList(List<Job> jobs) {
        List<Job> listCopy = new ArrayList<>(jobs);
        Collections.sort(listCopy);
        return listCopy;
    }
}
