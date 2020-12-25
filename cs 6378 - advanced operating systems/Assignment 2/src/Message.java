import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.stream.Collectors;

/******************************************************************
 * encapsulation class for messages sent between nodes
 ******************************************************************/
public class Message implements Serializable {

    private int roundNumber;
    private boolean isFluff;
    private NodeInfo startingNode;
    private HashMap<Integer, NodeInfo> historyPathMap;
    private ArrayList<NodeInfo> historyPath;

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Message(NodeInfo startingNode, boolean isFluff) {
        this.roundNumber = 0;
        this.isFluff = isFluff;
        this.startingNode = startingNode;

        this.historyPath = new ArrayList<>();
        this.historyPathMap = new HashMap<>();
        historyPathMap.put(roundNumber, startingNode);
        historyPath.add(startingNode);
    }

    public ByteBuffer toByteBuffer() throws Exception
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.flush();

        ByteBuffer buf = ByteBuffer.allocate((bos.size()));
        buf.put(bos.toByteArray());

        oos.close();
        bos.close();

        buf.flip();
        return buf;
    }

    public static Message fromByteBuffer(ByteBuffer buf) throws Exception
    {
        buf.flip();
        byte[] data = new byte[buf.limit()];
        buf.get(data);
        buf.clear();

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Message msg = (Message) ois.readObject();

        return msg;
    }

    public void incrementMessage(NodeInfo node)
    {
        roundNumber++;
        historyPath.add(node);
        historyPathMap.put(roundNumber, node);
        //System.out.println("NOTICE ME: I am incrementing:" + startingNode.toString());
    }

    public NodeInfo getLastSender()
    {
        return historyPath.get(historyPath.size() - 1);
    }

    public String getHistoryPathMapString()
    {
        String path = "";
        for (int i = 0; i < historyPathMap.size(); i++)
        {
            NodeInfo node = historyPath.get(i);
            path += i + node.toString();

        }
        return path;
    }

    public boolean encountered(NodeInfo node)
    {
        for (NodeInfo n : historyPath)
        {
            if (n.equals(node))
            {
                return true;
            }
        }
        return false;
    }

    public String getHistoryPathString()
    {
        //ArrayList<NodeInfo> historyClone = (ArrayList<NodeInfo>) historyPath.clone();
        //Collections.reverse(historyClone);
        String path = "";
        for (int i = 0; i < historyPath.size(); i++)
        {
            NodeInfo node = historyPath.get(i);
            path += i + node.toString();
        }
        return path;
    }

    public boolean hasVisited(NodeInfo nodeInfo)
    {
        for (NodeInfo n : historyPath)
        {
            if (n.equals(nodeInfo))
            {
                return true;
            }
        }
        return false;
    }

    /******************************************************************
     * prunes last node on history path
     * this is needed because sometimes the reciever will append the
     * message at the last round
     ******************************************************************/
    public void prune()
    {
        NodeInfo last = getLastSender();
        historyPathMap.remove(historyPath.size() - 1);
        historyPath.remove(last);
    }

    public int getLength()
    {
        return historyPath.size() - 1;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public boolean isFluff() {
        return isFluff;
    }

    public NodeInfo getStartingNode() {
        return startingNode;
    }

    public ArrayList<NodeInfo> getHistoryPath() {
        return historyPath;
    }


}
