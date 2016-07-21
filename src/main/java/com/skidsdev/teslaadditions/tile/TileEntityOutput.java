package com.skidsdev.teslaadditions.tile;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.teslaadditions.container.ContainerProducer;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityOutput extends TileEntityMachine
{
	public TileEntityOutput(ContainerProducer container, ItemStackHandler inventory)
	{
		super(container, inventory);
	}
	
	@Override
	public void update()
	{
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
	
	private ContainerProducer getContainer()
	{
		return (ContainerProducer)container;
	}
}
