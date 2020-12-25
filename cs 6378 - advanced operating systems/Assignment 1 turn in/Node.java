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

        messageLog = pruneMessageLog(messageLog);

        //debug
        for (Message m : messageLog)
        {
            System.out.println("Node debug:" + nodeInfo.getDxMachine() + m.getHistoryPathMapString());
        } //debug
        //waits for all other nodes to finish
        Thread.sleep(1000);
        //prints the final output
        outputOutput(computeOutput(messageLog));
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
            messageLog.addAll(mp.getMessages());
            ArrayList<Message> messages = mp.getMessages();
            for (Message m : messages)
            {
                for (MessagePacket p : outgoingBoxes)
                {
                    if (!p.destination.equals(mp.origin))
                    {
                        m.incrementMessage(nodeInfo);
                        p.addMessages(m);
                    }
                }
            }
        }
        return outgoingBoxes;
    }
    /******************************************************************
     * computes final output for k hop neighbors and ecentricity
     * from the message log
     ******************************************************************/
    private String computeOutput(ArrayList<Message> messageLog)
    {
        String output = "\nFINAL OUTOUT FOR:" + nodeInfo.toString() + ":\n";
        output += nodeInfo.toString() + "\n";
        HashMap<String, Integer> distanceMap = computeDistances(messageLog);
        int ecentricity = Collections.max(distanceMap.values());

        for (int i = 1; i <= ecentricity; i++)
        {
            Set<String> keySet = distanceMap.keySet();
            for (String s : keySet)
            {
                if (distanceMap.get(s) == i)
                {
                    output += s;
                }
            }
            output += "\n";
        }

        output += "ECENTRICITY:" + ecentricity + "\n";
        return output;
    }

    /******************************************************************
     * computes distance to each node
     ******************************************************************/
    private HashMap<String, Integer> computeDistances(ArrayList<Message> messageLog)
    {
        HashMap<String, Integer> distanceMap = new HashMap<>();
        messageLog = (ArrayList<Message>) messageLog.stream().filter(m -> !m.getStartingNode().equals(nodeInfo)).collect(Collectors.toList());

        for (Message m : messageLog)
        {
            NodeInfo startingNode = m.getStartingNode();
            int distance = m.getHistoryPath().size();

            if (distanceMap.get(startingNode.toString()) == null)
            {
                distanceMap.put(startingNode.toString(), distance);
            }
            else
            {
                if (distanceMap.get(startingNode.toString()) > distance)
                {
                    distanceMap.remove(startingNode.toString());
                    distanceMap.put(startingNode.toString(), distance);
                }
            }
        }

        return distanceMap;
    }

    /******************************************************************
     * computes the ecentricity from message logs
     ******************************************************************/
    private int computeEcentricity(ArrayList<Message> messageLog) {

        HashMap<String, Integer> ecentricityMap = new HashMap<>();
        messageLog = (ArrayList<Message>) messageLog.stream().filter(m -> !m.getStartingNode().equals(nodeInfo)).collect(Collectors.toList());

        for (Message m : messageLog)
        {
            NodeInfo startingNode = m.getStartingNode();
            int messageEcentricity = m.getHistoryPath().size();

            if (ecentricityMap.get(startingNode.toString()) == null)
            {
                ecentricityMap.put(startingNode.toString(), messageEcentricity);
            }
            else
            {
                if (ecentricityMap.get(startingNode.toString()) > messageEcentricity)
                {
                    ecentricityMap.remove(startingNode.toString());
                    ecentricityMap.put(startingNode.toString(), messageEcentricity);
                }
            }
        }

        return Collections.max(ecentricityMap.values());
    }
    /******************************************************************
     * prunes messages if needed
     ******************************************************************/
    private ArrayList<Message> pruneMessageLog(ArrayList<Message> messageLog)
    {
        ArrayList<Message> prunedMessages = new ArrayList<>();

        for (Message m : messageLog)
        {
            if (m.getLastSender().equals(nodeInfo))
            {
                m.prune();
            }
            prunedMessages.add(m);
        }

        return prunedMessages;
    }

    public void setConfigFilename(String configFilename)
    {
        this.configFilename = configFilename;
    }

    private void outputOutput(String output)
    {
        System.out.println(output);
        String[] split = configFilename.split("\\.");
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(split[0] + "_output_" + nodeInfo.getId() + ".dat"));
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
