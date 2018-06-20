package com.nukeologist.circuitos.block.tileblock.BasicWire;

import com.nukeologist.circuitos.block.tileblock.CircuitosBaseTile;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBasicWire extends CircuitosBaseTile<TileEntityBasicWire> {


    public BlockBasicWire(String name) {
        super(Material.CIRCUITS, name);
    }

    @Nullable
    @Override
    public TileEntityBasicWire createTileEntity(World world, IBlockState state) {
        return new TileEntityBasicWire();
    }

    @Override
    public Class getTileEntityClass() {
        return TileEntityBasicWire.class;
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(world, pos, neighbor);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }
}
