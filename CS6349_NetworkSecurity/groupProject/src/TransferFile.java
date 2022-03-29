import java.io.*;
import java.nio.ByteBuffer;

public class TransferFile implements Serializable {

    String message;

    public TransferFile(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ByteBuffer toByteBuffer() throws Exception
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        oos.writeObject(null);
        oos.flush();

        ByteBuffer buf = ByteBuffer.allocate((bos.size()));
        buf.put(bos.toByteArray());

        oos.close();
        bos.close();

        buf.flip();
        return buf;
    }

    public static TransferFile fromByteBuffer(ByteBuffer buf) throws Exception
    {
        buf.flip();
        byte[] data = new byte[buf.limit()];
        buf.get(data);
        buf.clear();

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);
        TransferFile msg = (TransferFile) ois.readObject();

        return msg;
    }
}
