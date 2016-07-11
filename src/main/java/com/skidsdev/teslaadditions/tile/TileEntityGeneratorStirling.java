package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.container.ContainerStirling;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGeneratorStirling extends TileEntityMachine
{
	private int burnTime = 0;
	
	private boolean isBurning = false;
	
	public TileEntityGeneratorStirling()
	{
		super(new ContainerStirling(), new ItemStackHandler(2));
	}
	
	@Override
	public void update()
	{
		ItemStack inputStack = inventory.getStackInSlot(0);
		
		if (!isBurning && inputStack != null && TileEntityFurnace.isItemFuel(inputStack))
		{
			burnTime += TileEntityFurnace.getItemBurnTime(inputStack);
			inputStack.stackSize -= 1;
			
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
	}
	
	public ContainerStirling getContainer()
	{
		return (ContainerStirling)this.container;
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
