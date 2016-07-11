package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.client.gui.IOpenableGUI;
import com.skidsdev.teslaadditions.container.ContainerBase;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class TileEntityMachine extends TileEntityInventory implements ITickable, IOpenableGUI
{
	protected final ContainerBase container;
	
	public TileEntityMachine(ContainerBase container)
	{
		this.container = container;
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public abstract Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos);

	@Override
	public abstract Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos);
	
	//Inventory methods
	@Override
	public int getSizeInventory()
	{
		return container.getSlots();
	}
	
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return container.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return container.extractItem(index, count, false);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return container.extractItem(index, container.getStackInSlot(index).stackSize, false);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		container.insertItem(index, stack, false);
	}
	
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if (this.worldObj.getTileEntity(this.pos) != this) return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}
	
	public int getSlots()
	{
		return container.getSlots();
	}
	
	public ItemStack getSlot(int index)
	{
		return container.getStackInSlot(index);
	}
	
	public ContainerBase getContainer()
	{
		return container;
	}
}
