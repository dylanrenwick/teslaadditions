package com.skidsdev.teslaadditions.item;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegister
{
	public static Item itemBattery;
	public static Item itemCableInterface;
	
	public static void createItems()
	{
		GameRegistry.register(itemBattery = new ItemBattery());
		GameRegistry.register(itemCableInterface = new ItemCableInterface());
	}
}
