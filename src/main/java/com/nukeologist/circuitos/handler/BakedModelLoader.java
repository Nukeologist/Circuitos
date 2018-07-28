package com.nukeologist.circuitos.handler;

import com.nukeologist.circuitos.client.WireModel;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class BakedModelLoader implements ICustomModelLoader {

    public static final WireModel WIRE_MODEL = new WireModel();

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return modelLocation.getResourceDomain().equals(Reference.MOD_ID) && "basicwire".equals(modelLocation.getResourcePath());
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        return WIRE_MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
