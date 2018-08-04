package com.nukeologist.circuitos.block.tileblock.BasicGenerator;



import com.nukeologist.circuitos.Config;
import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IGenerator;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;


public class TileEntityBasicGenerator extends BaseTileEntity implements ITickable, IGenerator {

    private int resistance;
    private int fem;
    public boolean analyzing;
    public boolean readyToWork;

    private ItemStackHandler inventory = new ItemStackHandler(2);


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));



    }



    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

        NBTTagCompound ret = super.writeToNBT(compound);


        ret.setTag("inventory", this.inventory.serializeNBT());



        return ret;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inventory) : super.getCapability(capability, facing);
    }

    public TileEntityBasicGenerator() {
        super();
        this.setFem(150);
        this.setResistance(10);
        this.analyzing = false;
        this.readyToWork = false;
    }




    @Override
    public void update() {
        if(world.getTotalWorldTime() % 80 == 0 && !(world.isRemote) && Config.debug && this.analyzing) {
            LogHelper.logInfo("analyzing");

        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    protected void constructMultiblock() {
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





    //section to sync the client
    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        SPacketUpdateTileEntity packet = super.getUpdatePacket();

        NBTTagCompound tag = packet !=null ? packet.getNbtCompound(): new NBTTagCompound();
        tag.setBoolean("analyze", this.analyzing);

        return new SPacketUpdateTileEntity(pos, 1, tag );

    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag =pkt.getNbtCompound();
        this.analyzing = tag.getBoolean("analyze");
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return super.getUpdateTag();
    }



}
