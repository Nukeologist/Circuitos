package com.nukeologist.circuitos.handler;

import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.client.gui.ContainerBasicGenerator;
import com.nukeologist.circuitos.client.gui.GUIBasicGenerator;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GUIHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Reference.GUI_BASIC_GENERATOR) return new ContainerBasicGenerator(player.inventory, (TileEntityBasicGenerator)world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID == Reference.GUI_BASIC_GENERATOR) return new GUIBasicGenerator(player.inventory, (TileEntityBasicGenerator)world.getTileEntity(new BlockPos(x, y, z)));
        return null;
    }
}
