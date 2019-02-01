package com.nukeologist.circuitos.block.tileblock.BasicWire;

import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IResistor;



public class TileEntityBasicWire extends BaseTileEntity implements IResistor {

    private int resistance;
    private int nodeIndex;



    public TileEntityBasicWire() {
        this.setResistance(1);
    }

    @Override
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    @Override
    public int getDissipatedEnergy() {
        return 0;
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
        return 0; // for now
    }
}
