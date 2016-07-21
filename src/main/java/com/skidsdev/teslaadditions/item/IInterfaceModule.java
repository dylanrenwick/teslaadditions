package com.skidsdev.teslaadditions.item;

import java.util.List;

import net.minecraftforge.common.capabilities.Capability;

public interface IInterfaceModule
{
	public <T> T getCapability(Capability<T> capability);
	
	public <T> boolean hasCapability(Capability<T> capability);
	
	public List<Capability> getCapabilities();
}
