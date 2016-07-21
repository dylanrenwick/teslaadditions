package com.skidsdev.teslaadditions.capability;

import java.util.Dictionary;
import java.util.Hashtable;

import javax.annotation.Nullable;

import com.skidsdev.teslaadditions.cable.CableNetwork;
import com.skidsdev.teslaadditions.container.ContainerCableInterface;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;

public class MultiCable implements IMultiCable, INBTSerializable<NBTTagCompound>
{
	@Nullable
	private ContainerCableInterface interfaces;
	
	private CableNetwork network;
	
	public boolean hasUpdated;
	
	public MultiCable(NBTTagCompound tag)
	{
		this();
		
		this.deserializeNBT(tag);
	}
	public MultiCable()
	{
		hasUpdated = false;
	}
	
	@Override
	public Dictionary<EnumFacing, TileEntity> getNeighbors(BlockPos pos, World worldIn)
	{
		Dictionary<EnumFacing, TileEntity> tes = new Hashtable<EnumFacing, TileEntity>();
		
		for (EnumFacing facing : EnumFacing.values())
		{
			BlockPos newPos = pos.offset(facing);
			
			TileEntity tileEntity = worldIn.getTileEntity(newPos);
			
			if (tileEntity != null)
			{
				tes.put(facing, tileEntity);
			}
		}
		
		return tes;
	}
	@Override
	public Dictionary<EnumFacing, TileEntity> getNeighborsHaveCapability(Capability capability, BlockPos pos, World worldIn)
	{
		Dictionary<EnumFacing, TileEntity> tes = new Hashtable<EnumFacing, TileEntity>();
		
		for (EnumFacing facing : EnumFacing.values())
		{
			BlockPos newPos = pos.offset(facing);
			
			TileEntity tileEntity = worldIn.getTileEntity(newPos);
			
			if (tileEntity != null && tileEntity.hasCapability(capability, facing.getOpposite()))
			{
				tes.put(facing, tileEntity);
			}
		}
		
		return tes;
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
		hasUpdated = true;
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
	@Override
	public void joinNetwork(TileEntity tileEntity)
	{
		if (tileEntity.hasCapability(TeslaAdditionsCapabilities.MULTI_CABLE, null))
		{
			network.addCableToNetwork(tileEntity);
		}
	}
	@Override
	public void setNetwork(CableNetwork network)
	{
		this.network = network;
	}
	@Override
	public void mergeNetwork(CableNetwork network)
	{
		this.network.mergeWithNetwork(network);
	}
	@Override
	public CableNetwork getNetwork()
	{
		return network;
	}
}
