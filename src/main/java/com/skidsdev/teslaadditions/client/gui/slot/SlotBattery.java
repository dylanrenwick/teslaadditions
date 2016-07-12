package com.skidsdev.teslaadditions.client.gui.slot;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotBattery extends SlotItemHandler
{
	public SlotBattery(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null);
	}
}
