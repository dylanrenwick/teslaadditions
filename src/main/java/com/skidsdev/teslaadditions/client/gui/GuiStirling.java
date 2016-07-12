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
	
	final int FLAME_XPOS = 80;
	final int FLAME_YPOS = 54;
	final int FLAME_ICON_U = 176;
	final int FLAME_ICON_V = 0;
	final int FLAME_WIDTH = 14;
	final int FLAME_HEIGHT = 14;
	
	public GuiStirling(InventoryPlayer invPlayer, TileEntityGeneratorStirling tileEntity)
	{
		super(invPlayer, tileEntity, new GuiContainerStirling(invPlayer, tileEntity), TEXTURE_WIDTH, TEXTURE_HEIGHT);
		
		this.texture = new ResourceLocation(VersionInfo.ModId, "textures/gui/blockStirling.png");
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
	{
		super.drawGuiContainerBackgroundLayer(partialTicks, x, y);
		
		double burnRemaining = ((TileEntityGeneratorStirling)tileEntity).getTimeRemaining();
		
		int yOffset = (int)Math.ceil((1.0 - burnRemaining) * (FLAME_HEIGHT));
		
		drawTexturedModalRect(guiLeft + FLAME_XPOS, guiTop + FLAME_YPOS + yOffset, FLAME_ICON_U, FLAME_ICON_V + yOffset, FLAME_WIDTH, FLAME_HEIGHT - yOffset);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		
	}
}
