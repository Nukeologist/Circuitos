package com.nukeologist.circuitos.block.tileblock.BasicResistor;

import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IResistor;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBasicResistor extends BaseTileEntity implements IResistor {

    private int resistance;
    private double current, dissipatedEnergy;

    public TileEntityBasicResistor(){
        super();
        this.resistance = 10; //may change default later
        this.current = 0; //default
        this.dissipatedEnergy = 0;
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
    public double getDissipatedEnergy() {
        return dissipatedEnergy;
    }

    @Override
    public double getCurrent() {
        return current;
    }

    @Override
    public void setCurrent(double current) {
        this.current = current;
        // In a second time usually.
        this.dissipatedEnergy = current * current * resistance;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound ret = super.writeToNBT(compound);
        ret.setDouble("current", this.current);
        ret.setDouble("dissipatedEnergy", this.dissipatedEnergy);
        return ret;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.current = compound.getDouble("current");
        this.dissipatedEnergy = compound.getDouble("dissipatedEnergy");

    }
}
