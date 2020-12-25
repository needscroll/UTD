import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
    {
        System.out.println("PROGRAM START ==============================");
        /*
        for (String s : args)
        {
            System.out.println(s);
        }*/

        if (args.length != 2)
        {
            System.out.println("ERROR: bad input");
            return;
        }

        int myIndex = Integer.valueOf(args[0]);
        String filename = args[1];

        IOParser parser = new IOParser(filename);
        NodeInfo myNode = parser.getMyNode(myIndex);
        ArrayList<NodeInfo> neighbors = parser.getMyNeighbors(myIndex);
        int totalNodes = parser.getTotalNodes();

        System.out.println(myNode.toString());
        for (NodeInfo n : neighbors)
        {
            System.out.println("Neighbors:" + n.toString());
        }

        try {
            Node node = new Node(myNode, neighbors, totalNodes);
            node.go();
        } catch (Exception e) {
            e.printStackTrace();
        }









        System.out.println("PROGRAM END ==============================");
    }
}
