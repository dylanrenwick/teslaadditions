package com.skidsdev.teslaadditions.utils;

import com.skidsdev.teslaadditions.tile.TileEntityMachine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.IItemHandler;

public class InventoryWrapper implements IInventory
{
	private IItemHandler itemHandler;
	private TileEntityMachine tileEntity;
	
	public InventoryWrapper(IItemHandler itemHandler, TileEntityMachine tileEntity)
	{
		this.itemHandler = itemHandler;
		this.tileEntity = tileEntity;
	}
	
	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return null;
	}

	@Override
	public int getSizeInventory()
	{
		return itemHandler.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return itemHandler.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return itemHandler.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return itemHandler.extractItem(index, itemHandler.getStackInSlot(index).stackSize, false);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		itemHandler.insertItem(index, stack, false);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
		tileEntity.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return tileEntity.isUseableByPlayer(player);
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{
		
	}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		
	}

}
