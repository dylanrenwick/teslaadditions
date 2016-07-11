package com.skidsdev.teslaadditions.container;

import com.skidsdev.teslaadditions.Config;

import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.items.IItemHandler;

public class ContainerFurnace extends ContainerBase implements ITeslaConsumer
{
	public static final int inputSlot = 0;
	public static final int chargeSlot = 1;
	public static final int outputSlot = 2;
	
	public ContainerFurnace()
	{
		slots = new ItemStack[3];
	}

	@Override
	public long givePower(long power, boolean simulated)
	{
		long removedPower = Math.min(power, this.getCapacity() - this.storedPower);
		
		if (!simulated)	this.storedPower += removedPower;
		
		return removedPower;
	}

	@Override
	public long getCapacity()
	{
		return Config.furnaceBasePowerCap;
	}
	
	public boolean consumePower(boolean simulated)
	{
		if (!simulated)
		{
			storedPower -= Config.furnaceBasePowerUse;
			return storedPower >= Config.furnaceBasePowerUse;
		}
		else
		{
			return storedPower - Config.furnaceBasePowerUse >= Config.furnaceBasePowerUse;
		}
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulated)
	{
		if (slot < slots.length && slot >= 0)
		{
			if (stack == null)
			{
				if (!simulated) slots[slot] = stack;
			}
			//Inserting into chargeSlot, and item can provide power
			else if (slot == chargeSlot && stack.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null))
			{
				return insertIntoChargeSlot(stack, simulated);
			}
			//Inserting into inputSlot, and item can be smelted
			else if (slot == inputSlot && FurnaceRecipes.instance().getSmeltingResult(stack) != null)
			{
				return insertIntoInputSlot(stack, simulated);
			}
		}
		
		return stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate)
	{
		if (slot < slots.length && slot >= 0)
		{
			ItemStack stack = slots[slot];
			
			if (!simulate) slots[slot] = null;
			
			return stack;
		}
		
		return null;
	}
	
	private ItemStack insertIntoChargeSlot(ItemStack stack, boolean simulated)
	{
		//No item currently in slot
		if (slots[chargeSlot] == null)
		{
			//Trying to insert more than max stackSize into slot
			if (stack.stackSize > 1)
			{
				ItemStack newStack = stack.copy();
				newStack.stackSize -= 1;
				
				if (!simulated)
				{
					stack.stackSize = 1;
					slots[chargeSlot] = stack;
				}
				
				return newStack;
			}
			//Trying to insert max stackSize or less into slot
			else
			{
				if (!simulated)	slots[chargeSlot] = stack;
				
				return null;
			}
		}
		//Another item currently in slot
		else
		{
			//Trying to insert more than max stackSize into slot
			if (stack.stackSize == 1)
			{
				ItemStack newStack = slots[chargeSlot];
				
				if (!simulated) slots[chargeSlot] = stack;
				
				return newStack;
			}
		}
		
		return stack;
	}
	
	private ItemStack insertIntoInputSlot(ItemStack stack, boolean simulated)
	{
		//No item currently in slot
		if (slots[inputSlot] == null)
		{
			//Trying to insert max stackSize or less into slot
			if (stack.stackSize <= 64)
			{
				if (!simulated) slots[inputSlot] = stack;
				
				return null;
			}
			//Trying to insert more than max stackSize into slot
			else
			{
				ItemStack newStack = stack.copy();
				newStack.stackSize -= 64;
				
				if (!simulated)
				{
					stack.stackSize = 64;
					slots[inputSlot] = stack;
				}
				
				return newStack;
			}
		}
		//Another item currently in slot
		else
		{
			//Trying to insert max stackSize or less into slot
			if (stack.stackSize <= 64)
			{
				ItemStack newStack = slots[inputSlot];
				
				//Inserted stack can be combined with existing stack
				if (ItemStack.areItemsEqual(stack, newStack) && ItemStack.areItemStackTagsEqual(stack, newStack))
				{
					if (stack.stackSize + newStack.stackSize <= 64)
					{
						if (!simulated) newStack.stackSize += stack.stackSize;
						
						return null;
					}
					else
					{
						stack.stackSize -= 64 - newStack.stackSize;
						
						if (!simulated) newStack.stackSize = 64;
						
						return stack;
					}
				}
				//Inserted stack cannot be combined with existing stack
				else
				{
					if (!simulated) slots[inputSlot] = stack;
					
					return newStack;
				}
			}
		}
		
		return stack;
	}
}
