package com.skidsdev.teslaadditions.client.gui;

import com.skidsdev.teslaadditions.tile.TileEntityElectroFurnace;
import com.skidsdev.teslaadditions.utils.VersionInfo;

import com.skidsdev.teslaadditions.guicontainer.GuiContainerFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiMachine
{
	private static final int TEXTURE_WIDTH = 176;
	private static final int TEXTURE_HEIGHT = 166;
	
	private final int COOK_BAR_XPOS = 79;
	private final int COOK_BAR_YPOS = 35;
	private final int COOK_BAR_ICON_U = 176;
	private final int COOK_BAR_ICON_V = 14;
	private final int COOK_BAR_WIDTH = 24;
	private final int COOK_BAR_HEIGHT = 17;
	
	public GuiFurnace(InventoryPlayer invPlayer, TileEntityElectroFurnace tileEntity)
	{
		super(invPlayer, tileEntity, new GuiContainerFurnace(invPlayer, tileEntity), TEXTURE_WIDTH, TEXTURE_HEIGHT);
		
		this.texture = new ResourceLocation(VersionInfo.ModId, "textures/gui/blockFurnace.png");
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, x, y);
		
		double cookProgress = ((TileEntityElectroFurnace)tileEntity).getProgress();
		
		drawTexturedModalRect(guiLeft + COOK_BAR_XPOS, guiTop + COOK_BAR_YPOS, COOK_BAR_ICON_U, COOK_BAR_ICON_V, (int)(cookProgress * COOK_BAR_WIDTH), COOK_BAR_HEIGHT);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		
	}
}
