package com.skidsdev.teslaadditions.tile;

import com.skidsdev.teslaadditions.client.gui.GuiFurnace;
import com.skidsdev.teslaadditions.container.ContainerFurnace;
import com.skidsdev.teslaadditions.guicontainer.GuiContainerFurnace;

import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityElectroFurnace extends TileEntityMachine
{
	private int smeltTime = 200;
	private int burnTime = 0;
	
	private long lastTickPowerLevel = 0;
	
	public TileEntityElectroFurnace()
	{
		super(new ContainerFurnace(), new ItemStackHandler(3));
	}
	
	@Override
	public void update()
	{
		if (canSmelt() && getContainer().consumePower(true))
		{
			this.setIsRunning(true);
			getContainer().consumePower(false);
			burnTime++;
			
			if (burnTime >= smeltTime)
			{
				smeltItem();
				burnTime = 0;
			}
			
			this.markDirty();
		}
		else if (this.getIsRunning())
		{
			this.setIsRunning(false);
			burnTime = 0;
			this.markDirty();
		}
		
		if (this.getStoredPower() < this.getPowerCap())
		{
			ItemStack chargeStack = inventory.getStackInSlot(1);
			
			if (chargeStack != null)
			{
				ITeslaProducer cap = chargeStack.getCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null);
				
				if (cap != null)
				{
					long powerToTake = cap.takePower(20, true);
					
					cap.takePower(getContainer().givePower(powerToTake, false), false);
				}
			}
		}
		
		if (this.getStoredPower() != lastTickPowerLevel) this.markDirty();
		lastTickPowerLevel = this.getStoredPower();
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER)
		{
			return (T) container;
		}
		
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing)
	{
		if (capability == TeslaCapabilities.CAPABILITY_CONSUMER)
		{
			return true;
		}
		
		return super.hasCapability(capability, facing);
	}
	
	public double getProgress()
	{
		return (double)burnTime / (double)smeltTime;
	}
	
	public ContainerFurnace getContainer()
	{
		return (ContainerFurnace)this.container;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		if (compound.hasKey("BurnTime")) burnTime = compound.getInteger("BurnTime");
	}
	
	@Override
	public NBTTagCompound writeToNBT (NBTTagCompound compound)
	{
		compound.setInteger("BurnTime", burnTime);
		return super.writeToNBT(compound);
	}
	
	private void smeltItem()
	{
		if (this.worldObj.isRemote) return;
		
		ItemStack inputStack = inventory.getStackInSlot(0);
		ItemStack outputStack = inventory.getStackInSlot(2);
		ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputStack).copy();
		
		inventory.extractItem(0, 1, false);
		
		inventory.insertItem(2, result, false);
	}
	
	private boolean canSmelt()
	{
		ItemStack inputStack = inventory.getStackInSlot(0);
		ItemStack outputStack = inventory.getStackInSlot(2);
		ItemStack result;
		
		if (inputStack != null && (result = FurnaceRecipes.instance().getSmeltingResult(inputStack)) != null)
		{
			if (outputStack == null) return true;
			
			if (ItemStack.areItemsEqual(result, outputStack) && (outputStack.stackSize + result.stackSize) <= 64)
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Gui getClientGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiFurnace(player.inventory, this);
	}

	@Override
	public Container getServerGuiElement(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		return new GuiContainerFurnace(player.inventory, this);
	}
}
