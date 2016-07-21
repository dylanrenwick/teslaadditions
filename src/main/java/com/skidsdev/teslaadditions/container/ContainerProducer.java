package com.skidsdev.teslaadditions.container;

import net.darkhax.tesla.api.ITeslaProducer;

public class ContainerProducer extends ContainerBase implements ITeslaProducer
{
	private long maxOutput;
	
	public ContainerProducer(long capacity) { this(capacity, capacity); }
	public ContainerProducer(long capacity, long maxOut)
	{
		super(capacity);
		
		maxOutput = maxOut;
	}

	@Override
	public long takePower(long power, boolean simulated)
	{
		long takenPower = Math.min(storedPower, Math.min(maxOutput, power));
		
		if (!simulated) storedPower -= takenPower;
		
		return takenPower;
	}
}
