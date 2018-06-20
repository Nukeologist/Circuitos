package com.nukeologist.circuitos.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

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
}
