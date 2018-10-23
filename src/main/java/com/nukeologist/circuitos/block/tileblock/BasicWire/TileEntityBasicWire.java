package com.nukeologist.circuitos.block.tileblock.BasicWire;

import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IResistor;



public class TileEntityBasicWire extends BaseTileEntity implements IResistor {

    private int resistance;
    public int nodeIndex;



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
}
