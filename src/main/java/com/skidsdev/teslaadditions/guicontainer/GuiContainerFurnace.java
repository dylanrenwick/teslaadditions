package com.skidsdev.teslaadditions.guicontainer;

import com.skidsdev.teslaadditions.client.gui.slot.SlotBattery;
import com.skidsdev.teslaadditions.client.gui.slot.SlotOutput;
import com.skidsdev.teslaadditions.client.gui.slot.SlotSmelt;
import com.skidsdev.teslaadditions.tile.TileEntityElectroFurnace;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class GuiContainerFurnace extends GuiContainerBase
{
	private final int FURNACE_SLOT_COUNT = 3;
	private final int FURNACE_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
	private final int FURNACE_INPUT_SLOT_INDEX = FURNACE_FIRST_SLOT_INDEX + 0;
	private final int FURNACE_CHARGE_SLOT_INDEX = FURNACE_FIRST_SLOT_INDEX + 1;
	private final int FURNACE_OUTPUT_SLOT_INDEX = FURNACE_FIRST_SLOT_INDEX + 2;
	
	public GuiContainerFurnace(InventoryPlayer invPlayer, TileEntityElectroFurnace tileEntity)
	{
		super(invPlayer, tileEntity);
		
		final int INPUT_SLOT_XPOS = 56;
		final int INPUT_SLOT_YPOS = 35;
		
		int slotNumber = 0;
		addSlotToContainer(new SlotSmelt(tileEntity.getInventory(), slotNumber, INPUT_SLOT_XPOS, INPUT_SLOT_YPOS));
		
		final int CHARGE_SLOT_XPOS = 8;
		final int CHARGE_SLOT_YPOS = 64;
		
		slotNumber = 1;
		addSlotToContainer(new SlotBattery(tileEntity.getInventory(), slotNumber, CHARGE_SLOT_XPOS, CHARGE_SLOT_YPOS));
		
		final int OUTPUT_SLOT_XPOS = 116;
		final int OUTPUT_SLOT_YPOS = 35;
		
		slotNumber = 2;
		addSlotToContainer(new SlotOutput(tileEntity.getInventory(), slotNumber, OUTPUT_SLOT_XPOS, OUTPUT_SLOT_YPOS));
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int sourceSlotIndex)
	{
		Slot sourceSlot = (Slot)inventorySlots.get(sourceSlotIndex);
		if (sourceSlot == null || !sourceSlot.getHasStack()) return null;
		ItemStack sourceStack = sourceSlot.getStack();
		ItemStack copyOfSourceStack = sourceStack.copy();
		
		if (sourceSlotIndex < FURNACE_FIRST_SLOT_INDEX || sourceSlotIndex >= FURNACE_FIRST_SLOT_INDEX + FURNACE_SLOT_COUNT)
		{
			if (FurnaceRecipes.instance().getSmeltingResult(sourceStack) != null)
			{
				if (!mergeItemStack(sourceStack, FURNACE_INPUT_SLOT_INDEX, FURNACE_INPUT_SLOT_INDEX + 1, false))
				{
					return null;
				}
			}
			else
			{
				if (sourceStack.hasCapability(TeslaCapabilities.CAPABILITY_PRODUCER, null))
				{
					if (!mergeItemStack(sourceStack, FURNACE_CHARGE_SLOT_INDEX, FURNACE_CHARGE_SLOT_INDEX + 1, false))
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
