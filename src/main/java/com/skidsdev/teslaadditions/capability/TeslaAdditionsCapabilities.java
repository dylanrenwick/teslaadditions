package com.skidsdev.teslaadditions.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class TeslaAdditionsCapabilities
{
	@CapabilityInject(IMultiCable.class)
	public static Capability<IMultiCable> MULTI_CABLE = null;
	
	public static void registerCapabilities()
	{
		CapabilityManager.INSTANCE.register(IMultiCable.class, new CapabilityMultiCable<IMultiCable>(), MultiCable.class);
	}
	
	public static class CapabilityMultiCable<T extends IMultiCable> implements IStorage<IMultiCable>
	{
		@Override
		public NBTBase writeNBT(Capability<IMultiCable> capability, IMultiCable instance, EnumFacing side)
		{
			return null;
		}
		
		@Override
		public void readNBT(Capability<IMultiCable> capability, IMultiCable instance, EnumFacing side, NBTBase nbt)
		{
			
		}
	}
}
