package com.skidsdev.teslaadditions.container;

import net.darkhax.tesla.api.ITeslaHolder;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ContainerBase implements IItemHandler, ITeslaHolder
{
	protected long storedPower;
	
	protected ItemStack[] slots;

	@Override
	public int getSlots()
	{
		return slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if (slot < slots.length && slot >= 0) return slots[slot];
		
		return null;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		return null;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		return null;
	}
	
	@Override
	public long getCapacity()
	{
		return 0;
	}

	@Override
	public long getStoredPower()
	{
		return storedPower;
	}
	
	public boolean updateSlots()
	{
		boolean updated = false;
		
		if (slots == null) return false;
		
		for(ItemStack stack : slots)
		{
			if (stack != null && stack.stackSize <= 0)
			{
				stack = null;
				updated = true;
			}
		}
		
		return updated;
	}
}
