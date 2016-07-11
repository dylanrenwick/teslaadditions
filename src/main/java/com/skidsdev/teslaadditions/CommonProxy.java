package com.skidsdev.teslaadditions;

import com.skidsdev.teslaadditions.client.gui.GuiHandler;
import com.skidsdev.teslaadditions.tile.TileEntityElectroFurnace;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy
{
	private Config modConfig;
	private GuiHandler guiHandler;
	
	public void preInit(FMLPreInitializationEvent e)
	{
		this.modConfig = new Config(e.getSuggestedConfigurationFile());
		
		this.modConfig.setupItems();
		this.modConfig.setupBlocks();
		
		GameRegistry.registerTileEntity(TileEntityElectroFurnace.class, "electrofurnace");
		
		guiHandler = GuiHandler.getInstance();
	}
	
	public void init(FMLInitializationEvent e)
	{
		
	}
	
	public void postInit(FMLPostInitializationEvent e)
	{
		
	}
}
