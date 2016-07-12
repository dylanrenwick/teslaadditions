package com.skidsdev.teslaadditions.client.gui;

import com.skidsdev.teslaadditions.guicontainer.GuiContainerStirling;
import com.skidsdev.teslaadditions.tile.TileEntityGeneratorStirling;
import com.skidsdev.teslaadditions.utils.VersionInfo;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiStirling extends GuiMachine
{
	private static final int TEXTURE_WIDTH = 176;
	private static final int TEXTURE_HEIGHT = 166;
	
	public GuiStirling(InventoryPlayer invPlayer, TileEntityGeneratorStirling tileEntity)
	{
		super(invPlayer, tileEntity, new GuiContainerStirling(invPlayer, tileEntity), TEXTURE_WIDTH, TEXTURE_HEIGHT);
		
		this.texture = new ResourceLocation(VersionInfo.ModId, "textures/gui/blockStirling.png");
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, x, y);
		
		
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		
	}
}
