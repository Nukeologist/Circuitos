package com.nukeologist.circuitos.circuit;

import java.util.ArrayList;
import java.util.List;

public class CircuitNode {
    public int index;
    public List<CircuitEdge> connections = new ArrayList<>();

    public CircuitNode(int index){
        this.index = index;

    }
}
