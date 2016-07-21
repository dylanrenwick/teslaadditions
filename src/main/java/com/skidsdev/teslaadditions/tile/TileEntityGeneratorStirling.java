package com.skidsdev.teslaadditions.tile;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.teslaadditions.client.gui.GuiStirling;
import com.skidsdev.teslaadditions.container.ContainerStirling;
import com.skidsdev.teslaadditions.guicontainer.GuiContainerStirling;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGeneratorStirling extends TileEntityOutput
{
	private int burnTime = 0;
	private int maxBurnTime = 0;
	
	public TileEntityGeneratorStirling()
	{
		super(new ContainerStirling(), new ItemStackHandler(2));
	}
	
	@Override
	public void update()
	{
		ItemStack inputStack = inventory.getStackInSlot(0);
		
		if (!this.getIsRunning() && inputStack != null && TileEntityFurnace.isItemFuel(inputStack) && container.getStoredPower() < container.getCapacity())
		{
			maxBurnTime = (TileEntityFurnace.getItemBurnTime(inputStack) / 2);
			burnTime = maxBurnTime;
			
			inventory.extractItem(0, 1, false);
			
			this.setIsRunning(true);
			this.markDirty();
		}
		else if (this.getIsRunning())
		{
			burnTime--;
			
			getContainer().generatePower(false);
			
			if (burnTime == 0) this.setIsRunning(false);
			this.markDirty();
		}
		
		super.update();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("BurnTime")) burnTime = compound.getInteger("BurnTime");
		if (compound.hasKey("MaxBurnTime")) maxBurnTime = compound.getInteger("MaxBurnTime");
		this.setIsRunning(burnTime > 0);
	}
	
	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound compound)
	{
		compound.setInteger("BurnTime", burnTime);
		compound.setInteger("MaxBurnTime", maxBurnTime);
		return super.writeToNBT(compound);
	}
	
	public ContainerStirling getContainer()
	{
		return (ContainerStirling)this.container;
	}
	
	public double getTimeRemaining()
	{
		if (maxBurnTime == 0) return 0;
		return (double)burnTime / (double)maxBurnTime;
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiStirling(player.inventory, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiContainerStirling(player.inventory, this);
	}
}
