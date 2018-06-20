package com.nukeologist.circuitos.block.tileblock.BasicGenerator;



import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IGenerator;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;



public class TileEntityBasicGenerator extends BaseTileEntity implements ITickable, IGenerator {

    private int resistance;
    private int fem;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
    }



    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    public TileEntityBasicGenerator() {
        super();
        this.setFem(150);
        this.setResistance(10);
    }




    @Override
    public void update() {
        if(world.getTotalWorldTime() % 80 == 0 && !(world.isRemote)) {
            LogHelper.logInfo("ticked");
            if(this.master == null) {
                constructMultiblock();
            }
            if(this.isMaster()) { //&& this.hasChanged
                LogHelper.logInfo("I AM THE MASTAH, " + this.getPos());
                //recaculate circuit nodes and voltages, dissipated energy, etc
            }
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public void constructMultiblock() {
        super.constructMultiblock();
    }

    @Override
    public void setFem(int fem) {
        this.fem = fem;
    }

    @Override
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    @Override
    public int getResistance() {
        return resistance;
    }

    @Override
    public int getFem() {
        return fem;
    }

    @Override
    public int getDissipatedEnergy() {
        return 0;
    }
}
