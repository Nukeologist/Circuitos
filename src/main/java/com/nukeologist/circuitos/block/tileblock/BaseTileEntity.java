package com.nukeologist.circuitos.block.tileblock;



import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.block.tileblock.BasicResistor.TileEntityBasicResistor;
import com.nukeologist.circuitos.block.tileblock.BasicWire.BlockBasicWire;
import com.nukeologist.circuitos.block.tileblock.BasicWire.TileEntityBasicWire;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;



public abstract class BaseTileEntity extends TileEntity {


    public TileEntityBasicGenerator master;
    public boolean analyzed = false;

    public void setMaster(TileEntityBasicGenerator master) {
        this.master = master;
        if(this.master == this && this instanceof TileEntityBasicGenerator) {
            ((TileEntityBasicGenerator) this).isMaster = true;
        }else if(this instanceof TileEntityBasicGenerator){
            ((TileEntityBasicGenerator) this).isMaster = false;

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


    public boolean isPartOfCircuit(BaseTileEntity firstTE, BaseTileEntity secondTE, String enumPos){

        if(firstTE instanceof TileEntityBasicWire && secondTE instanceof TileEntityBasicWire){
            return true;
        }else if(firstTE instanceof TileEntityBasicGenerator && secondTE instanceof TileEntityBasicWire){
            Block block = world.getBlockState(secondTE.pos).getBlock();
            BlockBasicWire wire;
            if(block instanceof BlockBasicWire){
                wire = (BlockBasicWire) block;
                if(wire.isConnectable(world, firstTE.pos, enumPos)){
                    return true;
                }
            }
        }else if(firstTE instanceof TileEntityBasicWire && secondTE instanceof TileEntityBasicGenerator){
            Block block = world.getBlockState(firstTE.pos).getBlock();
            BlockBasicWire wire;
            if(block instanceof BlockBasicWire){
                wire = (BlockBasicWire) block;
                if(wire.isConnectable(world, secondTE.pos, enumPos)){
                    return true;
                }
            }//now for Resistor:
        }else if(firstTE instanceof TileEntityBasicWire && secondTE instanceof TileEntityBasicResistor){
            Block block = world.getBlockState(firstTE.pos).getBlock();
            BlockBasicWire wire;
            if(block instanceof BlockBasicWire){
                wire = (BlockBasicWire) block;
                if(wire.isConnectable(world, secondTE.pos, enumPos)){
                    return true;
                }
            }
        }else if(firstTE instanceof TileEntityBasicResistor && secondTE instanceof TileEntityBasicWire){
            Block block = world.getBlockState(secondTE.pos).getBlock();
            BlockBasicWire wire;
            if(block instanceof BlockBasicWire){
                wire = (BlockBasicWire) block;
                if(wire.isConnectable(world, firstTE.pos, enumPos)){
                    return true;
                }
            }
        }

        return false;
    }


}
