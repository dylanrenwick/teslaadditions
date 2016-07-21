package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.Config;
import com.skidsdev.teslaadditions.client.gui.GuiCapacitor;
import com.skidsdev.teslaadditions.container.ContainerBattery;
import com.skidsdev.teslaadditions.guicontainer.GuiContainerCapacitor;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCapacitor extends TileEntityOutput
{
	public TileEntityCapacitor()
	{
		super(new ContainerBattery(Config.capacitorBasePowerCap), new ItemStackHandler(5));
	}
	
	public ContainerBattery getContainer()
	{
		return (ContainerBattery)container;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_PRODUCER || capability == TeslaCapabilities.CAPABILITY_CONSUMER)
		{
			return (T) container;
		}
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER ||
				capability == TeslaCapabilities.CAPABILITY_PRODUCER)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiCapacitor(player.inventory, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiContainerCapacitor(player.inventory, this);
	}
}
