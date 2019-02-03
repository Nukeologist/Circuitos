package com.nukeologist.circuitos.block.tileblock.BasicWire;

import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IResistor;



public class TileEntityBasicWire extends BaseTileEntity implements IResistor {

    private int resistance;
    private int nodeIndex;
    private double current;



    public TileEntityBasicWire() {
        this.setResistance(1);
        this.current = 0; //default
    }

    @Override
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    @Override
    public double getDissipatedEnergy() {
        return 0; //This may change
    }

    @Override
    public int getResistance() {
        return resistance;
    }

    public int getNodeIndex() {
        return nodeIndex;
    }

    public void setNodeIndex(int nodeIndex) {
        this.nodeIndex = nodeIndex;
    }

    @Override
    public double getCurrent() {
        return current; // for now
    }

    @Override
    public void setCurrent(double current) {
        this.current = current;
    }
}
