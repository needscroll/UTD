import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/*******************************************************************
 * this class reads in the config file and parses it to return the data
 ******************************************************************/
public class IOParser {

    String filename;
    ArrayList<NodeInfo> nodeInfo = new ArrayList<>();
    int numberOfNodes;
    int interRequestDelay;
    int csExecutionTime;
    int numberOfRequests;

    public IOParser(String filename) {
        this.filename = filename;

        UserIO io = new UserIO();
        ArrayList<String> data = io.getFileContents(filename);
        data = (ArrayList<String>) data.stream().filter(s -> s.length() > 0 && s.charAt(0) != '#').collect(Collectors.toList());

        //set up data from the first line
        String[] firstSplit = data.get(0).split("\\s+");

        this.numberOfNodes = Integer.valueOf(firstSplit[0]);
        this.interRequestDelay = Integer.valueOf(firstSplit[1]);
        this.csExecutionTime = Integer.valueOf(firstSplit[2]);
        this.numberOfRequests = Integer.valueOf(firstSplit[3]);

        List<String> nodes = data.subList(1, 1 + numberOfNodes);

        for (int i = 0; i < nodes.size(); i++) {
            String s = nodes.get(i);
            String newString = "";
            String[] split = s.split(" ");
            for (String s1 : split) {
                newString += s1 + ":";
            }
            newString = newString.substring(0, newString.length() - 1);
            nodes.set(i, newString);
        }
        //this now contains all node info
        for (String s : nodes) {
            nodeInfo.add(new NodeInfo(s));
        }
    }

    public NodeInfo getMyNode(int id)
    {
        return (nodeInfo.stream().filter(n -> n.getId() == id).collect(Collectors.toList())).get(0);
    }

    public ArrayList<NodeInfo> getNodeNeighbors(NodeInfo myNode)
    {
        return (ArrayList<NodeInfo>) nodeInfo.stream().filter(n -> !n.equals(myNode)).collect(Collectors.toList());
    }

    public int getTotalNodes()
    {
        return nodeInfo.size();
    }

    public int getInterRequestDelay() {
        return interRequestDelay;
    }

    public int getCsExecutionTime() {
        return csExecutionTime;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }
}
