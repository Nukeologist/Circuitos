package com.nukeologist.circuitos.item;

import com.nukeologist.circuitos.block.tileblock.BaseTileEntity;
import com.nukeologist.circuitos.circuit.ICapacitor;
import com.nukeologist.circuitos.circuit.IGenerator;
import com.nukeologist.circuitos.circuit.IResistor;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemCreativeMultimeter extends BaseItem {

    public ItemCreativeMultimeter(String name) {
        super(name);
    }


    //Add dem creative effects
    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        TileEntity te = worldIn.getTileEntity(pos);
        if(!worldIn.isRemote) {
            if (te instanceof IGenerator) {
                IGenerator generator = (IGenerator) te;
                player.sendMessage(new TextComponentTranslation("circuitos.message.fem",generator.getFem()));
                player.sendMessage(new TextComponentTranslation("circuitos.message.resistance", generator.getResistance()));
                player.sendMessage(new TextComponentTranslation("circuitos.message.current", generator.getCurrent()));
            }else if(te instanceof IResistor && !(te instanceof IGenerator)) {
                IResistor resistor = (IResistor) te;
                player.sendMessage(new TextComponentTranslation("circuitos.message.resistance", resistor.getResistance()));
                player.sendMessage(new TextComponentTranslation("circuitos.message.current", resistor.getCurrent()));
            }else if(te instanceof ICapacitor) {
                ICapacitor capacitor = (ICapacitor) te;
                player.sendMessage(new TextComponentTranslation("circuitos.message.capacitance", capacitor.getCapacity()));
            }
            if (te instanceof BaseTileEntity && ((BaseTileEntity) te).master != null) {

                player.sendMessage(new TextComponentTranslation("circuitos.message.master", ((BaseTileEntity) te).master.getPos().toString()));


            }
        }
        return EnumActionResult.SUCCESS;
    }
}
