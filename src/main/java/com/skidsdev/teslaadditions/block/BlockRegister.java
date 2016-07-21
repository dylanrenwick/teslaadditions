package com.skidsdev.teslaadditions.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegister
{
	public static Block blockElectroFurnace;
	public static Block blockGeneratorStirling;
	public static Block blockCapacitor;
	//public static Block blockMultiCable;
	
	public static List<Block> registeredBlocks;
	
	public static void createBlocks()
	{
		registeredBlocks = new ArrayList<Block>();
		
		blockElectroFurnace    = registerBlock(new BlockElectroFurnace());
		blockGeneratorStirling = registerBlock(new BlockGeneratorStirling());
		blockCapacitor         = registerBlock(new BlockCapacitor());
		
		//GameRegistry.register(blockMultiCable = new BlockMultiCable());
		//GameRegistry.register(new ItemBlock(blockMultiCable).setRegistryName(blockMultiCable.getRegistryName().toString()));
	}
	
	private static Block registerBlock(Block block)
	{
		GameRegistry.register(block);
		GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName().toString()));
		
		registeredBlocks.add(block);
		
		return block;
	}
}
