package com.nukeologist.circuitos.block.tileblock;


import com.nukeologist.circuitos.circuit.IGenerator;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.List;


public abstract class BaseTileEntity extends TileEntity {


    private boolean firstRun = true;

    public IGenerator master;
    public boolean isMaster;
    public List<BaseTileEntity> circuitBlocks;

    protected boolean hasChanged;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    public void constructMultiblock() {
        if(master == null) {
            for(EnumFacing d: EnumFacing.VALUES) {
                TileEntity te = world.getTileEntity(new BlockPos(this.pos.getX() + d.getFrontOffsetX(), this.pos.getY() + d.getFrontOffsetY(), this.pos.getZ() + d.getFrontOffsetZ()));
                if(te instanceof BaseTileEntity && ((BaseTileEntity) te).master != null) {
                    this.setMaster(((BaseTileEntity) te).master);
                    if(master instanceof BaseTileEntity) {
                        ((BaseTileEntity) master).circuitBlocks.add(this);
                    }
                    return;
                }
            }
        }
    }



    public void setMaster(IGenerator master) {
        this.master = master;
        if(this.master == this) {
            this.isMaster = true;
        }else {
            this.isMaster = false;
        }

      //  for(EnumFacing d: EnumFacing.VALUES) {
       //     TileEntity te = world.getTileEntity(new BlockPos(this.pos.getX() + d.getFrontOffsetX(), this.pos.getY() + d.getFrontOffsetY(), this.pos.getZ() + d.getFrontOffsetZ()));
       //}
        //shove all the info to the master
    }

    public boolean isMaster(){
        return isMaster;
    }

}
