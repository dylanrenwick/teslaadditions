package com.skidsdev.teslaadditions.item;

import java.util.List;

import com.skidsdev.teslaadditions.container.ContainerBattery;
import com.skidsdev.teslaadditions.container.ContainerBatteryProvider;

import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class ItemBattery extends ItemBase
{
	public ItemBattery()
	{
		super("itemBattery");
		this.setMaxStackSize(1);
		
	}
    
    @Override
    public void addInformation (ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        
        super.addInformation(stack, playerIn, tooltip, advanced);
        final ContainerBattery container = (ContainerBattery) stack.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, null);
        
        tooltip.add(container.getStoredPower() + "/" + container.getCapacity() + " T");
    }
    
    @Override
    public ICapabilityProvider initCapabilities (ItemStack stack, NBTTagCompound nbt)
    {
        return new ContainerBatteryProvider(new ContainerBattery());
    }
}
