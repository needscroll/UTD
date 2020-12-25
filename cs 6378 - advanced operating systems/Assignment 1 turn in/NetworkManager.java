import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/******************************************************************
 * this class manages networking logic for the node
 ******************************************************************/
public class NetworkManager {

    final static int BUF_SIZE = 14096;
    ByteBuffer buf;
    ArrayList<NodeInfo> neighborNodes;
    ArrayList<SctpChannel> clientChannels; //recieve channels
    ArrayList<SctpChannel> serverChannels; //send channels
    HashMap<NodeInfo, SctpChannel> sendChannelMap;
    HashMap<NodeInfo, SctpChannel> recieveChannelMap;
    NodeInfo myNode;

    /******************************************************************
     * initializes all network connections
     ******************************************************************/
    public NetworkManager(NodeInfo myNode, ArrayList<NodeInfo> neighborNodes) throws Exception {
        this.myNode = myNode;
        this.neighborNodes = neighborNodes;
        clientChannels = new ArrayList<>();
        serverChannels = new ArrayList<>();
        sendChannelMap = new HashMap<>();
        recieveChannelMap = new HashMap<>();
        buf = ByteBuffer.allocateDirect(BUF_SIZE);

        RunnableStartServer server = new RunnableStartServer();
        server.start();
        RunableStartClient client = new RunableStartClient();
        client.start();

        server.t.join();
        client.t.join();

        System.out.println("debug:" + myNode.getDxMachine() + ":number of client channels:" + clientChannels.size());
        System.out.println("debug:" + myNode.getDxMachine() + ":number of server channels:" + serverChannels.size());

        createChannelMap();
    }

    /******************************************************************
     * sends a message to a specific channel
     ******************************************************************/
    /*
    public void sendTarget(NodeInfo targetNode, MessagePacket message) throws Exception {
        //System.out.println("debug:" + myNode.getDxMachine() + ":send hashmap size:" + sendChannelMap.size());
        //the only reason why I have to manually iterate through the keys is because java thinks two objects
        //with the same properties are seperate keys
        Set<NodeInfo> keySet = sendChannelMap.keySet();
        for (NodeInfo n : keySet)
        {
            //System.out.println("debug:" + myNode.getDxMachine() + ":n string:" + n.toString());
            //System.out.println("debug:" + myNode.getDxMachine() + ":target string:" + targetNode.toString());

            if (n.equals(targetNode))
            {

            }
        }
        System.out.println("ERROR:" + myNode.getDxMachine() + ":could not get target node to send to");
        System.out.println("ERROR:" + myNode.getDxMachine() + ":trying to send to:" + targetNode.toString());
    }
     */

    /******************************************************************
     * sends a packet to its destination node
     ******************************************************************/
    public void sendOff(MessagePacket messagePacket) throws Exception
    {
        Set<NodeInfo> keySet = sendChannelMap.keySet();
        for (NodeInfo n : keySet)
        {
            if (n.equals(messagePacket.destination))
            {
                SctpChannel sc = sendChannelMap.get(n);
                MessageInfo messageInfo = MessageInfo.createOutgoing(null, 0);
                sc.send(messagePacket.toByteBuffer(), messageInfo);
                //System.out.println("debug:" + myNode.getDxMachine() + ":message sent to:" + targetNode.toString());

                return;
            }
        }
    }

    /******************************************************************
     * recieves all messages waiting on inbound channels
     ******************************************************************/
    public ArrayList<MessagePacket> recieveAllIncoming() throws Exception {
        ArrayList<MessagePacket> recievedMessages = new ArrayList<>();

        for (int i = 0; i < clientChannels.size(); i++)
        {
            SctpChannel sc = clientChannels.get(i);
            sc.receive(buf, null, null);
            MessagePacket recievedMessage = MessagePacket.fromByteBuffer(buf);
            recievedMessages.add(recievedMessage);
            buf.clear();
        }
        return recievedMessages;
    }

