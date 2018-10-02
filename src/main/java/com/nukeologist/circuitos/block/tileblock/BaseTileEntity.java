package com.nukeologist.circuitos.block.tileblock;



import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;



public abstract class BaseTileEntity extends TileEntity {


    public TileEntityBasicGenerator master;

    public void setMaster(TileEntityBasicGenerator master) {
        this.master = master;
        if(this.master == this && this instanceof TileEntityBasicGenerator) {
            ((TileEntityBasicGenerator) this).isMaster = true;
        }else {
            if(this instanceof TileEntityBasicGenerator)((TileEntityBasicGenerator) this).isMaster = false;
        }

    }

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




}
