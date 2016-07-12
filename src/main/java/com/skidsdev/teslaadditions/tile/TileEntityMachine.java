package com.skidsdev.teslaadditions.tile;

import javax.annotation.Nullable;

import com.skidsdev.teslaadditions.client.gui.IOpenableGUI;
import com.skidsdev.teslaadditions.container.ContainerBase;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public abstract class TileEntityMachine extends TileEntity implements ITickable, IOpenableGUI
{
	protected final ContainerBase container;
	protected final ItemStackHandler inventory;
	
	public TileEntityMachine(ContainerBase container, ItemStackHandler inventory)
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
		if (compound.hasKey("TeslaContainer")) container.deserializeNBT((NBTTagCompound)compound.getTag("TeslaContainer"));
		if (compound.hasKey("Inventory")) inventory.deserializeNBT((NBTTagCompound)compound.getTag("Inventory"));
	}
	
	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound compound)
	{
		compound.setTag("TeslaContainer", this.container.serializeNBT());
		compound.setTag("Inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

    @Override
    public NBTTagCompound getUpdateTag()
    {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
        return tag;
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }
    
	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket()
	{
	    return new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
	}
	
	@Override
	public void onDataPacket (NetworkManager net, SPacketUpdateTileEntity packet)
	{
	    super.onDataPacket(net, packet);
	    this.readFromNBT(packet.getNbtCompound());
	}
	
	public long getStoredPower()
	{
		return container.getStoredPower();
	}
	
	public long getPowerCap()
	{
		return container.getCapacity();
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
