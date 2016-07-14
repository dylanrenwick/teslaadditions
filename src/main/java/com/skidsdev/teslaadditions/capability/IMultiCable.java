package com.skidsdev.teslaadditions.capability;

import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IMultiCable
{
	List<TileEntity> getNeighbors(BlockPos pos, World worldIn);
	
	public ICableInterface getCableInterface(EnumFacing facing);
	
	public boolean hasCableInterface(EnumFacing facing);
	
	public boolean hasInterfaces();
	
	public void addCableInterface(EnumFacing facing);
	
	public void update();
}
