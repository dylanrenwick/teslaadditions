package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.container.ContainerFurnace;
import com.skidsdev.teslaadditions.container.ContainerStirling;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityGeneratorStirling extends TileEntityMachine
{
	private int burnTime = 0;
	
	private boolean isBurning = false;
	
	public TileEntityGeneratorStirling()
	{
		super(new ContainerStirling());
	}
	
	@Override
	public void update()
	{
		if (!isBurning && getInputStack() != null && TileEntityFurnace.isItemFuel(getInputStack()))
		{
			burnTime += TileEntityFurnace.getItemBurnTime(getInputStack());
			getInputStack().stackSize--;
			
			isBurning = true;
			this.markDirty();
		}
		else if (isBurning)
		{
			burnTime--;
			
			getContainer().generatePower(false);
			
			if (burnTime == 0) isBurning = false;
			this.markDirty();
		}
		
		if (getContainer().updateSlots()) this.markDirty();
	}
	
	public ContainerStirling getContainer()
	{
		return (ContainerStirling)this.container;
	}
	
	private ItemStack getInputStack()
	{
		return getContainer().getStackInSlot(getContainer().inputSlot);
	}
	
	private ItemStack getChargeStack()
	{
		return getContainer().getStackInSlot(getContainer().chargeSlot);
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return null;
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return null;
	}
}
