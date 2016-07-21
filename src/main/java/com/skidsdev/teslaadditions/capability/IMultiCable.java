package com.skidsdev.teslaadditions.capability;

import java.util.Dictionary;

import com.skidsdev.teslaadditions.cable.CableNetwork;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public interface IMultiCable
{
	Dictionary<EnumFacing, TileEntity> getNeighbors(BlockPos pos, World worldIn);
	Dictionary<EnumFacing, TileEntity> getNeighborsHaveCapability(Capability capability, BlockPos pos, World worldIn);
	
	public ICableInterface getCableInterface(EnumFacing facing);
	
	public void setNetwork(CableNetwork network);
	
	public CableNetwork getNetwork();
	
	public void joinNetwork(TileEntity tileEntity);
	
	public void mergeNetwork(CableNetwork network);
	
	public boolean hasCableInterface(EnumFacing facing);
	
	public boolean hasInterfaces();
	
	public void addCableInterface(EnumFacing facing);
	
	public void update();
}
