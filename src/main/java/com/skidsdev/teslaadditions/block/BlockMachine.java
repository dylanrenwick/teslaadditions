package com.skidsdev.teslaadditions.block;

import java.util.List;

import com.skidsdev.teslaadditions.tile.TileEntityElectroFurnace;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockMachine extends BlockDirectional
{	
	public BlockMachine(String regName)
	{
		super(regName, Material.IRON, 5.0f, 10.0f);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }
}