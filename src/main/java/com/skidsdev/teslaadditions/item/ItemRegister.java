package com.skidsdev.teslaadditions.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegister
{
	public static Item itemBattery;
	//public static Item itemCableInterface;
	
	public static List<Item> registeredItems;
	
	public static void createItems()
	{
		registeredItems = new ArrayList<Item>();
		
		itemBattery = registerItem(new ItemBattery());
		//GameRegistry.register(itemCableInterface = new ItemCableInterface());
	}
	
	private static Item registerItem(Item item)
	{
		GameRegistry.register(item);
		
		registeredItems.add(item);
		
		return item;
	}
}
