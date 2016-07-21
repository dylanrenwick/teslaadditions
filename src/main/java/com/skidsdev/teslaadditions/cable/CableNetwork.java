package com.skidsdev.teslaadditions.cable;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class CableNetwork
{
	private List<BlockPos> cables;
	private List<TileEntity> tileEntities;
	
	public CableNetwork()
	{
		cables = new ArrayList<BlockPos>();
		tileEntities = new ArrayList<TileEntity>();
	}
	
	public void addCableToNetwork(TileEntity tileEntity)
	{
		if (!cables.contains(tileEntity.getPos())) cables.add(tileEntity.getPos());
	}
	
	public void mergeWithNetwork(CableNetwork network)
	{
		if (network == this) return;
	}
}
