package com.nukeologist.circuitos.handler;

import com.nukeologist.circuitos.client.ResistorModel;
import com.nukeologist.circuitos.client.WireModel;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class BakedModelLoader implements ICustomModelLoader {

    public static final WireModel WIRE_MODEL = new WireModel();
    public static final ResistorModel RESISTOR_MODEL = new ResistorModel();

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        if (modelLocation instanceof ModelResourceLocation && ((ModelResourceLocation)modelLocation).getVariant().equals("inventory")) return false;

        if( modelLocation.getNamespace().equals(Reference.MOD_ID) && "basicwire".equals(modelLocation.getPath())){
            return true;
        }else if(modelLocation.getNamespace().equals(Reference.MOD_ID) && "basicresistor".equals(modelLocation.getPath())){
            return true;
        }
        return false;
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) {
        if(modelLocation.getPath().equals("basicwire"))return WIRE_MODEL;
        return RESISTOR_MODEL;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
