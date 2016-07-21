package com.skidsdev.teslaadditions.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

public class ExpandingItemHandler implements IItemHandler, INBTSerializable<NBTTagCompound>
{
	private List<ItemStack> items;
	
	public ExpandingItemHandler()
	{
		items = new ArrayList<ItemStack>();
	}
	
	@Override
	public int getSlots()
	{
		return getFullSlots() + 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if (isValidSlot(slot))
		{
			return items.get(slot);
		}
		return null;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
	{
		int fullSlots = getFullSlots();
		
		if (slot < fullSlots)
		{
			ItemStack inSlot = items.get(slot);
			
			if (!simulate) items.set(slot, stack);
			
			return inSlot;
		}
		else if (slot == fullSlots)
		{
			return null;
		}
		return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if (isValidSlot(slot))
		{
			return items.get(slot);
		}
		return null;
	}
	
	private int getFullSlots()
	{
		int count = 0;
		
		for(ItemStack stack : items)
		{
			if (stack.getItem() != null) count++;
		}
		
		return count;
	}
	
	private boolean isValidSlot(int slot)
	{
		return slot >= 0 && slot <= getFullSlots() && slot < 8 && slot < items.size();
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		for(int i = 0; i < getFullSlots(); i++)
		{
			ItemStack stack = items.get(i);
			
			if (stack != null)
			{
				tag.setTag("slot" + i, stack.serializeNBT());
			}
		}
		
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		
	}
}
