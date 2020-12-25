import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

/******************************************************************
 * this class encapsulates a node and contains logic
 * on how to process and send messages
 ******************************************************************/
public class Node {

    NodeInfo nodeInfo;
    int roundNumber;
    int totalNodes;

    ArrayList<NodeInfo> neighbors;
    ArrayList<Message> messageLog;
    NetworkManager networkManager;
    String configFilename;

    public Node(NodeInfo info, ArrayList<NodeInfo> neighborInfo, int totalNodes) throws Exception {
        this.nodeInfo = info;
        this.neighbors = neighborInfo;
        this.roundNumber = 0;
        this.totalNodes = totalNodes;
        this.messageLog = new ArrayList<>();
        //creats and inits networking logic
        networkManager = new NetworkManager(nodeInfo, neighborInfo);
    }

    /******************************************************************
     * contains logic for main ecentricity algorithm
     * runs after all connections have been set
     ******************************************************************/
    public void go() throws Exception {
        System.out.println(nodeInfo.getDxMachine() + " is running on port: " + nodeInfo.getPort());

        //starts the initial packet
        Message firstMessage = new Message(nodeInfo, false);
        for (NodeInfo n : neighbors)
        {
            MessagePacket packet = new MessagePacket(nodeInfo, n);
            packet.addMessages(firstMessage);
            networkManager.sendOff(packet);
        }

        for (int i = 0; i < totalNodes; i++)
        {
            System.out.println("Node debug:" + nodeInfo.getDxMachine() + ":round:" + i + " =====================");
            ArrayList<MessagePacket> incomingBoxes = networkManager.recieveAllIncoming();
            ArrayList<MessagePacket> outgoingBoxes = createOutgoingBoxes(incomingBoxes);

            for (MessagePacket mp : outgoingBoxes)
            {
                networkManager.sendOff(mp);
            }
        }

        Thread.sleep(5000);
    }

    /******************************************************************
     * reads messages in incomming boxes and places then in
     * appropriate outgoing boxes
     ******************************************************************/
    public ArrayList<MessagePacket> createOutgoingBoxes(ArrayList<MessagePacket> incomingBoxes)
    {
        ArrayList<MessagePacket> outgoingBoxes = new ArrayList<>();
        for (NodeInfo n : neighbors)
        {
            MessagePacket packet = new MessagePacket(nodeInfo, n);
            outgoingBoxes.add(packet);
        }

        //processes incomming box messages here
        for (MessagePacket mp : incomingBoxes)
        {
            if (!mp.isEmpty())
            {
                //process packet here
            }
        }
        return outgoingBoxes;
    }
}
