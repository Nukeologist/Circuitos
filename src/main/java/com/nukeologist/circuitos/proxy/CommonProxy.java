package com.nukeologist.circuitos.proxy;

import com.nukeologist.circuitos.Config;
import com.nukeologist.circuitos.block.tileblock.BasicResistor.BlockBasicResistor;
import com.nukeologist.circuitos.block.tileblock.BasicResistor.TileEntityBasicResistor;
import com.nukeologist.circuitos.block.tileblock.BasicWire.BlockBasicWire;
import com.nukeologist.circuitos.block.tileblock.BasicGenerator.BlockBasicGenerator;
import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.block.tileblock.BasicWire.TileEntityBasicWire;
import com.nukeologist.circuitos.init.ModBlocks;
import com.nukeologist.circuitos.item.ItemCreativeMultimeter;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {

        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "circuitos.cfg"));
        Config.readConfig();

    }

    public void registerItemRenderer(Item item, int meta, String id) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        //Blocks
        event.getRegistry().register(new BlockBasicGenerator("basicgenerator"));
        event.getRegistry().register(new BlockBasicWire("basicwire"));
        event.getRegistry().register(new BlockBasicResistor("basicresistor"));


        //Tile Entities
        TileEntity.register("circuitos:basicgenerator", TileEntityBasicGenerator.class);
        TileEntity.register("circuitos:basicwire", TileEntityBasicWire.class);
        TileEntity.register("circuitos:basicresistor", TileEntityBasicResistor.class);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        //Items
        event.getRegistry().register(new ItemCreativeMultimeter("creativemultimeter"));

        //ItemBlocks
        event.getRegistry().register(new ItemBlock(ModBlocks.basicGenerator).setRegistryName(ModBlocks.basicGenerator.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.basicWire).setRegistryName(ModBlocks.basicWire.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.basicResistor).setRegistryName(ModBlocks.basicResistor.getRegistryName()));

    }

    public void postInit(FMLPostInitializationEvent e) {
        if (config.hasChanged()) {
            config.save();
        }
    }
}
