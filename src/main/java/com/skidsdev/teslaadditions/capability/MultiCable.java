package com.skidsdev.teslaadditions.capability;

import java.util.List;

import javax.annotation.Nullable;

import com.skidsdev.teslaadditions.container.ContainerCableInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

public class MultiCable implements IMultiCable, INBTSerializable<NBTTagCompound>
{
	@Nullable
	private ContainerCableInterface interfaces;
	
	public MultiCable(NBTTagCompound tag)
	{
		this();
		
		this.deserializeNBT(tag);
	}
	public MultiCable()
	{
		
	}
	
	@Override
	public List<TileEntity> getNeighbors(BlockPos pos, World worldIn)
	{
		return null;
	}

	@Override
	public ICableInterface getCableInterface(EnumFacing facing)
	{
		if (interfaces != null) return interfaces.getInterface(facing);
		return null;
	}

	@Override
	public boolean hasCableInterface(EnumFacing facing)
	{
		if (interfaces != null) return interfaces.hasInterface(facing);
		return false;
	}
	
	@Override
	public boolean hasInterfaces()
	{
		if (interfaces != null)
		{
			for(EnumFacing facing : EnumFacing.values())
			{
				if (interfaces.hasInterface(facing)) return true;
			}
		}
		return false;
	}

	@Override
	public void addCableInterface(EnumFacing facing)
	{
		if (interfaces != null) interfaces.addInterface(facing);
		else
		{
			interfaces = new ContainerCableInterface(facing);
		}
	}
	
	@Override
	public void update()
	{
		if (interfaces != null) interfaces.update();
	}

	@Override
	public NBTTagCompound serializeNBT()
	{		
		if (interfaces != null) return interfaces.serializeNBT();
		
		return null;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		if (interfaces != null) interfaces.deserializeNBT(nbt);
		else interfaces = new ContainerCableInterface(nbt); 
	}
}
