import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/*******************************************************************
 * this class reads in the config file and parses it to return the data
 ******************************************************************/
public class IOParser {

    String filename;
    int interRequestDelay;
    int executionTime;
    int numberOfRequests;
    ArrayList<NodeInfo> nodeInfo = new ArrayList<>();
    HashMap<NodeInfo, ArrayList<NodeInfo>> neighborMap = new HashMap<>();

    public IOParser(String filename) {
        this.filename = filename;

        UserIO io = new UserIO();
        ArrayList<String> data = io.get_file_contents(filename);
        data = (ArrayList<String>) data.stream().filter(s -> s.length() > 0 && s.charAt(0) != '#').collect(Collectors.toList());

        String firstLine = data.get(0);
        String[] firstSplit = firstLine.split("\\s+");
        this.interRequestDelay = Integer.valueOf(firstSplit[1]);
        this.executionTime = Integer.valueOf(firstSplit[2]);
        this.numberOfRequests = Integer.valueOf(firstSplit[3]);

        List<String> nodes = data.subList(1, data.size() - 1);

        for (String s : nodes)
        {
            s = s.replace(" ", ":");
            nodeInfo.add(new NodeInfo(s));
        }
    }

    public NodeInfo getMyNode(int index)
    {
        return nodeInfo.get(index);
    }

    public ArrayList<NodeInfo> getMyNeighbors(int index)
    {
        ArrayList<NodeInfo> neighbors = (ArrayList<NodeInfo>) nodeInfo.clone();
        neighbors.remove(getMyNode(index));
        return neighbors;
    }

    public int getTotalNodes ()
    {
        return nodeInfo.size();
    }
}
