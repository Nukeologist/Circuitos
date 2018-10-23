package com.nukeologist.circuitos.client.gui;

import com.nukeologist.circuitos.block.tileblock.BasicGenerator.TileEntityBasicGenerator;
import com.nukeologist.circuitos.network.CircuitosPacketHandler;
import com.nukeologist.circuitos.network.PacketGeneratorGUIUpdate;
import com.nukeologist.circuitos.reference.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;


public class GUIBasicGenerator extends GuiContainer {

    private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/basicgenerator.png");
    private final TileEntityBasicGenerator tileentity;
    private final InventoryPlayer player;

    private int id = 0;

    GuiButton button1, button2;

    public GUIBasicGenerator(InventoryPlayer player, TileEntityBasicGenerator tileEntity) {
        super(new ContainerBasicGenerator(player, tileEntity));
        this.player = player;
        this.tileentity = tileEntity;

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        String tileName = I18n.format("circuitos.container.basic_generator");

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
    public void initGui() {
        buttonList.clear();
        buttonList.add(button1 = new GuiButton(newId(), 75, this.ySize - 80, 90, 20, "Analyze Circuitry"));
        buttonList.add(button2 = new GuiButton(newId(), 0, 0, 30, 15, "Start"));
        if(!tileentity.readyToWork) button2.enabled = false;
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
        button1.x = this.guiLeft + 30;
        button1.y = this.guiTop +60;
        button2.x = this.guiLeft + 130;
        button2.y = this.guiTop + 55;

        updateButtons();
    }

    public void updateButtons() {
        if(tileentity.analyzing) {
            button1.enabled = false;
            button1.displayString = "Analyzing...";
            button2.enabled = false;
        }else {
            button1.enabled = true;
            button1.displayString = "Analyze Circuitry";
            button2.enabled = true;

        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                button1.displayString = "Analyzing..."; //start the ANALYSIS!
                CircuitosPacketHandler.INSTANCE.sendToServer(new PacketGeneratorGUIUpdate(tileentity.getPos().getX(), tileentity.getPos().getY(), tileentity.getPos().getZ(),"analyzing"));
                break;
            case 1: //start doing the Circuit flow!

                break;
        }
        updateButtons();
        super.actionPerformed(button);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private int newId() {
        return this.id ++;
    }
}
