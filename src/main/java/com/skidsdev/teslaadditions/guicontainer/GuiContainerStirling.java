package com.skidsdev.teslaadditions.guicontainer;

import com.skidsdev.teslaadditions.client.gui.slot.SlotCharge;
import com.skidsdev.teslaadditions.client.gui.slot.SlotFuel;
import com.skidsdev.teslaadditions.tile.TileEntityGeneratorStirling;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class GuiContainerStirling extends GuiContainerBase
{
	private final int STIRLING_SLOT_COUNT = 2;
	private final int STIRLING_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int STIRLING_INPUT_SLOT_INDEX = STIRLING_FIRST_SLOT_INDEX + 0;
	private final int STIRLING_CHARGE_SLOT_INDEX = STIRLING_FIRST_SLOT_INDEX + 1;
	
	public GuiContainerStirling(InventoryPlayer invPlayer, TileEntityGeneratorStirling tileEntity)
	{
		super(invPlayer, tileEntity);
		
		final int INPUT_SLOT_XPOS = 80;
		final int INPUT_SLOT_YPOS = 35;
		
		int slotNumber = 0;
		addSlotToContainer(new SlotFuel(tileEntity.getInventory(), slotNumber, INPUT_SLOT_XPOS, INPUT_SLOT_YPOS));
		
		final int CHARGE_SLOT_XPOS = 8;
		final int CHARGE_SLOT_YPOS = 64;
		
		slotNumber = 1;
		addSlotToContainer(new SlotCharge(tileEntity.getInventory(), slotNumber, CHARGE_SLOT_XPOS, CHARGE_SLOT_YPOS));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return null;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();
		
		if (sourceSlotIndex < STIRLING_FIRST_SLOT_INDEX || sourceSlotIndex >= STIRLING_FIRST_SLOT_INDEX + STIRLING_SLOT_COUNT)
		{
			if (TileEntityFurnace.isItemFuel(sourceStack))
			{
				if (!mergeItemStack(sourceStack, STIRLING_INPUT_SLOT_INDEX, STIRLING_INPUT_SLOT_INDEX + 1, false))
				{
					return null;
				}
			}
			else
			{
				if (sourceStack.hasCapability(TeslaCapabilities.CAPABILITY_CONSUMER, null))
				{
					if (!mergeItemStack(sourceStack, STIRLING_CHARGE_SLOT_INDEX, STIRLING_CHARGE_SLOT_INDEX + 1, false))
					{
						return null;
					}
				}
				else
				{
					return null;
				}
			}
		}
		else
		{
			return super.transferStackInSlot(player, sourceSlotIndex);
		}
		
		if (sourceStack.stackSize == 0) {
			sourceSlot.putStack(null);
		} else {
			sourceSlot.onSlotChanged();
		}

		sourceSlot.onPickupFromSlot(player, sourceStack);
		return copyOfSourceStack;
	}
}
