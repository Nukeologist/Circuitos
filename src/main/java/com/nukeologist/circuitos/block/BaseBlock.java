package com.nukeologist.circuitos.block;

import com.nukeologist.circuitos.Circuitos;
import com.nukeologist.circuitos.CreativeTab;
import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.IGenerator;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

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

        TileEntity te = world.getTileEntity(neighbor);
        TileEntity me = world.getTileEntity(pos);

        if(te instanceof BaseTileEntity && me instanceof IGenerator) {
            if (((BaseTileEntity) me).isMaster()) {
                ((BaseTileEntity) te).setMaster((IGenerator) me);
                ((BaseTileEntity) me).circuitBlocks.add((BaseTileEntity) te);
                return;
            }else if(((BaseTileEntity) me).master != null) {
                ((BaseTileEntity) te).setMaster(((BaseTileEntity) me).master);
                if(((BaseTileEntity) me).master instanceof BaseTileEntity) {
                    ((BaseTileEntity) ((BaseTileEntity) me).master).circuitBlocks.add((BaseTileEntity) te);
                    return;
                }
            }
        }else if(te instanceof BaseTileEntity && me instanceof BaseTileEntity) {
            if(((BaseTileEntity) me).master != null ) {
                ((BaseTileEntity) te).setMaster(((BaseTileEntity) me).master);
                if(((BaseTileEntity) me).master instanceof BaseTileEntity) {
                    ((BaseTileEntity) ((BaseTileEntity) me).master).circuitBlocks.add((BaseTileEntity) te);
                    return;
                }
            }else if(((BaseTileEntity) te).master != null && ((BaseTileEntity) me).master == null){
                ((BaseTileEntity) me).setMaster(((BaseTileEntity) te).master);
                if(((BaseTileEntity) te).master instanceof BaseTileEntity) {
                    ((BaseTileEntity) ((BaseTileEntity) te).master).circuitBlocks.add((BaseTileEntity) me);
                    return;
                }
            }
        }
    }
}
