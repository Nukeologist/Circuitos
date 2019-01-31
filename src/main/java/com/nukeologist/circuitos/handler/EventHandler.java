package com.nukeologist.circuitos.handler;


import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.block.tileblock.BasicResistor.TileEntityBasicResistor;
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

        //LogHelper.logInfo("Aqui!: " + pos.toString());
        TileEntity te = world.getTileEntity(pos);
        //if it is the master, we must warn ALL the slaves.
        if (te instanceof TileEntityBasicGenerator) {
            if (((TileEntityBasicGenerator) te).isMaster()) {
                for (BaseTileEntity tile : ((TileEntityBasicGenerator) te).allCircuitList) {
                    tile.analyzed = false;
                    //tile.master.markDirty();
                    tile.setMaster(null);

                }
                ((TileEntityBasicGenerator) te).allCircuitList.clear();
                ((TileEntityBasicGenerator) te).machineList.clear();
                ((TileEntityBasicGenerator) te).master.getGraph().getNodes().clear();
                ((TileEntityBasicGenerator) te).master.readyToWork = false;
                ((TileEntityBasicGenerator) te).master.markDirty();
                world.notifyBlockUpdate(((TileEntityBasicGenerator) te).master.getPos(), world.getBlockState(((TileEntityBasicGenerator) te).master.getPos()),world.getBlockState(((TileEntityBasicGenerator) te).master.getPos()), 0);
                return;
            }else {
                if (((TileEntityBasicGenerator) te).master !=null) {
                    for (BaseTileEntity tile : (((TileEntityBasicGenerator) te).master).allCircuitList) {
                        tile.analyzed = false;
                        //tile.master.markDirty();
                        tile.setMaster(null);

                    }
                    ((TileEntityBasicGenerator) te).master.allCircuitList.clear();
                    ((TileEntityBasicGenerator) te).master.machineList.clear();
                    ((TileEntityBasicGenerator) te).master.getGraph().getNodes().clear();
                    ((TileEntityBasicGenerator) te).master.readyToWork = false;
                    ((TileEntityBasicGenerator) te).master.markDirty();
                    world.notifyBlockUpdate(((TileEntityBasicGenerator) te).master.getPos(), world.getBlockState(((TileEntityBasicGenerator) te).master.getPos()),world.getBlockState(((TileEntityBasicGenerator) te).master.getPos()), 0);
                    return;
                }
            }
        } else if (te instanceof TileEntityBasicWire && !(te instanceof TileEntityBasicGenerator)) {
            if (((TileEntityBasicWire) te).master != null) {
                for (BaseTileEntity tile : ((BaseTileEntity) te).master.allCircuitList) {
                    if(tile != te) {
                        tile.analyzed = false;
                        //tile.master.markDirty();
                        tile.setMaster(null);

                    }

                }
                ((TileEntityBasicWire) te).master.allCircuitList.clear();
                ((TileEntityBasicWire) te).master.machineList.clear();
                ((TileEntityBasicWire) te).master.getGraph().getNodes().clear();
                ((TileEntityBasicWire) te).master.readyToWork = false;
                ((TileEntityBasicWire) te).master.markDirty();
                world.notifyBlockUpdate(((TileEntityBasicWire) te).master.getPos(), world.getBlockState(((TileEntityBasicWire) te).master.getPos()),world.getBlockState(((TileEntityBasicWire) te).master.getPos()), 0);
            }
        }else if(te instanceof TileEntityBasicResistor &&!(te instanceof TileEntityBasicGenerator)){
            if(((TileEntityBasicResistor) te).master != null){
                for (BaseTileEntity tile : ((BaseTileEntity) te).master.allCircuitList) {
                    if(tile != te) {
                        tile.analyzed = false;
                       // tile.master.markDirty();
                        tile.setMaster(null);
                    }

                }
                ((TileEntityBasicResistor) te).master.allCircuitList.clear();
                ((TileEntityBasicResistor) te).master.machineList.clear();
                ((TileEntityBasicResistor) te).master.getGraph().getNodes().clear();
                ((TileEntityBasicResistor) te).master.readyToWork = false;
                ((TileEntityBasicResistor) te).master.markDirty();
                world.notifyBlockUpdate(((TileEntityBasicResistor) te).master.getPos(), world.getBlockState(((TileEntityBasicResistor) te).master.getPos()),world.getBlockState(((TileEntityBasicResistor) te).master.getPos()), 0);
            }
        }

    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.PlaceEvent event){
        BlockPos pos = event.getPos();
        World world = event.getWorld();
        //LogHelper.logInfo("Opa!: " + pos.toString());
    }
}
