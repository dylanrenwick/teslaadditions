package com.skidsdev.teslaadditions.container;

import com.skidsdev.teslaadditions.Config;

import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;

public class ContainerStirling extends ContainerBase implements ITeslaProducer
{
	public ContainerStirling()
	{
		super(Config.stirlingBasePowerCap);
	}

	@Override
	public long takePower(long power, boolean simulated)
	{
		long takenPower = Math.min(storedPower, Math.min(Config.stirlingBasePowerOut, power));
		
		if (!simulated) storedPower -= takenPower;
		
		return takenPower;
	}
	
	public long generatePower(boolean simulated)
	{
		long generatedPower = Math.min((getCapacity() - storedPower), Config.stirlingBasePowerGen);
		
		if (!simulated) storedPower += generatedPower;
		
		return generatedPower;
	}
	
/*	private ItemStack insertIntoChargeSlot(ItemStack stack, boolean simulated)
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
	}*/
}
