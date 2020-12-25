import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

/*******************************************************************
 * this class serves as a manager for keys and permissions
 * related to the critical section
 ******************************************************************/
public class KeyManager {

    private ArrayList<NodeInfo> neighbors;
    HashMap<String, Boolean> permissionKeys;
    NodeInfo myNode;

    public KeyManager(NodeInfo myNode, ArrayList<NodeInfo> neighbors) {
        this.neighbors = neighbors;
        this.permissionKeys = new HashMap<>();
        this.myNode = myNode;
        for (NodeInfo n : neighbors)
        {
            permissionKeys.put(n.toString(), false);
        }
    }

    public void setPermission(NodeInfo node, boolean key)
    {
        permissionKeys.remove(node.toString());
        permissionKeys.put(node.toString(), key);
    }

    public boolean hasCriticalPerimission()
    {
        return permissionKeys.values().stream().allMatch(p -> p==true);
    }

    public ArrayList<NodeInfo> getMissingKeys()
    {
        return (ArrayList<NodeInfo>) neighbors.stream().filter(n -> permissionKeys.get(n.toString()) == false).collect(Collectors.toList());
    }

    public String getPermissions()
    {
        String result = "";
        for (boolean b : permissionKeys.values())
        {
            result += b + ":";
        }
        return result;
    }

    /******************************************************************
     * creates request boxes
     ******************************************************************/
    public ArrayList<MessagePacket> createRequestBoxes(int round)
    {
        ArrayList<MessagePacket> outgoingBoxes = new ArrayList<>();
        for (NodeInfo n : neighbors)
        {
            MessagePacket packet = new MessagePacket(myNode, n, round);
            outgoingBoxes.add(packet);
        }

        ArrayList<NodeInfo> missingKeys = getMissingKeys();
        for (NodeInfo n : missingKeys)
        {
            for (int i = 0; i < outgoingBoxes.size(); i++)
            {
                MessagePacket p = outgoingBoxes.get(i);
                if (p.destination.equals(n))
                {
                    Message requestMessage = new Message(myNode, Message.REQUEST.SEND);
                    p.addMessages(requestMessage);
                }
            }
        }
        return outgoingBoxes;
    }
    /******************************************************************
     * creates reply boxes, gives up key only to processes above them (ideally if their timestamp is before, yet to be implemented)
     ******************************************************************/
    public ArrayList<MessagePacket> createReplyBoxes(ArrayList<MessagePacket> requestingPackets, int round) {
        //creates empty packets

        ArrayList<MessagePacket> outgoingBoxes = new ArrayList<>();
        for (NodeInfo n : neighbors)
        {
            MessagePacket packet = new MessagePacket(myNode, n, round);
            outgoingBoxes.add(packet);
        }
        //ArrayList<MessagePacket> outgoingBoxes = createRequestBoxes(round);

        for (MessagePacket p : requestingPackets) {
            NodeInfo requestingNode = p.getOrigin();
            if (p.getRound() < round || (p.getRound() == round && requestingNode.getId() < myNode.getId())) {
                setPermission(requestingNode, false);
                for (int i = 0; i < outgoingBoxes.size(); i++) {
                    MessagePacket p1 = outgoingBoxes.get(i);
                    if (p1.getDestination().getId() == requestingNode.getId()) {
                        //add reply message
                        Message replyMessage = new Message(myNode, Message.REQUEST.REPLY);
                        p1.addMessages(replyMessage);
                    }
                }
            }
        }
        
        return outgoingBoxes;
    }

    public ArrayList<MessagePacket> createEmptyBoxes(int round) {
        ArrayList<MessagePacket> outgoingBoxes = new ArrayList<>();
        for (NodeInfo n : neighbors)
        {
            MessagePacket packet = new MessagePacket(myNode, n, round);
            outgoingBoxes.add(packet);
        }
        return outgoingBoxes;
    }
}
