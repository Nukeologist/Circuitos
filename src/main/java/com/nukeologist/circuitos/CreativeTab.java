package com.nukeologist.circuitos;

import com.nukeologist.circuitos.init.ModItems;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Collections;
import java.util.Comparator;

public class CreativeTab {

    public static final CreativeTabs CIRCUITOS_TAB = new CreativeTabs(Reference.MOD_ID) {

        private ItemSorter itemSorter = new ItemSorter();

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.itemCreativeMultimeter);
        }

        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
            super.displayAllRelevantItems(p_78018_1_);
            Collections.sort(p_78018_1_, itemSorter);
        }
    };


    private static class ItemSorter implements Comparator<ItemStack> {

        @Override
        public int compare(ItemStack o1, ItemStack o2) {
            Item item1 = o1.getItem();
            Item item2 = o2.getItem();

            if (((item1 instanceof ItemBlock)) && (!(item2 instanceof ItemBlock))) {
                return -1;
            }

            if (((item2 instanceof ItemBlock)) && (!(item1 instanceof ItemBlock))) {
                return 1;
            }

            String displayName1 = o1.getDisplayName();
            String displayName2 = o2.getDisplayName();




            return displayName1.compareToIgnoreCase(displayName2);
        }
    }


}
