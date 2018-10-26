package com.nukeologist.circuitos.handler;

import com.nukeologist.circuitos.client.WireModel;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class BakedModelLoader implements ICustomModelLoader {

    public static final WireModel WIRE_MODEL = new WireModel();

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        if (modelLocation instanceof ModelResourceLocation && ((ModelResourceLocation)modelLocation).getVariant().equals("inventory")) return false;

        return modelLocation.getNamespace().equals(Reference.MOD_ID) && "basicwire".equals(modelLocation.getPath());
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        return WIRE_MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
