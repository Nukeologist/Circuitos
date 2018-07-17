package com.nukeologist.circuitos.block;

import com.nukeologist.circuitos.Circuitos;
import com.nukeologist.circuitos.CreativeTab;
import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IGenerator;
import com.nukeologist.circuitos.reference.Reference;
import com.nukeologist.circuitos.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BaseBlock extends Block {

    protected String name;

    public BaseBlock() {
        super(Material.ROCK);
        this.setCreativeTab(CreativeTab.CIRCUITOS_TAB);
    }

    public BaseBlock(Material material, String name){
        super(material);

        this.setCreativeTab(CreativeTab.CIRCUITOS_TAB);
        this.name = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(Reference.MOD_ID +  "." + name);

    }

    public void registerItemModel(Item itemBlock) {
        Circuitos.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);

        LogHelper.logInfo("Neighbor changed! "+ pos.toString());

        TileEntity te = world.getTileEntity(neighbor);
        TileEntity me = world.getTileEntity(pos);



    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);

        LogHelper.logInfo("Block added ");


        TileEntity me = worldIn.getTileEntity(pos);

        for(EnumFacing d: EnumFacing.VALUES) {
            TileEntity te = worldIn.getTileEntity(new BlockPos(pos.getX() + d.getFrontOffsetX(), pos.getY() + d.getFrontOffsetY(), pos.getZ() + d.getFrontOffsetZ()));

            if (te instanceof BaseTileEntity && me instanceof IGenerator) {
                if (((BaseTileEntity) me).isMaster()) {
                    ((BaseTileEntity) te).setMaster((IGenerator) me);
                    ((BaseTileEntity) me).circuitBlocks.add((BaseTileEntity) te);
                    return;
                } else if (((BaseTileEntity) me).master != null) {
                    ((BaseTileEntity) te).setMaster(((BaseTileEntity) me).master);
                    if (((BaseTileEntity) me).master instanceof BaseTileEntity) {
                        ((BaseTileEntity) ((BaseTileEntity) me).master).circuitBlocks.add((BaseTileEntity) te);
                        return;
                    }
                } else if (((BaseTileEntity) me).master != null && ((BaseTileEntity) me).master != ((BaseTileEntity) te).master && ((BaseTileEntity) te).master != null) {
                    if (((BaseTileEntity) me).master instanceof BaseTileEntity) {
                        for (BaseTileEntity tee : ((BaseTileEntity) te).circuitBlocks) {
                            tee.setMaster(((BaseTileEntity) me).master);
                            ((BaseTileEntity) ((BaseTileEntity) me).master).circuitBlocks.add(tee);

                        }
                        ((BaseTileEntity) te).setMaster(((BaseTileEntity) me).master);

                    }
                }
            } else if (te instanceof BaseTileEntity && me instanceof BaseTileEntity) {
                if (((BaseTileEntity) me).master != null) {
                    ((BaseTileEntity) te).setMaster(((BaseTileEntity) me).master);
                    if (((BaseTileEntity) me).master instanceof BaseTileEntity) {
                        ((BaseTileEntity) ((BaseTileEntity) me).master).circuitBlocks.add((BaseTileEntity) te);
                        return;
                    }
                } else if (((BaseTileEntity) te).master != null && ((BaseTileEntity) me).master == null) {
                    ((BaseTileEntity) me).setMaster(((BaseTileEntity) te).master);
                    if (((BaseTileEntity) te).master instanceof BaseTileEntity) {
                        ((BaseTileEntity) ((BaseTileEntity) te).master).circuitBlocks.add((BaseTileEntity) me);
                        return;
                    }
                }
            }
        }
    }
}
