package com.skidsdev.teslaadditions.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.teslaadditions.client.gui.slot.SlotInterfaceModule;
import com.skidsdev.teslaadditions.container.CableInterface;
import com.skidsdev.teslaadditions.guicontainer.GuiContainerInterface;
import com.skidsdev.teslaadditions.tile.TileEntityMultiCable;
import com.skidsdev.teslaadditions.utils.VersionInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;

public class GuiInterface extends GuiContainer
{
	private static final int TEXTURE_WIDTH = 176;
	private static final int TEXTURE_HEIGHT = 166;
	
	private TileEntityMultiCable tileEntity;
	private ResourceLocation texture;
	private CableInterface cableInterface;
	private IItemHandler inventory;
	
	public GuiInterface(InventoryPlayer invPlayer, TileEntityMultiCable tileEntity, CableInterface interfaze)
	{
		super(new GuiContainerInterface(invPlayer, tileEntity, interfaze));
		
		this.tileEntity = tileEntity;
		cableInterface = interfaze;
		inventory = cableInterface.getInventory();
		
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
		
		final int SLOT_XPOS = 7;
		final int SLOT_YPOS = 7;
		final int SLOT_PADDING_X = 21;
		final int SLOT_PADDING_Y = 42;
		
		final int SLOT_U = 176;
		final int SLOT_V = 40;
		
		int slots = inventory.getSlots();
		for(int i = 0; i < slots; i++)
		{
			boolean secondRow = i >= 9;
			drawTexturedModalRect(guiLeft + SLOT_XPOS + SLOT_PADDING_X * i, guiTop + SLOT_YPOS + SLOT_PADDING_Y * (secondRow ? 1 : 0), SLOT_U, SLOT_V, 20, 20);
		}
	}
	
	public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY)
	{
		return ((mouseX >= x && mouseX <= x+xSize) && (mouseY >= y && mouseY <= y+ySize));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{		
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		List<String> hoveringText = new ArrayList<String>();
	}
}
