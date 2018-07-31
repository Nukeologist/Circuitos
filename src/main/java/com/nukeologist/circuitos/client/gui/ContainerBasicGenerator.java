package com.nukeologist.circuitos.client.gui;

import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerBasicGenerator extends Container {

    private final TileEntityBasicGenerator te;

    public ContainerBasicGenerator(InventoryPlayer player, TileEntityBasicGenerator tileEntity) {
        this.te = tileEntity;

        //add slots
        this.addSlotToContainer(new Slot(tileEntity, 0, 8, 22  ));
        this.addSlotToContainer(new Slot(tileEntity, 1, 8, 55  ));


        for(int y = 0; y<3; y++){
            for(int x = 0; x<9; x++){
                this.addSlotToContainer(new Slot(player, x+y *9 + 9, 8+ x * 18, 84 + y*18 ));

            }

        }

        for(int x = 0; x<9; x++ ) {
            this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));

        }



    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.te.isUsableByPlayer(playerIn);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.te);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }


    //when stack is shift-clicked
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public void updateProgressBar(int id, int data) {
        //super.updateProgressBar(id, data);
        this.te.setField(id, data);
    }


}
