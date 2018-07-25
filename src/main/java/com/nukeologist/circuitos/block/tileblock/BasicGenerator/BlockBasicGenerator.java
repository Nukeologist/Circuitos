package com.nukeologist.circuitos.block.tileblock.BasicGenerator;

import com.nukeologist.circuitos.Circuitos;
import com.nukeologist.circuitos.block.tileblock.CircuitosBaseTile;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote) {
            playerIn.openGui(Circuitos.instance, Reference.GUI_BASIC_GENERATOR, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }
}
