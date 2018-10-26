package com.nukeologist.circuitos.item;

import com.nukeologist.circuitos.CreativeTab;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItem extends Item {

    public BaseItem(String name) {
        //setUnlocalizedName(Reference.MOD_ID + "." +name);
        setRegistryName(name);
        setCreativeTab(CreativeTab.CIRCUITOS_TAB);
        this.setTranslationKey(Reference.MOD_ID +  "." + name);


    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
