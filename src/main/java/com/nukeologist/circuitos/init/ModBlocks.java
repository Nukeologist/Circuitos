package com.nukeologist.circuitos.init;

import com.nukeologist.circuitos.block.tileblock.BasicResistor.BlockBasicResistor;
import com.nukeologist.circuitos.block.tileblock.BasicWire.BlockBasicWire;
import com.nukeologist.circuitos.block.tileblock.BasicGenerator.BlockBasicGenerator;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    @GameRegistry.ObjectHolder("circuitos:basicgenerator")
    public static BlockBasicGenerator basicGenerator;

    @GameRegistry.ObjectHolder("circuitos:basicwire")
    public static BlockBasicWire basicWire;

    @GameRegistry.ObjectHolder("circuitos:basicresistor")
    public static BlockBasicResistor basicResistor;


    public static void registerModels() {

        basicGenerator.registerItemModel(Item.getItemFromBlock(basicGenerator));
        basicWire.registerItemModel(Item.getItemFromBlock(basicWire));
        basicResistor.registerItemModel(Item.getItemFromBlock(basicResistor));
    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        basicWire.initModel();
        basicGenerator.initModel();
        //basicResistor.initModel();
    }

    @SideOnly(Side.CLIENT)
    public static void initItemModels() {
        basicWire.initItemModel();
    }
}
