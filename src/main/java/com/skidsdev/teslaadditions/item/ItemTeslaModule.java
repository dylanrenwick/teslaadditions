package com.skidsdev.teslaadditions.item;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.teslaadditions.container.ContainerTeslaModule;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraftforge.common.capabilities.Capability;

public class ItemTeslaModule extends ItemBase implements IInterfaceModule
{
	private ContainerTeslaModule container;
	
	public ItemTeslaModule()
	{
		super("itemTeslaModule");
		container = new ContainerTeslaModule();
	}

	@Override
	public <T> T getCapability(Capability<T> capability)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER) return (T)container;
		
		return null;
	}

	@Override
	public <T> boolean hasCapability(Capability<T> capability)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER) return true;
		
		return false;
	}

	@Override
	public List<Capability> getCapabilities()
	{
		List<Capability> caps = new ArrayList<Capability>();
		
		caps.add(TeslaCapabilities.CAPABILITY_CONSUMER);
		
		return caps;
	}

}
