import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/*******************************************************************
 * let args be the following
 * 0 - which node this is
 * 1 - path to config file
 ******************************************************************/
public class Main {

    public static void main(String[] args) {

        for (String s : args)
        {
            System.out.println(s);
        }

        if (args.length != 2)
        {
            System.out.println("ERROR: bad input");
            return;
        }

        int myIndex = Integer.valueOf(args[0]);
        String filename = args[1];
        IOParser parser = new IOParser(filename);
        NodeInfo myNode = parser.getMyNode(myIndex);
        ArrayList<NodeInfo> neighbors = parser.getNodeNeighbors(myNode);
        int totalNodes = parser.getTotalNodes();

        System.out.println(myNode.toString());
        for (NodeInfo n : neighbors)
        {
            System.out.println("Neighbors:" + n.toString());
        }

        try {
            Node node = new Node(myNode, neighbors, totalNodes);
            node.setConfigFilename(filename);
            node.go();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
