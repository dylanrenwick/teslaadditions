package com.skidsdev.teslaadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegister
{
	public static Block blockElectroFurnace;
	public static Block blockGeneratorStirling;
	public static Block blockPowerCable;
	
	public static void createBlocks()
	{
		GameRegistry.register(blockElectroFurnace = new BlockElectroFurnace());
		GameRegistry.register(new ItemBlock(blockElectroFurnace).setRegistryName(blockElectroFurnace.getRegistryName().toString()));
		
		GameRegistry.register(blockGeneratorStirling = new BlockGeneratorStirling());
		GameRegistry.register(new ItemBlock(blockGeneratorStirling).setRegistryName(blockGeneratorStirling.getRegistryName().toString()));
		
		GameRegistry.register(blockPowerCable = new BlockMultiCable());
		GameRegistry.register(new ItemBlock(blockPowerCable).setRegistryName(blockPowerCable.getRegistryName().toString()));
	}
}
