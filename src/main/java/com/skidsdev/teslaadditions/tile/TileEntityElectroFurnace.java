package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.client.gui.GuiFurnace;
import com.skidsdev.teslaadditions.container.ContainerFurnace;
import com.skidsdev.teslaadditions.utils.Helper;

import guicontainer.GuiContainerFurnace;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityElectroFurnace extends TileEntityMachine
{
	private int smeltTime = 200;
	private int burnTime = 0;
	
	private boolean isBurning = false;
	
	public TileEntityElectroFurnace()
	{
		super(new ContainerFurnace());
	}
	
	@Override
	public void update()
	{
		if (canSmelt() && getContainer().consumePower(false))
		{
			isBurning = true;
			
			burnTime++;
			
			if (burnTime >= smeltTime)
			{
				smeltItem();
				burnTime = 0;
			}
			
			this.markDirty();
		}
		else if (isBurning)
		{		
			isBurning = false;
			this.markDirty();
		}
		
		if (getContainer().updateSlots()) this.markDirty();
	}
	
	public ContainerFurnace getContainer()
	{
		return (ContainerFurnace)this.container;
	}
	
	private void smeltItem()
	{
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(getInputStack());
		
		getInputStack().stackSize -= 1;
		
		if (getOutputStack() != null)
		{
			getOutputStack().stackSize += result.stackSize;
		}
		else
		{
			getContainer().insertItem(getContainer().outputSlot, result, false);
		}
	}
	
	private boolean canSmelt()
	{
		//No input item
		if (getInputStack() == null)
		{
			return false;
		}
		//Has input item
		else
		{
			ItemStack inputStack = getInputStack();
			//Input item is not smeltable
			if (FurnaceRecipes.instance().getSmeltingResult(inputStack) == null) return false;
			
			ItemStack outputStack = getOutputStack();
			if (outputStack == null) return true;
			
			if (Helper.areItemStacksStackable(inputStack, outputStack))
			{
				return true;
			}
			else return false;
		}
	}
	
	private ItemStack getInputStack()
	{
		return getContainer().getStackInSlot(getContainer().inputSlot);
	}
	
	private ItemStack getChargeStack()
	{
		return getContainer().getStackInSlot(getContainer().chargeSlot);
	}
	
	private ItemStack getOutputStack()
	{
		return getContainer().getStackInSlot(getContainer().outputSlot);
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiFurnace(player.inventory, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiContainerFurnace(player.inventory, this);
	}
}
