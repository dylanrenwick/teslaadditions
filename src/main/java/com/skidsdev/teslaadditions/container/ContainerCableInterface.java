package com.skidsdev.teslaadditions.container;

import java.util.Dictionary;
import java.util.Hashtable;

import com.skidsdev.teslaadditions.capability.ICableInterface;
import com.skidsdev.teslaadditions.capability.ICableInterfaceContainer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;

public class ContainerCableInterface implements INBTSerializable<NBTTagCompound>, ICableInterfaceContainer
{
	private Dictionary<EnumFacing, CableInterface> interfaces;
	
	public ContainerCableInterface(NBTTagCompound tag)
	{
		this();
		
		this.deserializeNBT(tag);
	}
	public ContainerCableInterface(EnumFacing facing)
	{
		this();
		
		interfaces.put(facing, new CableInterface());
	}
	public ContainerCableInterface()
	{
		interfaces = new Hashtable<EnumFacing, CableInterface>();
	}
	
	public void addInterface(EnumFacing facing)
	{
		if (!hasInterface(facing)) interfaces.put(facing, new CableInterface());
	}
	
	@Override
	public boolean hasInterface(EnumFacing facing)
	{
		return interfaces.get(facing) != null;
	}
	
	public void update()
	{
		if (!interfaces.isEmpty())
		{
			for (EnumFacing facing : EnumFacing.values())
			{
				if (hasInterface(facing))
				{
					interfaces.get(facing).update(facing);
				}
			}
		}
	}
	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		for (EnumFacing facing : EnumFacing.values())
		{
			if (hasInterface(facing))
			{
				tag.setTag(facing.getName(), interfaces.get(facing).serializeNBT());
			}
		}
		
		return tag;
	}
	@Override
	public void deserializeNBT(NBTTagCompound nbt) 
	{
		for (EnumFacing facing : EnumFacing.values())
		{
			if (nbt.hasKey(facing.getName())) interfaces.put(facing, new CableInterface((NBTTagCompound)nbt.getTag(facing.getName())));
		}
	}
	
	@Override
	public ICableInterface getInterface(EnumFacing facing)
	{
		return interfaces.get(facing);
	}
}
