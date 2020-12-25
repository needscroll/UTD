import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/*******************************************************************
 * this class reads in the config file and parses it to return the data
 ******************************************************************/
public class IOParser {

    String filename;
    NodeInfo myInfo;
    ArrayList<NodeInfo> nodeInfo = new ArrayList<>();
    HashMap<NodeInfo, ArrayList<NodeInfo>> neighborMap = new HashMap<>();

    public IOParser(String filename) {
        this.filename = filename;

        UserIO io = new UserIO();
        ArrayList<String> data = io.get_file_contents(filename);
        data = (ArrayList<String>) data.stream().filter(s -> s.length() > 0 && s.charAt(0) != '#').collect(Collectors.toList());
        int numberOfNodes = Integer.valueOf(data.get(0));
        List<String> nodes = data.subList(1, 1 + numberOfNodes);
        List<String> neighborsData = data.subList(numberOfNodes + 1, data.size());

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

        //this sets the neighborsData
        for (int i = 0; i < neighborsData.size(); i++) {
            ArrayList<NodeInfo> neighborNodes = new ArrayList<>();

            String s = neighborsData.get(i);
            s = s.substring(0, s.indexOf("#"));
            String[] split = s.split(" ");

            for (String s1 : split) {
                NodeInfo n = getNeighbor(s1, nodeInfo);
                neighborNodes.add(n);
            }
            neighborMap.put(nodeInfo.get(i), neighborNodes);
        }
    }

    public NodeInfo getNeighbor(String neighbor, ArrayList<NodeInfo> nodes)
    {
        for (NodeInfo n : nodes)
        {
            if (Integer.valueOf(neighbor) == n.getId())
            {
                return n;
            }
        }
        return null;
    }

    public NodeInfo getMyNode(int id)
    {
        return getNeighbor(String.valueOf(id), nodeInfo);
    }

    public ArrayList<NodeInfo> getNodeNeighbors(NodeInfo myNode) {
        return neighborMap.get(myNode);
    }

    public int getTotalNodes ()
    {
        return nodeInfo.size();
    }
}
