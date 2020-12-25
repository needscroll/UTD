import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/******************************************************************
 * this class encapsulates wraps around and acts as a container
 * for message sent between nodes so they can be sent
 * as a bundle instead of individually
 ******************************************************************/
public class MessagePacket implements Serializable {

    NodeInfo destination;
    NodeInfo origin;
    ArrayList<Message> messages;

    public MessagePacket(NodeInfo origin, NodeInfo destination) {
        this.destination = destination;
        this.origin = origin;
        this.messages = new ArrayList<>();
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

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void addMessages(Message message) {
        this.messages.add(message);
    }
}
