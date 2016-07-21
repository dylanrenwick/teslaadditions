package com.skidsdev.teslaadditions.container;

import com.skidsdev.teslaadditions.capability.ICableInterface;
import com.skidsdev.teslaadditions.utils.ExpandingItemHandler;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

public class CableInterface implements INBTSerializable<NBTTagCompound>, ICableInterface
{
	protected ExpandingItemHandler inventory;
	
	public CableInterface(NBTTagCompound tag)
	{
		this();
		
		this.deserializeNBT(tag);
	}
	public CableInterface()
	{
		inventory = new ExpandingItemHandler();
	}
	
	public IItemHandler getInventory()
	{
		return inventory;
	}
	
	@Override
	public void update(EnumFacing facing)
	{
		
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setTag("Inventory", inventory.serializeNBT());
		
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("Inventory")) inventory.deserializeNBT((NBTTagCompound)nbt.getTag("Inventory"));
	}
}
