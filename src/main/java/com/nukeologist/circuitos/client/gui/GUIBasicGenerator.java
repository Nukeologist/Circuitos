package com.nukeologist.circuitos.client.gui;

import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;


public class GUIBasicGenerator extends GuiContainer {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/basicgenerator.png");
    private final TileEntityBasicGenerator tileentity;
    private final InventoryPlayer player;

    public GUIBasicGenerator(InventoryPlayer player, TileEntityBasicGenerator tileEntity) {
        super(new ContainerBasicGenerator(player, tileEntity));
        this.player = player;
        this.tileentity = tileEntity;

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        //super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        //if(this.tileentity.getDisplayName().getUnformattedText() != null) {
            String tileName = this.tileentity.getDisplayName().getUnformattedText();
       //}
        this.fontRenderer.drawString(tileName, (this.xSize / 2 - this.fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
        this.fontRenderer.drawString(this.player.getDisplayName().getUnformattedText(), 122, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(TEXTURES);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
