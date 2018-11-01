package com.nukeologist.circuitos.block.tileblock.BasicResistor;

import com.nukeologist.circuitos.block.tileblock.CircuitosBaseTile;
import com.nukeologist.circuitos.client.ResistorBakedModel;
import com.nukeologist.circuitos.init.ModBlocks;
import com.nukeologist.circuitos.reference.Reference;
import com.nukeologist.circuitos.utility.UnlistedPropertyBoolean;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockBasicResistor extends CircuitosBaseTile<TileEntityBasicResistor> {



    public static final UnlistedPropertyBoolean NORTH = new UnlistedPropertyBoolean("north");
    public static final UnlistedPropertyBoolean SOUTH = new UnlistedPropertyBoolean("south");
    public static final UnlistedPropertyBoolean WEST = new UnlistedPropertyBoolean("west");
    public static final UnlistedPropertyBoolean EAST = new UnlistedPropertyBoolean("east");
    public static final UnlistedPropertyBoolean UP = new UnlistedPropertyBoolean("up");
    public static final UnlistedPropertyBoolean DOWN = new UnlistedPropertyBoolean("down");

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

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        // To make sure that our baked model model is chosen for all states we use this custom state mapper:
        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return ResistorBakedModel.BAKED_MODEL;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);
    }
    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID, "basicresistor"));
        ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation(getRegistryName(), "inventory");
        final int DEFAULT_ITEM_SUBTYPE = 0;
        ModelLoader.setCustomModelResourceLocation(itemBlock, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    @Override
    protected BlockStateContainer createBlockState() {
        IProperty[] listedProperties = new IProperty[0]; // no listed properties
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { NORTH, SOUTH, WEST, EAST, UP, DOWN };
        return new ExtendedBlockState(this, listedProperties, unlistedProperties);
    }



    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

        boolean north = canConnect(world, pos.north());
        boolean south = canConnect(world, pos.south());
        boolean west = canConnect(world, pos.west());
        boolean east = canConnect(world, pos.east());
        boolean up = canConnect(world, pos.up());
        boolean down = canConnect(world, pos.down());
        //HAS PREFERENCE FOR NORTH AND SOUTH.
        if(north && south){
            west = false;
            east = false;
            up = false;
            down = false;
        }else if(west && east){
            north = false;
            south = false;
            up = false;
            down = false;
        }else if(up && down){
            north = false;
            south = false;
            west = false;
            east = false;
        }else if(north || south){
            east = false;
            west = false;
            up = false;
            down = false;
        }else if(west || east){
            north = false;
            south = false;
            up = false;
            down = false;
        }else if(up || down){
            north = false;
            south = false;
            west = false;
            east = false;
        }

        return extendedBlockState
                .withProperty(NORTH, north)
                .withProperty(SOUTH, south)
                .withProperty(WEST, west)
                .withProperty(EAST, east)
                .withProperty(UP, up)
                .withProperty(DOWN, down);
    }

    private boolean canConnect(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos).getBlock() == ModBlocks.basicWire;
    }
}
