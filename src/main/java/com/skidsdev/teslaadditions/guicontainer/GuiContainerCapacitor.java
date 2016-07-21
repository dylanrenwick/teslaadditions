package com.skidsdev.teslaadditions.guicontainer;

import com.skidsdev.teslaadditions.client.gui.slot.SlotBattery;
import com.skidsdev.teslaadditions.client.gui.slot.SlotCharge;
import com.skidsdev.teslaadditions.tile.TileEntityCapacitor;

import net.minecraft.entity.player.InventoryPlayer;

public class GuiContainerCapacitor extends GuiContainerBase
{
	public GuiContainerCapacitor(InventoryPlayer invPlayer, TileEntityCapacitor tileEntity)
	{
		super(invPlayer, tileEntity);
		
		final int CHARGE_SLOT_XPOS = 8;
		final int CHARGE_SLOT_YPOS = 64;
		
		int slotNumber = 0;
		addSlotToContainer(new SlotBattery(tileEntity.getInventory(), slotNumber, CHARGE_SLOT_XPOS, CHARGE_SLOT_YPOS));
		
		final int INPUT_SLOT_XPOS = 56;
		final int INPUT_SLOT_YPOS = 35;
		
		for(int i = 1; i < 5; i++)
		{
			addSlotToContainer(new SlotCharge(tileEntity.getInventory(), i, INPUT_SLOT_XPOS + (20 * (i - 1)), INPUT_SLOT_YPOS));
		}
	}
}
