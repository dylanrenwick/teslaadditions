package com.skidsdev.teslaadditions.container;

import com.skidsdev.teslaadditions.Config;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaProducer;

public class ContainerBattery extends ContainerBase implements ITeslaConsumer, ITeslaProducer
{
	public ContainerBattery()
	{
		super(Config.batteryBasePower);
	}

	@Override
	public long takePower(long power, boolean simulated)
	{
		long powerChange = Math.min(power, this.storedPower);
		
		if (!simulated) this.storedPower -= powerChange;
		
		return powerChange;
	}

	@Override
	public long givePower(long power, boolean simulated)
	{
		long powerChange = Math.min(power, this.capacity - this.storedPower);
		
		if (!simulated) this.storedPower += powerChange;
		
		return powerChange;
	}

}
