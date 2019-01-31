package com.nukeologist.circuitos.circuit;


import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;

/**
 * An edge in a graph-like structure.
 * Hopefully assists in calculation.
 * @author Nukeologist
 */
public class CircuitEdge {

    private CircuitNode A, B;
    private BaseTileEntity teConnector;

    public CircuitEdge(CircuitNode node1, CircuitNode node2, BaseTileEntity teConnector){
        this.A = node1;
        this.B = node2;
        this.teConnector = teConnector;
    }

    public CircuitNode getA() {
        return A;
    }

    public CircuitNode getB() {
        return B;
    }

    public BaseTileEntity getConnector() {
        return teConnector;
    }
}
