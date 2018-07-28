package com.nukeologist.circuitos.client.gui;

import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.item.ItemStack;

public class ContainerBasicGenerator extends Container {

    private final TileEntityBasicGenerator te;

    public ContainerBasicGenerator(InventoryPlayer player, TileEntityBasicGenerator tileEntity) {
        this.te = tileEntity;

        //add slots
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.te.isUsableByPlayer(playerIn);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }


    //when stack is shift-clicked
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        return super.transferStackInSlot(playerIn, index);
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
    }


}
