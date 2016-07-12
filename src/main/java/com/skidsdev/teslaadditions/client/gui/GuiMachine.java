package com.skidsdev.teslaadditions.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.teslaadditions.guicontainer.GuiContainerBase;
import com.skidsdev.teslaadditions.tile.TileEntityMachine;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiMachine extends GuiContainer
{
	private final int POWER_BAR_TOP_LEFT_X = 7;
	private final int POWER_BAR_TOP_LEFT_Y = 7;
	private final int POWER_BAR_BOTTOM_WIDTH = 18;
	private final int POWER_BAR_BOTTOM_HEIGHT = 55;
	
	protected ResourceLocation texture;
	
	protected TileEntityMachine tileEntity;
	
	public GuiMachine(InventoryPlayer invPlayer, TileEntityMachine tileEntity, GuiContainerBase container, int xSize, int ySize)
	{
		super(container);
		
		this.xSize = xSize;
		this.ySize = ySize;
		
		this.tileEntity = tileEntity;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		List<String> hoveringText = new ArrayList<String>();
		
		if (isInRect(guiLeft + POWER_BAR_TOP_LEFT_X, guiTop + POWER_BAR_TOP_LEFT_Y, POWER_BAR_BOTTOM_WIDTH, POWER_BAR_BOTTOM_HEIGHT, mouseX, mouseY))
		{
			hoveringText.add(tileEntity.getStoredPower() + "/" + tileEntity.getPowerCap() + " T");
		}
		
		if (!hoveringText.isEmpty())
		{
			drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRendererObj);
		}
	}
	
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY){
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
}
