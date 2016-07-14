package com.skidsdev.teslaadditions.client.gui;

import com.skidsdev.teslaadditions.capability.MultiCable;
import com.skidsdev.teslaadditions.capability.TeslaAdditionsCapabilities;
import com.skidsdev.teslaadditions.guicontainer.GuiContainerCable;
import com.skidsdev.teslaadditions.tile.TileEntityMultiCable;
import com.skidsdev.teslaadditions.utils.VersionInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

public class GuiCable extends GuiContainer
{
	private static final int TEXTURE_WIDTH = 176;
	private static final int TEXTURE_HEIGHT = 166;
	
	private static final int BUTTON_U = 176;
	private static final int BUTTON_V = 0;
	private static final int BUTTON_WIDTH = 20;
	private static final int BUTTON_HEIGHT = 20;
	
	private static final int BUTTON_LOC_X = 25;
	private static final int BUTTON_LOC_Y = 30;
	private static final int BUTTON_X_PADDING = 21;
	
	private TileEntityMultiCable tileEntity;
	private MultiCable multiCable;
	private ResourceLocation texture;
	
	public GuiCable(InventoryPlayer invPlayer, TileEntityMultiCable tileEntity)
	{
		super(new GuiContainerCable(invPlayer, tileEntity));
		
		this.tileEntity = tileEntity;
		this.multiCable = (MultiCable)tileEntity.getCapability(TeslaAdditionsCapabilities.MULTI_CABLE, null);
		
		this.texture = new ResourceLocation(VersionInfo.ModId, "textures/gui/blockCable.png");
		
		this.xSize = TEXTURE_WIDTH;
		this.ySize = TEXTURE_HEIGHT;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
	{		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		for(EnumFacing facing : EnumFacing.values())
		{
			if (tileEntity.hasInterface(facing))
			{
				drawButton(facing);
			}
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	private void drawButton(EnumFacing facing)
	{
		int padding = BUTTON_X_PADDING * facing.ordinal();
		drawTexturedModalRect(guiLeft + padding, guiTop + BUTTON_LOC_Y, BUTTON_U, BUTTON_V, BUTTON_U + BUTTON_WIDTH, BUTTON_V + BUTTON_HEIGHT);
	}
}
