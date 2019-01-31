package com.nukeologist.circuitos.circuit;

import java.util.ArrayList;
import java.util.List;

public class CircuitNode {

    private int index;
    private List<CircuitEdge> connections;

    public CircuitNode(int index){
        this.index = index;
        this.connections = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public void addConnection(CircuitEdge connection) {
        this.connections.add(connection);
    }

    public List<CircuitEdge> getConnections() {
        return connections;
    }


}
