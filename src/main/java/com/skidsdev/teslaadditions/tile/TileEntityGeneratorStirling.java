package com.skidsdev.teslaadditions.tile;

import java.util.ArrayList;
import java.util.List;

import com.skidsdev.teslaadditions.client.gui.GuiStirling;
import com.skidsdev.teslaadditions.container.ContainerStirling;
import com.skidsdev.teslaadditions.guicontainer.GuiContainerStirling;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGeneratorStirling extends TileEntityMachine
{
	private int burnTime = 0;
	
	private boolean isBurning = false;
	
	public TileEntityGeneratorStirling()
	{
		super(new ContainerStirling(), new ItemStackHandler(2));
	}
	
	@Override
	public void update()
	{
		ItemStack inputStack = inventory.getStackInSlot(0);
		
		if (!isBurning && inputStack != null && TileEntityFurnace.isItemFuel(inputStack))
		{
			burnTime += TileEntityFurnace.getItemBurnTime(inputStack);
			inputStack.stackSize -= 1;
			
			if (inputStack.stackSize <= 0) inventory.insertItem(0, null, false);
			
			isBurning = true;
			this.markDirty();
		}
		else if (isBurning)
		{
			burnTime--;
			
			getContainer().generatePower(false);
			
			if (burnTime == 0) isBurning = false;
			this.markDirty();
		}
		
		if (container.getStoredPower() > 0)
		{
			List<ITeslaConsumer> consumers = new ArrayList<ITeslaConsumer>();
			
			for (int x = -1; x < 2; x += 2)
			{
				BlockPos searchPos = this.pos.add(x, 0, 0);
				
				TileEntity tileEntity = this.worldObj.getTileEntity(searchPos);
				
				if (tileEntity != null)
				{
					EnumFacing facing = x > 0 ? EnumFacing.WEST : EnumFacing.EAST;
					ITeslaConsumer cap = tileEntity.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing);
					
					if (cap != null) consumers.add(cap);
				}
			}
			
			for (int y = -1; y < 2; y += 2)
			{
				BlockPos searchPos = this.pos.add(0, y, 0);
				
				TileEntity tileEntity = this.worldObj.getTileEntity(searchPos);
				
				if (tileEntity != null)
				{
					EnumFacing facing = y > 0 ? EnumFacing.DOWN : EnumFacing.UP;
					ITeslaConsumer cap = tileEntity.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing);
					
					if (cap != null) consumers.add(cap);
				}
				
			}
			
			for (int z = -1; z < 2; z += 2)
			{

				BlockPos searchPos = this.pos.add(0, 0, z);
				
				TileEntity tileEntity = this.worldObj.getTileEntity(searchPos);
				
				if (tileEntity != null)
				{
					EnumFacing facing = z > 0 ? EnumFacing.NORTH : EnumFacing.SOUTH;
					ITeslaConsumer cap = tileEntity.getCapability(TeslaCapabilities.CAPABILITY_CONSUMER, facing);
					
					if (cap != null) consumers.add(cap);
				}
			}
			
			if (!consumers.isEmpty())
			{
				for(ITeslaConsumer consumer : consumers)
				{
					long powerToGive = consumer.givePower(container.getStoredPower(), true);
					
					consumer.givePower(getContainer().takePower(powerToGive, false), false);
				}
				
				markDirty();
			}
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_PRODUCER)
		{
			return (T) container;
		}
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_PRODUCER)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}
	
	public ContainerStirling getContainer()
	{
		return (ContainerStirling)this.container;
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiStirling(player.inventory, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiContainerStirling(player.inventory, this);
	}
}
