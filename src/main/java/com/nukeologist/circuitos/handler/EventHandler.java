package com.nukeologist.circuitos.handler;


import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.block.tileblock.BasicWire.TileEntityBasicWire;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {


    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event){

        BlockPos pos = event.getPos();
        World world = event.getWorld();

        LogHelper.logInfo("Aqui!: " + pos.toString());
        TileEntity te = world.getTileEntity(pos);
        //if it is the master, we must warn ALL the slaves.
        if (te instanceof TileEntityBasicGenerator) {
            if (((TileEntityBasicGenerator) te).isMaster()) {
                for (BaseTileEntity tile : ((TileEntityBasicGenerator) te).allCircuitList) {
                    tile.analyzed = false;
                    tile.setMaster(null);

                }
                ((TileEntityBasicGenerator) te).allCircuitList.clear();
                ((TileEntityBasicGenerator) te).machineList.clear();
                return;
            }
        } else if (te instanceof TileEntityBasicWire && !(te instanceof TileEntityBasicGenerator)) {
            if (((TileEntityBasicWire) te).master != null) {
                for (BaseTileEntity tile : ((BaseTileEntity) te).master.allCircuitList) {
                    if(tile != te) {
                        tile.analyzed = false;
                        tile.setMaster(null);
                    }

                }
                ((TileEntityBasicWire) te).master.allCircuitList.clear();
                ((TileEntityBasicWire) te).master.machineList.clear();
            }
        }

    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.PlaceEvent event){
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        LogHelper.logInfo("Opa!: " + pos.toString());
    }
}
