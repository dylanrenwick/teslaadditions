package com.skidsdev.teslaadditions.client.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.skidsdev.teslaadditions.TeslaAdditions;
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
	
	private static final int BUTTON_U_HIGHLIGHT = 176;
	private static final int BUTTON_V_HIGHLIGHT = 20;
	
	private static final int BUTTON_LOC_X = 25;
	private static final int BUTTON_LOC_Y = 30;
	private static final int BUTTON_X_PADDING = 21;
	
	private static final int BUTTON_TEXT_PADDING_X = 7;
	private static final int BUTTON_TEXT_PADDING_Y = 6;
	
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
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		for(EnumFacing facing : EnumFacing.values())
		{
			if (tileEntity.hasInterface(facing) && mouseOverButton(facing, mouseX, mouseY))
			{
				Minecraft.getMinecraft().thePlayer.openGui(TeslaAdditions.instance, facing.ordinal() + 1, Minecraft.getMinecraft().theWorld, tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ());
			}
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
	{
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		List<String> hoveringText = new ArrayList<String>();
		
		for(EnumFacing facing : EnumFacing.values())
		{
			if (tileEntity.hasInterface(facing))
			{
				drawButton(facing, mouseX, mouseY);
			}
		}
		for(EnumFacing facing : EnumFacing.values())
		{
			if (tileEntity.hasInterface(facing))
			{
				drawButtonText(facing, mouseX, mouseY, hoveringText);
			}
		}
		
		if (!hoveringText.isEmpty())
		{
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
		}
	}
	
	private void drawButton(EnumFacing facing, int mouseX, int mouseY)
	{
		int padding = BUTTON_X_PADDING * facing.ordinal();
		boolean highlight = mouseOverButton(facing, mouseX, mouseY);
		drawTexturedModalRect(BUTTON_LOC_X + padding, BUTTON_LOC_Y, highlight ? BUTTON_U_HIGHLIGHT : BUTTON_U, highlight ? BUTTON_V_HIGHLIGHT : BUTTON_V, BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	private void drawButtonText(EnumFacing facing, int mouseX, int mouseY, List<String> hoveringText)
	{
		int padding = BUTTON_X_PADDING * facing.ordinal();
		boolean highlight = mouseOverButton(facing, mouseX, mouseY);
		fontRendererObj.drawString(facing.getName().substring(0, 1).toUpperCase(), BUTTON_LOC_X + padding + BUTTON_TEXT_PADDING_X + 1, BUTTON_LOC_Y + BUTTON_TEXT_PADDING_Y + 1, Color.darkGray.getRGB());
		fontRendererObj.drawString(facing.getName().substring(0, 1).toUpperCase(), BUTTON_LOC_X + padding + BUTTON_TEXT_PADDING_X, BUTTON_LOC_Y + BUTTON_TEXT_PADDING_Y, highlight ? Color.yellow.getRGB() : Color.white.getRGB());
		
		if (highlight) hoveringText.add(facing.getName().substring(0, 1).toUpperCase() + facing.getName().substring(1));
	}
	
	private boolean mouseOverButton(EnumFacing facing, int mouseX, int mouseY)
	{
		int padding = BUTTON_X_PADDING * facing.ordinal();
		boolean highlight = isInRect(guiLeft + BUTTON_LOC_X + padding, guiTop + BUTTON_LOC_Y, BUTTON_WIDTH, BUTTON_HEIGHT, mouseX, mouseY);
		return highlight;
	}
}
