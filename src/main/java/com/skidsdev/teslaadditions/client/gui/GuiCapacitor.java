package com.skidsdev.teslaadditions.client.gui;

import com.skidsdev.teslaadditions.guicontainer.GuiContainerCapacitor;
import com.skidsdev.teslaadditions.tile.TileEntityCapacitor;
import com.skidsdev.teslaadditions.utils.VersionInfo;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCapacitor extends GuiMachine
{
	private static final int TEXTURE_WIDTH = 176;
	private static final int TEXTURE_HEIGHT = 166;
	
	public GuiCapacitor(InventoryPlayer invPlayer, TileEntityCapacitor tileEntity)
	{
		super(invPlayer, tileEntity, new GuiContainerCapacitor(invPlayer, tileEntity), TEXTURE_WIDTH, TEXTURE_HEIGHT);
		
		this.texture = new ResourceLocation(VersionInfo.ModId, "textures/gui/blockCapacitor.png");
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
