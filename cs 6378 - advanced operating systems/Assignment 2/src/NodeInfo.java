import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/******************************************************************
 * this class is just a data class for node information
 * this class exists to prevent circular dependency in nodes class
 ******************************************************************/
public class NodeInfo implements Serializable {

    private int id;
    private String dxMachine;
    private int port;

    public NodeInfo(int id, String dxMachine, int port) {
        this.id = id;
        this.dxMachine = dxMachine;
        this.port = port;
    }

    public NodeInfo(String info)
    {
        String[] split = info.split(":");
        this.id = Integer.valueOf(split[0]);
        this.dxMachine = split[1];
        this.port = Integer.valueOf(split[2]);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDxMachine() {
        return dxMachine;
    }

    public void setDxMachine(String dxMachine) {
        this.dxMachine = dxMachine;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "<" + getId() + ":" + getDxMachine() + ":" + getPort() + ">";
    }

    public boolean equals(NodeInfo node) {
        return node.toString().contentEquals(toString());
    }
}
