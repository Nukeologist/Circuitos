package com.nukeologist.circuitos.circuit;

import java.util.ArrayList;
import java.util.List;

public class CircuitGraph {

    private List<CircuitNode> nodes;

    public CircuitGraph(){
        nodes = new ArrayList<>();
    }

    public List<CircuitNode> getNodes() {
        return nodes;
    }

    public void addNode(CircuitNode node) {
        this.nodes.add(node);
    }

    public CircuitNode getNodeByIndex (int index) {

        for (CircuitNode node : nodes)
            if (node.getIndex() == index) return node;
        //Didnt find a node with index, as such it is NULL
        return null;
    }
}
