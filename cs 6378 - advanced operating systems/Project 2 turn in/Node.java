import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/******************************************************************
 * this class encapsulates a node and contains logic
 * on how to process and send messages
 ******************************************************************/
public class Node {

    NodeInfo myNode;
    int totalNodes;
    String outputFilename = System.getProperty("user.dir") + "/CriticalSectionLog.txt";
    UserIO io;

    ArrayList<NodeInfo> neighbors;
    ArrayList<Message> messageLog;
    NetworkManager networkManager;
    KeyManager keyManager;
    String configFilename;

    public Node(NodeInfo myNode, ArrayList<NodeInfo> neighbors, int totalNodes) throws Exception {
        this.myNode = myNode;
        this.neighbors = neighbors;
        this.totalNodes = totalNodes;
        this.messageLog = new ArrayList<>();
        //creats and inits networking logic
        networkManager = new NetworkManager(myNode, neighbors);
        keyManager = new KeyManager(myNode, neighbors);
        io = new UserIO();
    }

    /******************************************************************
     * contains logic for main ecentricity algorithm
     * runs after all connections have been set
     ******************************************************************/
    public void go(int interRequestDelay, int csExecutionTime, int numberOfRequests) throws Exception {
        System.out.println(myNode.getDxMachine() + " is running on port: " + myNode.getPort());
        io.deleteFile(outputFilename);

        //starts the initial packet
        ArrayList<MessagePacket> firstMessages = keyManager.createRequestBoxes(0);
        networkManager.sendOff(firstMessages);
        System.out.println(myNode.getDxMachine() + " number of requests: " + numberOfRequests);

        for (int i = 0; i < numberOfRequests;)
        {
            if (CsEnter(i, csExecutionTime))
            {
                i++;
            }
            Thread.sleep(interRequestDelay);
        }

        //detect if done and keep replying and also when to terminate
        ArrayList<MessagePacket> incomingBoxes;
        do {
            incomingBoxes = networkManager.recieveAllIncoming();

            for (NodeInfo n : neighbors)
            {
                MessagePacket packet = new MessagePacket(myNode, n, numberOfRequests + 1);
                Message replyMessage = new Message(myNode, Message.REQUEST.DONE);
                packet.addMessages(replyMessage);
                networkManager.sendOff(packet);
            }
        } while (!incomingBoxes.stream().allMatch(p -> p.getRound() == numberOfRequests + 1));

        System.out.println(myNode.toString() + ":is ending");
        //waits for all other nodes to finish
        Thread.sleep(1000);
        //prints the final output
        outputOutput(computeOutput(messageLog));
        Thread.sleep(5000);
    }

    private String computeOutput(ArrayList<Message> messageLog) {
        return "default output";
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(split[0] + "_output_" + myNode.getId() + ".dat"));
            writer.write(output);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /******************************************************************
     * contains logic for entering critical section and returning keys
     ******************************************************************/
    public boolean CsEnter(int round, int csExecutionTime) throws Exception {
        System.out.println("debug:" + myNode.toString() + ":is trying to enter on round:" + round);

        ArrayList<MessagePacket> incomingBoxes = networkManager.recieveAllIncoming();
        ArrayList<MessagePacket> requestingPackets = new ArrayList<>();
        for (MessagePacket p : incomingBoxes) {
            NodeInfo incommingNode = p.getOrigin();
            if (p.getRound() > round || (p.getRound() == round && incommingNode.getId() > myNode.getId())) {
                keyManager.setPermission(incommingNode, true);
            } else {
                keyManager.setPermission(incommingNode, false);
            }
        }

        //debug
        System.out.println("debug:" + myNode.toString() + ":permissions:" + keyManager.getPermissions());

        //TODO: implement correct wait times, outout vector to clock on crit section, auto check crit output at end
        //TODO: also, refactor this code
        if (keyManager.hasCriticalPerimission())
        {
            // enter critical section
            io.appendToFile(getVectorTimestamp(incomingBoxes), outputFilename);
            System.out.println("debug:" + myNode.toString() + ":is running CRITICAL section on round:" + round);
            Thread.sleep(csExecutionTime);
            CsLeave(requestingPackets, round + 1);
            return true;
        }
        else
        {
            ArrayList<MessagePacket> outgoingBoxes = keyManager.createReplyBoxes(requestingPackets, round);
            networkManager.sendOff(outgoingBoxes);
        }
        return false;
    }
    /******************************************************************
     * contains logic for leaving critical section
     ******************************************************************/
    public void CsLeave(ArrayList<MessagePacket> requestingPackets, int round) throws Exception {
        //do special cs leave stuff here
        ArrayList<MessagePacket> outgoingBoxes = keyManager.createReplyBoxes(requestingPackets, round);
        networkManager.sendOff(outgoingBoxes);
    }

    /******************************************************************
     * returns the vector timestamp of all process critical sections
     ******************************************************************/
    private String getVectorTimestamp(ArrayList<MessagePacket> packets)
    {
        String result = myNode.toString() + " ";
        ArrayList<MessagePacket> sortedList = (ArrayList<MessagePacket>) packets.clone();
        Collections.sort(sortedList, new MessagePacket(myNode, myNode, 0));

        for (MessagePacket p : sortedList)
        {
            result += p.round + ":";
        }
        return result + "\n";
    }

}
