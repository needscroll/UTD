import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;

/******************************************************************
 * this class encapsulates wraps around and acts as a container
 * for message sent between nodes so they can be sent
 * as a bundle instead of individually
 ******************************************************************/
public class MessagePacket implements Serializable, Comparator<MessagePacket> {

    NodeInfo destination;
    NodeInfo origin;
    ArrayList<Message> messages;
    int round;

    public MessagePacket(NodeInfo origin, NodeInfo destination, int round) {
        this.destination = destination;
        this.origin = origin;
        this.messages = new ArrayList<>();
        this.round = round;
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

    public NodeInfo getDestination() {
        return destination;
    }

    public static MessagePacket fromByteBuffer(ByteBuffer buf) throws Exception
    {
        buf.flip();
        byte[] data = new byte[buf.limit()];
        buf.get(data);
        buf.clear();

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        MessagePacket msg = (MessagePacket) ois.readObject();

        return msg;
    }

    public boolean isEmpty()
    {
        return messages.size() < 1;
    }

    public NodeInfo getOrigin() {
        return origin;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessages(Message message) {
        this.messages.add(message);
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    @Override
    public int compare(MessagePacket o1, MessagePacket o2) {
        return o1.getOrigin().getId() - o2.getOrigin().getId();
    }
}
