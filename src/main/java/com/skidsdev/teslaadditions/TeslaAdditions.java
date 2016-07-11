package com.skidsdev.teslaadditions;

import com.skidsdev.teslaadditions.utils.VersionInfo;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = VersionInfo.ModId,
		name = VersionInfo.ModName,
		version = VersionInfo.Version,
		guiFactory = VersionInfo.Factory
)
public class TeslaAdditions
{
	@SidedProxy(clientSide="com.skidsdev.teslaadditions.ClientProxy", serverSide="com.skidsdev.teslaadditions.ServerProxy")
	public static CommonProxy proxy;
	
	@Instance
	public static TeslaAdditions instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e)
    {
    	proxy.init(e);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
    	proxy.postInit(e);
    }	
}
