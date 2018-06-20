package com.nukeologist.circuitos.init;

import com.nukeologist.circuitos.block.tileblock.BasicWire.BlockBasicWire;
import com.nukeologist.circuitos.block.tileblock.BasicGenerator.BlockBasicGenerator;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {

    @GameRegistry.ObjectHolder("circuitos:basicgenerator")
    public static BlockBasicGenerator basicGenerator;

    @GameRegistry.ObjectHolder("circuitos:basicwire")
    public static BlockBasicWire basicWire;

    public static void registerModels() {

        basicGenerator.registerItemModel(Item.getItemFromBlock(basicGenerator));
        basicWire.registerItemModel(Item.getItemFromBlock(basicWire));
    }
}
