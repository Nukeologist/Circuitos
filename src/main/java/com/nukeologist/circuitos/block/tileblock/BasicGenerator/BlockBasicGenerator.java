package com.nukeologist.circuitos.block.tileblock.BasicGenerator;

import com.nukeologist.circuitos.block.tileblock.CircuitosBaseTile;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBasicGenerator extends CircuitosBaseTile<TileEntityBasicGenerator> {


    @Nullable
    @Override
    public TileEntityBasicGenerator createTileEntity(World world, IBlockState state) {
        return new TileEntityBasicGenerator();
    }

    @Override
    public Class getTileEntityClass() {
        return TileEntityBasicGenerator.class;
    }

    public BlockBasicGenerator(String name) {
        super(Material.ROCK, name);

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
