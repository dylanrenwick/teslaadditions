package com.skidsdev.teslaadditions.container;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;

public class ContainerBattery extends ContainerProducer implements ITeslaConsumer
{
	public ContainerBattery(long capacity)
	{
		super(capacity);
	}

	@Override
	public long givePower(long power, boolean simulated)
	{
		long powerChange = Math.min(power, this.capacity - this.storedPower);
		
		if (!simulated) this.storedPower += powerChange;
		
		return powerChange;
	}

}
