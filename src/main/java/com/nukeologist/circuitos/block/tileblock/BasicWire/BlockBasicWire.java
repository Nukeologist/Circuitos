package com.nukeologist.circuitos.block.tileblock.BasicWire;

import com.nukeologist.circuitos.block.tileblock.BasicGenerator.BlockBasicGenerator;
import com.nukeologist.circuitos.block.tileblock.BasicResistor.BlockBasicResistor;
import com.nukeologist.circuitos.block.tileblock.CircuitosBaseTile;
import com.nukeologist.circuitos.client.WireBakedModel;
import com.nukeologist.circuitos.init.ModBlocks;
import com.nukeologist.circuitos.reference.Reference;
import com.nukeologist.circuitos.utility.LogHelper;
import com.nukeologist.circuitos.utility.UnlistedPropertyBoolean;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
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

public class BlockBasicWire extends CircuitosBaseTile<TileEntityBasicWire> {

    public static final AxisAlignedBB BASICWIRE_AABB = new AxisAlignedBB(4/16D, 4/16D, 4/16D, 12/16D, 12/16D, 12/16D);

    public static final UnlistedPropertyBoolean NORTH = new UnlistedPropertyBoolean("north");
    public static final UnlistedPropertyBoolean SOUTH = new UnlistedPropertyBoolean("south");
    public static final UnlistedPropertyBoolean WEST = new UnlistedPropertyBoolean("west");
    public static final UnlistedPropertyBoolean EAST = new UnlistedPropertyBoolean("east");
    public static final UnlistedPropertyBoolean UP = new UnlistedPropertyBoolean("up");
    public static final UnlistedPropertyBoolean DOWN = new UnlistedPropertyBoolean("down");




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

    @SideOnly(Side.CLIENT)
    public void initModel() {

        StateMapperBase ignoreState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return WireBakedModel.BAKED_MODEL;
            }
        };
        ModelLoader.setCustomStateMapper(this, ignoreState);

    }

    @SideOnly(Side.CLIENT)
    public void initItemModel() {
        Item itemBlock = Item.REGISTRY.getObject(new ResourceLocation(Reference.MOD_ID, "basicwire"));
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

    //TODO: FIX BOX
    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        
        return BASICWIRE_AABB;
}

    @Override
    protected BlockStateContainer createBlockState() {
        //return super.createBlockState();
        IProperty[] listedProperties = new IProperty[0]; // no listed properties
        IUnlistedProperty[] unlistedProperties = new IUnlistedProperty[] { NORTH, SOUTH, WEST, EAST, UP, DOWN };
        return new ExtendedBlockState(this, listedProperties, unlistedProperties);
    }

    @Override
    public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos) {
        //return super.getExtendedState(state, world, pos);
        IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

        boolean north = isConnectable(world, pos.north(), "north");
        boolean south = isConnectable(world, pos.south(), "south");
        boolean west = isConnectable(world, pos.west(), "west");
        boolean east = isConnectable(world, pos.east(), "east");
        boolean up = isConnectable(world, pos.up(), "up");
        boolean down = isConnectable(world, pos.down(), "down");

        return extendedBlockState
                .withProperty(NORTH, north)
                .withProperty(SOUTH, south)
                .withProperty(WEST, west)
                .withProperty(EAST, east)
                .withProperty(UP, up)
                .withProperty(DOWN, down);
    }

    //TODO: fix interaction with up and down
    public boolean isConnectable(IBlockAccess world, BlockPos pos, String enumpos) {
        if(world.getBlockState(pos).getBlock() == ModBlocks.basicGenerator) {
            //LogHelper.logInfo(world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName());
            switch (enumpos) {
                case "north":
                    if (world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName().equals("west")) return true;
                    if (world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName().equals("east")) return true;
                    break;
                case "south":
                    if (world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName().equals("west")) return true;
                    if (world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName().equals("east")) return true;
                    break;
                case "east":
                    if (world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName().equals("north")) return true;
                    if (world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName().equals("south")) return true;
                    break;
                case "west":
                    if (world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName().equals("north")) return true;
                    if (world.getBlockState(pos).getValue(BlockBasicGenerator.FACING).getName().equals("south")) return true;
                    break;
                case "up":
                    LogHelper.logInfo("what? up");
                    break;
                case "down":
                    LogHelper.logInfo("what? down");
                    break;
            }
        }


        if(world.getBlockState(pos).getBlock() == ModBlocks.basicResistor) {
            IBlockState state = ModBlocks.basicResistor.getExtendedState(world.getBlockState(pos), world, pos);
            IExtendedBlockState extendedBlockState = (IExtendedBlockState) state;

            switch (enumpos) {
                case "north":
                    if (extendedBlockState.getValue(BlockBasicResistor.SOUTH)) return true;
                    break;
                case "south":
                    if (extendedBlockState.getValue(BlockBasicResistor.NORTH)) return true;
                    break;
                case "east":
                    if (extendedBlockState.getValue(BlockBasicResistor.WEST)) return true;
                    break;
                case "west":
                    if (extendedBlockState.getValue(BlockBasicResistor.EAST)) return true;
                    break;
                case "up":
                    if (extendedBlockState.getValue(BlockBasicResistor.DOWN)) return true;
                    break;
                case "down":
                    if (extendedBlockState.getValue(BlockBasicResistor.UP)) return true;
                    break;
            }

        }
        return world.getBlockState(pos).getBlock() == ModBlocks.basicWire;
    }


}
