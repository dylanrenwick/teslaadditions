package com.skidsdev.teslaadditions.client.gui.slot;

import com.skidsdev.teslaadditions.item.IInterfaceModule;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotInterfaceModule extends SlotItemHandler
{
	public SlotInterfaceModule(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.getItem() instanceof IInterfaceModule;
	}
}
