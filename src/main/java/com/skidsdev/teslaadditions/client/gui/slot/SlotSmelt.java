package com.skidsdev.teslaadditions.client.gui.slot;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotSmelt extends SlotItemHandler
{
	public SlotSmelt(IItemHandler itemHandler, int index, int xPosition, int yPosition)
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return FurnaceRecipes.instance().getSmeltingResult(stack) != null;
	}
}
