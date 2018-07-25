package com.nukeologist.circuitos;

import com.nukeologist.circuitos.handler.GUIHandler;
import com.nukeologist.circuitos.proxy.CommonProxy;
import com.nukeologist.circuitos.reference.Reference;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Circuitos{

    @Mod.Instance
    public static Circuitos instance;


    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){

        proxy.preInit(event);

        LogHelper.logInfo("preInit complete!");
    }

    @EventHandler
    public void init(FMLInitializationEvent event){


        NetworkRegistry.INSTANCE.registerGuiHandler(Circuitos.instance, new GUIHandler());

        LogHelper.logInfo("Init complete!");

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit(event);
        LogHelper.logInfo("postInit complete!");
    }


}
