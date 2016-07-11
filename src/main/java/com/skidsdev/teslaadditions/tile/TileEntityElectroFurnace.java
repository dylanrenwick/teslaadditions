package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.client.gui.GuiFurnace;
import com.skidsdev.teslaadditions.container.ContainerFurnace;
import com.skidsdev.teslaadditions.guicontainer.GuiContainerFurnace;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class TileEntityElectroFurnace extends TileEntityMachine
{
	private int smeltTime = 200;
	private int burnTime = 0;
	
	private boolean isBurning = false;
	
	public TileEntityElectroFurnace()
	{
		super(new ContainerFurnace(), new ItemStackHandler(3));
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
	}
	
	public ContainerFurnace getContainer()
	{
		return (ContainerFurnace)this.container;
	}
	
	private void smeltItem()
	{
		ItemStack inputStack = inventory.getStackInSlot(0);
		ItemStack outputStack = inventory.getStackInSlot(2);
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputStack);
		
		inputStack.stackSize -= 1;
		
		inventory.insertItem(2, result, false);
	}
	
	private boolean canSmelt()
	{
		ItemStack inputStack = inventory.getStackInSlot(0);
		ItemStack outputStack = inventory.getStackInSlot(2);
		ItemStack result;
		
		if (inputStack != null && (result = FurnaceRecipes.instance().getSmeltingResult(inputStack)) != null)
		{
			if (outputStack == null) return true;
			
			if (ItemStack.areItemsEqual(result, outputStack) && (outputStack.stackSize + result.stackSize) <= 64)
			{
				return true;
			}
		}
		
		return false;
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
