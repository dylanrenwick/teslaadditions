package com.skidsdev.teslaadditions.container;

import com.skidsdev.teslaadditions.Config;

import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;

public class ContainerStirling extends ContainerProducer
{
	private long maxGen;
	
	public ContainerStirling()
	{
		super(Config.stirlingBasePowerCap, Config.stirlingBasePowerOut);
		maxGen = Config.stirlingBasePowerGen;
	}
	
	public long generatePower(boolean simulated)
	{
		long generatedPower = Math.min((getCapacity() - storedPower), maxGen);
		
		if (!simulated) storedPower += generatedPower;
		
		return generatedPower;
	}
}
