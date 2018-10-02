package com.nukeologist.circuitos.circuit;

import java.util.List;

public class CircuitNode {
    public int index;
    public List<CircuitEdge> connections;

    public CircuitNode(int index){
        this.index = index;

    }
}
