package com.nukeologist.circuitos.network;

import com.nukeologist.circuitos.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CircuitosPacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID);
    private static int ID = 0;

    public static int nextID() {
        return ID++;
    }

    private CircuitosPacketHandler() {

    }

    public static void init() {

        INSTANCE.registerMessage(PacketGeneratorGUIUpdate.Handler.class, PacketGeneratorGUIUpdate.class, nextID(), Side.SERVER );
        //INSTANCE.registerMessage(PacketGeneratorGUIUpdate.Handler.class, PacketGeneratorGUIUpdate.class, nextID(), Side.CLIENT);

    }
}
