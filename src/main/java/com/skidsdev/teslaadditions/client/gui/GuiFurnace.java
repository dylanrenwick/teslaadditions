package com.skidsdev.teslaadditions.client.gui;

import com.skidsdev.teslaadditions.tile.TileEntityElectroFurnace;
import com.skidsdev.teslaadditions.utils.VersionInfo;

import guicontainer.GuiContainerFurnace;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFurnace extends GuiMachine
{
	public GuiFurnace(InventoryPlayer invPlayer, TileEntityElectroFurnace tileEntity)
	{
		super(invPlayer, tileEntity, new GuiContainerFurnace(invPlayer, tileEntity));
		
		this.texture = new ResourceLocation(VersionInfo.ModId, "textures/gui/blockFurnace.png");
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
