package com.skidsdev.teslaadditions.capability;

import java.util.Dictionary;

import net.minecraft.util.EnumFacing;

public interface ICableInterfaceContainer
{
	public ICableInterface getInterface(EnumFacing facing);
	
	public boolean hasInterface(EnumFacing facing);
	
	public void addInterface(EnumFacing facing);
	
	public void update();
}
