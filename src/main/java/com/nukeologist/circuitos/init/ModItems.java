package com.nukeologist.circuitos.init;

import com.nukeologist.circuitos.item.ItemCreativeMultimeter;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {


    @GameRegistry.ObjectHolder("circuitos:creativemultimeter")
    public static ItemCreativeMultimeter itemCreativeMultimeter;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemCreativeMultimeter.initModel();
    }
}
