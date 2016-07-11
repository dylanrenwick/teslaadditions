package com.skidsdev.teslaadditions.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockRegister
{
	public static Block blockElectroFurnace;
	
	public static void createBlocks()
	{
		GameRegistry.register(blockElectroFurnace = new BlockElectroFurnace());
		GameRegistry.register(new ItemBlock(blockElectroFurnace).setRegistryName(blockElectroFurnace.getRegistryName().toString()));
	}
}
