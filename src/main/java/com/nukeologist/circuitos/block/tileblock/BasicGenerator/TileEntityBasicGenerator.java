package com.nukeologist.circuitos.block.tileblock.BasicGenerator;


import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.block.tileblock.BasicWire.TileEntityBasicWire;
import com.nukeologist.circuitos.circuit.*;
import com.nukeologist.circuitos.network.CircuitosPacketHandler;
import com.nukeologist.circuitos.network.PacketGeneratorGUIUpdate;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
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
    private ItemStackHandler inventory = new ItemStackHandler(2);
    private CircuitGraph graph = new CircuitGraph();
    private Stack<TileEntityBasicWire> wiresToLook = new Stack<>();
    private List<BaseTileEntity> tempMachineList = new ArrayList<>();

    public List<BaseTileEntity> machineList = new ArrayList<>();
    public List<BaseTileEntity> allCircuitList = new ArrayList<>();

    protected int index = 0;

    public boolean isMaster;

    public boolean analyzing, readyToWork;

    @Override
    public void update() {
        if(world.getTotalWorldTime() % 80 == 0 && !(world.isRemote) && this.analyzing) {
            LogHelper.logInfo("analyzing");
            this.setMaster(this);
            this.analyzing = false;
            this.analyzed = true;
            this.graph.nodes.add(new CircuitNode(index));
            BaseTileEntity tileEntity;
            canAnalyze(this, index);
            doIt(this);

            while(!tempMachineList.isEmpty()) {
                machineList.addAll(tempMachineList);

                tempMachineList.clear();

                for(BaseTileEntity machine : machineList){
                    this.graph.nodes.add(new CircuitNode(newIndex()));
                    canAnalyze(machine, index);
                    doIt(machine);
                }

            }
            CircuitosPacketHandler.INSTANCE.sendToServer(new PacketGeneratorGUIUpdate(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(),"!analyzing"));

        }

        if(!this.analyzing && world.getTotalWorldTime() % 80 == 0 && !world.isRemote){
            LogHelper.logInfo("Not analyzing: ");
            LogHelper.logInfo(machineList.toString());
            LogHelper.logInfo("" + machineList.size());
            LogHelper.logInfo(allCircuitList.toString());
            LogHelper.logInfo("" + allCircuitList.size());
            LogHelper.logInfo(""+ this.graph.toString());
        }
    }

    private void doIt(BaseTileEntity tileEntity){
        while(!wiresToLook.empty()) {

            tileEntity = wiresToLook.pop();
            canAnalyze(tileEntity, index);


        }
    }

    private void canAnalyze(BaseTileEntity te, int nodeIndex){

        for(EnumFacing facing : EnumFacing.VALUES){
            BlockPos newPos = te.getPos().offset(facing);
            TileEntity teToAnalyze = world.getTileEntity(newPos);
            if(teToAnalyze instanceof TileEntityBasicWire){
                if(isPartOfCircuit(te, (TileEntityBasicWire) teToAnalyze , facing.getName() ) && !((TileEntityBasicWire) teToAnalyze).analyzed){
                    wiresToLook.push((TileEntityBasicWire) teToAnalyze);
                    ((TileEntityBasicWire) teToAnalyze).analyzed = true;
                    ((TileEntityBasicWire) teToAnalyze).setMaster(this);
                    ((TileEntityBasicWire) teToAnalyze).nodeIndex = index;
                    this.allCircuitList.add((BaseTileEntity) teToAnalyze);


                }
            }else if(teToAnalyze instanceof BaseTileEntity && !(teToAnalyze instanceof TileEntityBasicWire) && !(((BaseTileEntity) teToAnalyze).analyzed)){
                if(isPartOfCircuit(te, (BaseTileEntity) teToAnalyze, facing.getName()) && !(((BaseTileEntity) teToAnalyze).analyzed)){
                    ((BaseTileEntity) teToAnalyze).analyzed = true;
                    ((BaseTileEntity) teToAnalyze).setMaster(this);
                    this.graph.nodes.get(nodeIndex).connections.add(new CircuitEdge( te, (BaseTileEntity)teToAnalyze));
                    this.allCircuitList.add((BaseTileEntity) teToAnalyze);
                    this.tempMachineList.add((BaseTileEntity) teToAnalyze);


                }
            }

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
        return ++index ;
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