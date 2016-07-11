package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.client.gui.IOpenableGUI;
import com.skidsdev.teslaadditions.container.ContainerBase;
import com.skidsdev.teslaadditions.utils.Helper;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class TileEntityMachine extends TileEntity implements ITickable, IOpenableGUI
{
	protected final ContainerBase container;
	protected final IItemHandler inventory;
	
	public TileEntityMachine(ContainerBase container, IItemHandler inventory)
	{
		this.container = container;
		this.inventory = inventory;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return (T) inventory;
		}
		else if (capability == TeslaCapabilities.CAPABILITY_HOLDER)
		{
			return (T) container;
		}
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
				capability == TeslaCapabilities.CAPABILITY_HOLDER)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}

	@Override
	public abstract Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos);

	@Override
	public abstract Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos);
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		container.deserializeNBT(compound);
		if (compound.hasKey("Inventory")) Helper.deserializeItemHandler((NBTTagCompound)compound.getTag("Inventory"), inventory);
	}
	
	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound compound)
	{
		compound.setTag("TeslaContainer", this.container.serializeNBT());
		
		compound.setTag("Inventory", Helper.serializeItemHandler(compound, inventory));
		
		return super.writeToNBT(compound);
	}
	
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		if (this.worldObj.getTileEntity(this.pos) != this) return false;
		final double X_CENTRE_OFFSET = 0.5;
		final double Y_CENTRE_OFFSET = 0.5;
		final double Z_CENTRE_OFFSET = 0.5;
		final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
		return player.getDistanceSq(pos.getX() + X_CENTRE_OFFSET, pos.getY() + Y_CENTRE_OFFSET, pos.getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
	}
	
	public IItemHandler getInventory()
	{
		return inventory;
	}
}
