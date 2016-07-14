package com.skidsdev.teslaadditions.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.teslaadditions.guicontainer.GuiContainerBase;
import com.skidsdev.teslaadditions.tile.TileEntityMachine;

import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class GuiMachine extends GuiContainer
{
	private final int POWER_BAR_TOP_LEFT_X = 9;
	private final int POWER_BAR_TOP_LEFT_Y = 9;
	private final int POWER_BAR_BOTTOM_WIDTH = 14;
	private final int POWER_BAR_BOTTOM_HEIGHT = 50;
	
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
		
		drawPowerbar(this, guiLeft + POWER_BAR_TOP_LEFT_X, guiTop + POWER_BAR_TOP_LEFT_Y, tileEntity, false);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
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
	
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
	{
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
	
	public static void drawPowerbar(GuiContainer container, int x, int y, TileEntity tile, EnumFacing side, boolean bg)
	{
		ITeslaHolder holder = tile.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, side);
		
		if (holder != null)
		{
			drawPowerbar(container, x, y, holder.getStoredPower(),holder.getCapacity(),bg);
		}
	}
	
	public static void drawPowerbar(GuiContainer container, int x, int y, TileEntity tile, boolean bg)
	{
		ITeslaHolder holder = tile.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, null);
		
		if (holder != null)
		{
			drawPowerbar(container, x, y, holder.getStoredPower(),holder.getCapacity(),bg);
		}
	}
	
	public static void drawPowerbar(GuiContainer container, int x, int y, long amount, long capacity, boolean bg)
	{
		ResourceLocation resourceLocation = new ResourceLocation("teslaadditions", "textures/gui/sheet.png");
		container.mc.getTextureManager().bindTexture(resourceLocation);
		if (bg) container.drawTexturedModalRect(x, y, 3, 1, 14, 50);
		long j = (amount * 51) / capacity;
		container.drawTexturedModalRect(x + 1, (int)(y + 50 - j), 18, (int)(51 - j), 14, (int)(j + 2));
	}
}
