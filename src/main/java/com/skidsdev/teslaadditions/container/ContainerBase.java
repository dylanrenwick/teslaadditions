package com.skidsdev.teslaadditions.container;

import net.darkhax.tesla.api.ITeslaHolder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public class ContainerBase implements ITeslaHolder, INBTSerializable<NBTTagCompound>
{
	protected long storedPower;
	protected long capacity;
	
	public ContainerBase(long capacity)
	{
		this.capacity = capacity;
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

    @Override
    public NBTTagCompound serializeNBT()
    {
        final NBTTagCompound dataTag = new NBTTagCompound();
        dataTag.setLong("TeslaPower", this.storedPower);
        dataTag.setLong("TeslaCapacity", this.capacity);
        
        return dataTag;
    }
    
    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        this.storedPower = nbt.getLong("TeslaPower");
        
        if (nbt.hasKey("TeslaCapacity"))
            this.capacity = nbt.getLong("TeslaCapacity");
            
        if (this.storedPower > this.capacity)
            this.storedPower = this.capacity;
    }
}
