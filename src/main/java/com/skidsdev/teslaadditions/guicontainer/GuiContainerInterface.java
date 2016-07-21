package com.skidsdev.teslaadditions.guicontainer;

import com.skidsdev.teslaadditions.client.gui.slot.SlotInterfaceModule;
import com.skidsdev.teslaadditions.container.CableInterface;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;

public class GuiContainerInterface extends GuiContainerBase
{
	private CableInterface cabInterface;
	private IItemHandler inventory;
	
	public GuiContainerInterface(InventoryPlayer invPlayer, TileEntity tileEntity, CableInterface interfaze)
	{
		super(invPlayer, tileEntity);
		
		cabInterface = interfaze;
		inventory = cabInterface.getInventory();
		
		final int SLOT_XPOS = 7;
		final int SLOT_YPOS = 7;
		final int SLOT_PADDING_X = 21;
		final int SLOT_PADDING_Y = 42;
		
		int slots = inventory.getSlots();
		for(int i = 0; i < slots; i++)
		{
			boolean secondRow = i >= 9;
			addSlotToContainer(new SlotInterfaceModule(inventory, i, SLOT_XPOS + 2 + SLOT_PADDING_X * i, SLOT_YPOS + 2 + SLOT_PADDING_Y * (secondRow ? 1 : 0)));
		}
	}
}
