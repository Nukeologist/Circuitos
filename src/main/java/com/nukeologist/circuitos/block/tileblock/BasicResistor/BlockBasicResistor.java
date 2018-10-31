package com.nukeologist.circuitos.block.tileblock.BasicResistor;

import com.nukeologist.circuitos.block.tileblock.CircuitosBaseTile;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockBasicResistor extends CircuitosBaseTile<TileEntityBasicResistor> {

    public BlockBasicResistor(Material material, String name){
        super(material, name);
    }

    public BlockBasicResistor(String name){
        super(Material.CIRCUITS, name);
    }

    @Nullable
    @Override
    public TileEntityBasicResistor createTileEntity(World world, IBlockState state) {
        return new TileEntityBasicResistor();
    }

    @Override
    public Class<TileEntityBasicResistor> getTileEntityClass() {
        return TileEntityBasicResistor.class;
    }
}
