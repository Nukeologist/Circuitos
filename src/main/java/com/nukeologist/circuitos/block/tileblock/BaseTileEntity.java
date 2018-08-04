package com.nukeologist.circuitos.block.tileblock;


import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.circuit.IGenerator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseTileEntity extends TileEntity {


    private boolean firstRun = true;

    public IGenerator master;
    public boolean isMaster;
    public List<BaseTileEntity> circuitBlocks = new ArrayList<>();

    protected boolean hasChanged;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }


    protected void constructMultiblock() {

    }


    public void setMaster(IGenerator master) {
        this.master = master;
        if(this.master == this) {
            this.isMaster = true;
        }else {
            this.isMaster = false;
        }

    }

    public boolean isMaster(){
        return isMaster;
    }

}
