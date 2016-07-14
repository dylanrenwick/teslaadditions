package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.capability.MultiCable;
import com.skidsdev.teslaadditions.capability.TeslaAdditionsCapabilities;
import com.skidsdev.teslaadditions.client.gui.GuiCable;
import com.skidsdev.teslaadditions.client.gui.IOpenableGUI;
import com.skidsdev.teslaadditions.guicontainer.GuiContainerCable;

import net.minecraft.block.state.IBlockState;
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

public class TileEntityMultiCable extends TileEntity implements ITickable, IOpenableGUI
{
	private MultiCable container;
	
	public TileEntityMultiCable()
	{
		super();
		
		container = new MultiCable();
	}
	
	public boolean hasInterface(EnumFacing facing)
	{
		return container.hasCableInterface(facing);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		
		if (tag.hasKey("Interfaces"))
		{
			container = new MultiCable((NBTTagCompound)tag.getTag("Interfaces"));
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		NBTTagCompound interfaces = container.serializeNBT();
		
		if (interfaces != null) tag.setTag("Interfaces", interfaces);
		
		return super.writeToNBT(tag);
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
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		return capability == TeslaAdditionsCapabilities.MULTI_CABLE ||
			   super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == TeslaAdditionsCapabilities.MULTI_CABLE)
		{
			return (T)this.container;
		}
		
		return super.getCapability(capability, facing);
	}

	@Override
	public void update()
	{
		container.update();
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		if (container.hasInterfaces())
		{
			return new GuiCable(player.inventory, this);
		}
		return null;
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		if (container.hasInterfaces())
		{
			return new GuiContainerCable(player.inventory, this);
		}		
		return null;
	}
}
