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

public class TileEntityGeneratorStirling extends TileEntityMachine
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
		
		if (container.getStoredPower() > 0)
		{
			List<ITeslaConsumer> consumers = new ArrayList<ITeslaConsumer>();
			
			ItemStack chargeStack = inventory.getStackInSlot(1);
			
			if (chargeStack != null)
			{
				ITeslaConsumer cap = chargeStack.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null);
				
				if (cap != null) consumers.add(cap);
			}
			
			List<ITeslaConsumer> neighbors = this.getNeighborCaps(TeslaCapabilities.CAPABILITY_CONSUMER, pos, this.worldObj);
			
			for (ITeslaConsumer neighbor : neighbors)
			{
				if (neighbor != null) consumers.add(neighbor);
			}
			
			if (!consumers.isEmpty())
			{
				for(ITeslaConsumer consumer : consumers)
				{
					long powerToGive = consumer.givePower(container.getStoredPower(), true);
					
					consumer.givePower(getContainer().takePower(powerToGive, false), false);
				}
				
				markDirty();
			}
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_PRODUCER)
		{
			return (T) container;
		}
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_PRODUCER)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
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
