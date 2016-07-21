package com.skidsdev.teslaadditions.block;

import com.skidsdev.teslaadditions.TeslaAdditions;
import com.skidsdev.teslaadditions.tile.TileEntityCapacitor;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCapacitor extends BlockDirectional
{
	public static final PropertyInteger CAP_LEVEL = PropertyInteger.create("caplevel", 0, 6);
	
	public BlockCapacitor()
	{
		super("blockCapacitor", Material.IRON, 5.0f, 10.0f);
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockHorizontal.FACING, CAP_LEVEL);
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		return new TileEntityCapacitor();
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		int capLevel = 0;
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if(tileEntity != null && tileEntity instanceof TileEntityCapacitor)
		{
			TileEntityCapacitor capEntity = (TileEntityCapacitor)tileEntity;
			
			long storedPower = capEntity.getStoredPower();
			long capacity = capEntity.getPowerCap();
			
			if (storedPower > 0)
			{
				if (storedPower == capacity) capLevel = 6;
				else
				{
					capLevel = (int)(storedPower / (capacity / 5)) + 1;
				}
			}
		}
		
		return this.getDefaultState().withProperty(CAP_LEVEL, capLevel);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			player.openGui(TeslaAdditions.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
}
