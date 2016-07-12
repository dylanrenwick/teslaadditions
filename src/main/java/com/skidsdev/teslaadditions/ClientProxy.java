package com.skidsdev.teslaadditions;

import com.skidsdev.teslaadditions.client.render.blocks.BlockRenderRegister;
import com.skidsdev.teslaadditions.client.render.items.ItemRenderRegister;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		
		BlockRenderRegister.registerBlockRenderer();
		ItemRenderRegister.registerItemRenderer();
	}
	
	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
		
		//ItemRenderRegister.registerItemRenderer();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e)
	{
		super.postInit(e);
	}
}
