package com.skidsdev.teslaadditions.container;

import com.skidsdev.teslaadditions.capability.ICableInterface;
import com.skidsdev.teslaadditions.client.gui.IOpenableGUI;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.ItemStackHandler;

public class CableInterface implements IOpenableGUI, INBTSerializable<NBTTagCompound>, ICableInterface
{
	protected ItemStackHandler inventory;
	
	public CableInterface(NBTTagCompound tag)
	{
		this();
		
		this.deserializeNBT(tag);
	}
	public CableInterface()
	{
		inventory = new ItemStackHandler();
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return null;
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return null;
	}
	
	@Override
	public void update(EnumFacing facing)
	{
		
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setTag("Inventory", inventory.serializeNBT());
		
		return tag;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("Inventory")) inventory.deserializeNBT((NBTTagCompound)nbt.getTag("Inventory"));
	}
}
