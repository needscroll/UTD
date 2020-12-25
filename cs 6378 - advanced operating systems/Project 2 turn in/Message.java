import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

/******************************************************************
 * encapsulation class for messages sent between nodes
 ******************************************************************/
public class Message implements Serializable {

    enum REQUEST {SEND, REPLY, DONE};

    private int roundNumber;
    private boolean isFluff;
    private NodeInfo startingNode;
    private HashMap<Integer, NodeInfo> historyPathMap;
    private ArrayList<NodeInfo> historyPath;
    REQUEST messageType;

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Message(NodeInfo startingNode, REQUEST messageType) {
        this.startingNode = startingNode;
        this.messageType = messageType;
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

    public NodeInfo getStartingNode() {
        return startingNode;
    }

    public REQUEST getMessageType() {
        return messageType;
    }

}
