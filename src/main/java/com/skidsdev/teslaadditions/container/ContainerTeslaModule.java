package com.skidsdev.teslaadditions.container;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;

public class ContainerTeslaModule implements ITeslaHolder, ITeslaConsumer, ITeslaProducer
{
	private long capacity;
	private long storedPower;
	
	public ContainerTeslaModule()
	{
		capacity = 160;
		storedPower = 0;
	}
	
	@Override
	public long takePower(long power, boolean simulated)
	{
		long powerChange =  Math.min(power, storedPower);
		
		if (!simulated) storedPower -= powerChange;
		
		return powerChange;
	}

	@Override
	public long givePower(long power, boolean simulated)
	{
		long powerChange =  Math.min(power, capacity - storedPower);
		
		if (!simulated) storedPower += powerChange;
		
		return powerChange;
	}

	@Override
	public long getCapacity()
	{
		return capacity;
	}

	@Override
	public long getStoredPower()
	{
		return storedPower;
	}
	
}
