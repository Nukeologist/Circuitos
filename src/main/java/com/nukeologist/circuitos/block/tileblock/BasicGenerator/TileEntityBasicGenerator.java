package com.nukeologist.circuitos.block.tileblock.BasicGenerator;


import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.*;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;


public class TileEntityBasicGenerator extends BaseTileEntity implements ITickable, IGenerator {

    private int resistance, fem;
    private HashMap<BlockPos, EnumFacing> connected = new HashMap<>();
    private ItemStackHandler inventory = new ItemStackHandler(2);
    private CircuitGraph graph;
    //TODO: PRECISA REFAZER TODO O SISTEMA DE ACHAR TILE ENTITIES, A PARTIR DAS FACES DA TILE ORIGINAL....
    protected static int index = 0;

    public boolean isMaster;

    public boolean analyzing, readyToWork;

    @Override
    public void update() {
        if(world.getTotalWorldTime() % 80 == 0 && !(world.isRemote) && this.analyzing) {
            LogHelper.logInfo("analyzing");
            this.analyzing = false;



        }
    }





    public boolean isMaster(){
        return isMaster;
    }

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

    private int newIndex(){
        return index ++;
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
