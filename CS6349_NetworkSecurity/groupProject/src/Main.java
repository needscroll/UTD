import java.nio.ByteBuffer;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Hello Lain");

        try{
            TransferFile tf = new TransferFile("a message");
            ByteBuffer b = tf.toByteBuffer();
            TransferFile tf1 = TransferFile.fromByteBuffer(b);
            System.out.println(tf1.getMessage());
        } catch (Exception e)
        {
            //ignore the exception
        }

    }
}
