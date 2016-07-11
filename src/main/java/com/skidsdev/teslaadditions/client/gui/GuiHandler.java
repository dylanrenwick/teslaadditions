package com.skidsdev.teslaadditions.client.gui;

import com.skidsdev.teslaadditions.TeslaAdditions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler
{
	private static GuiHandler instance;
	
	public static GuiHandler getInstance()
	{
		if (instance == null) instance = new GuiHandler();
		return instance;
	}
	
	private GuiHandler()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TeslaAdditions.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World worldIn, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		IOpenableGUI openableGUI = this.getOpenableGUI(id, player, worldIn, pos);
		if (openableGUI != null) return openableGUI.getServerGuiElement(id, player, worldIn, pos);
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World worldIn, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		IOpenableGUI openableGUI = this.getOpenableGUI(id, player, worldIn, pos);
		if (openableGUI != null) return openableGUI.getClientGuiElement(id, player, worldIn, pos);
		
		return null;
	}
	
	private IOpenableGUI getOpenableGUI(int id, EntityPlayer player, World worldIn, BlockPos pos)
	{
		IOpenableGUI openableGUI = null;
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if(tileEntity instanceof IOpenableGUI)
		{
			openableGUI = (IOpenableGUI) tileEntity;
		}
		
		return openableGUI;
	}
}
