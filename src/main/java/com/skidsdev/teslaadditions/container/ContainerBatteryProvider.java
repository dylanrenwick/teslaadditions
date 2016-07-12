package com.skidsdev.teslaadditions.container;

import com.skidsdev.teslaadditions.Config;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class ContainerBatteryProvider implements INBTSerializable<NBTTagCompound>, ICapabilityProvider
{
	private final ContainerBattery container;
	
	public ContainerBatteryProvider(ContainerBattery container)
	{
		this.container = container;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == TeslaCapabilities.CAPABILITY_CONSUMER ||
			   capability == TeslaCapabilities.CAPABILITY_HOLDER  ||
			   capability == TeslaCapabilities.CAPABILITY_PRODUCER;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER ||
			   capability == TeslaCapabilities.CAPABILITY_HOLDER  ||
			   capability == TeslaCapabilities.CAPABILITY_PRODUCER)
		{
			return (T)this.container;
		}
		
		return null;
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		return this.container.serializeNBT();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		this.container.deserializeNBT(nbt);
	}

}