    /******************************************************************
     * sends a blank message to tell which chanels belong to which nodes
     * waits for messages to come in from other nodes and links
     * sc channels to node information
     ******************************************************************/
    private void createChannelMap() throws Exception {

        System.out.println("debug:" + myNode.getDxMachine() + ":attempting to create channel map");

        Message messageZero = new Message(myNode, false);
        messageZero.setRoundNumber(-9);
        for (int i = 0; i < clientChannels.size(); i++)
        {
            SctpChannel sc = clientChannels.get(i);
            MessageInfo messageInfo = MessageInfo.createOutgoing(null, 0);
            sc.send(messageZero.toByteBuffer(), messageInfo);
        }
        System.out.println("debug:" + myNode.getDxMachine() + ":identifying client messages sent, waiting 5 second for reply");
        Thread.sleep(5000);

        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 0; i < serverChannels.size(); i++)
        {
            SctpChannel sc = serverChannels.get(i);
            sc.receive(buf, null, null);
            Message recievedMessage = Message.fromByteBuffer(buf);
            messages.add(recievedMessage);
            buf.clear();
        }

        //filters for only -9 messages
        if ((messages.size() > 0) && messages.stream().allMatch(m -> m.getRoundNumber() == -9))
        {
            for (int i = 0; i < messages.size(); i++)
            {
                Message m = messages.get(i);
                SctpChannel sc = serverChannels.get(i);
                System.out.println("debug:" + myNode.getDxMachine() + ":processing message from:" + m.getStartingNode().getDxMachine());
                sendChannelMap.put(m.getStartingNode(), sc);
            }
            System.out.println("debug:" + myNode.getDxMachine() + ":channel map sucssesfully created");
        }
        else
        {
            System.out.println("ERROR: something went wrong with creating channel maps");
        }
        Thread.sleep(5000);
    }

    /******************************************************************
     * initializes all server network connections
     ******************************************************************/
    private class RunableStartClient implements Runnable
    {
        Thread t;
        @Override
        public void run() {
            //System.out.println("Running thread:" + myNode.getDxMachine()+":isServer:"+myNode.isServer());
            ArrayList<SctpChannel> connectedChannels = new ArrayList<>();
            try
            {
                System.out.println("debug:" + myNode.getDxMachine() + ":initing client waiting 1 seconds for server startup");
                Thread.sleep(5000);
                System.out.println("NEIGHBOR NODES SIZE: " + neighborNodes.size());
                for (NodeInfo n : neighborNodes)
                {
                    System.out.println("debug:" + myNode.getDxMachine() + ":trying to connect:"+n.getDxMachine()+":"+n.getPort());
                    //change to localhost for local testing
                    InetSocketAddress nodeAddr = new InetSocketAddress(n.getDxMachine(), n.getPort());
                    //InetSocketAddress nodeAddr = new InetSocketAddress("localhost", n.getPort());
                    SctpChannel sc = SctpChannel.open(nodeAddr, 0, 0);
                    connectedChannels.add(sc);
                }
                clientChannels = connectedChannels;
                System.out.println("debug:" + myNode.getDxMachine() + ":all connections made");

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void start () {
            System.out.println("Starting Server Thread:" + myNode.getDxMachine());
            if (t == null) {
                t = new Thread (this);
                t.start ();
            }
        }

    }

    /******************************************************************
     * initializes all client network connections
     ******************************************************************/
    private class RunnableStartServer implements Runnable
    {
        Thread t;
        @Override
        public void run() {
            try
            {
                //sets up server for this node
                System.out.println("debug:" + myNode.getDxMachine() + ":initing basic server to send things");
                SctpServerChannel ssc = SctpServerChannel.open();
                InetSocketAddress serverAddr = new InetSocketAddress(myNode.getPort());
                ssc.bind(serverAddr);
                System.out.println("debug:" + myNode.getDxMachine() + ":created basic server setup, waiting on connection");
                System.out.println("debug:" + myNode.getDxMachine() + ":details:"+ myNode.getDxMachine() + ":" + myNode.getPort());

                for (int i = 0; i < neighborNodes.size(); i++)
                {
                    SctpChannel sc = ssc.accept();
                    serverChannels.add(sc);
                    System.out.println("debug:" + myNode.getDxMachine() + ":connection accepted");
                }
                System.out.println("debug:" + myNode.getDxMachine() + ":all connections made");
            } catch (Exception e)
            {
                System.out.println("ERROR: something wrong with server thread");
                e.printStackTrace();
            }
        }

        public void start () {
            System.out.println("Starting Server Thread:" + myNode.getDxMachine());
            if (t == null) {
                t = new Thread (this);
                t.start ();
            }
        }
    }
}
