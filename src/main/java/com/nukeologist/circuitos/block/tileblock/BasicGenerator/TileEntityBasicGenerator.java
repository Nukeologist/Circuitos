package com.nukeologist.circuitos.block.tileblock.BasicGenerator;



import com.nukeologist.circuitos.Config;
import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IGenerator;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nullable;


public class TileEntityBasicGenerator extends BaseTileEntity implements ITickable, IGenerator, IInventory {

    private int resistance;
    private int fem;
    public boolean analyzing;
    public boolean readyToWork;

    private String customName;

    private NonNullList<ItemStack> inventory = NonNullList.withSize(2, ItemStack.EMPTY); //size: slots of inv without counting player

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.inventory);


        if(compound.hasKey("CustomName", 8)) this.setCustomName(compound.getString("CustomName"));
    }



    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {



        super.writeToNBT(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory);

        if(this.hasCustomName()) compound.setString("CustomName", this.customName);

        return compound;
    }

    public TileEntityBasicGenerator() {
        super();
        this.setFem(150);
        this.setResistance(10);
        this.analyzing = false;
        this.readyToWork = false;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack stack : this.inventory){

            if(!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.inventory.get(index );
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.inventory, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.inventory, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.inventory.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.inventory.set(index, stack);

        if(stack.getCount() > this.getInventoryStackLimit()) stack.setCount(this.getInventoryStackLimit());
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }//this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        //if(index == 3) return false;  //if it is output
        return true; //for now
    }



    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "circuitos.container.basic_generator";
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    @Nullable
    @Override
    public ITextComponent getDisplayName() {
        return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
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


    public void constructMultiblock() {

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


    //TODO: sync client (after the packet, the server knows and the client doesnt)



    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        SPacketUpdateTileEntity packet = super.getUpdatePacket();

        NBTTagCompound tag = packet !=null ? packet.getNbtCompound(): new NBTTagCompound();
        tag.setBoolean("analyze", this.analyzing);

        return new SPacketUpdateTileEntity(pos, 1, tag );
        //return super.getUpdatePacket();
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
