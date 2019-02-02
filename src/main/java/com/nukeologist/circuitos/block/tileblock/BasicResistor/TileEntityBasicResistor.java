package com.nukeologist.circuitos.block.tileblock.BasicResistor;

import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IResistor;

public class TileEntityBasicResistor extends BaseTileEntity implements IResistor {

    private int resistance;
    private double current;

    public TileEntityBasicResistor(){
        super();
        this.resistance = 10; //may change default later
        this.current = 0; //default
    }

    @Override
    public int getResistance() {
        return resistance;
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
    public double getCurrent() {
        return current;
    }

    @Override
    public void setCurrent(double current) {
        this.current = current;
    }
}
