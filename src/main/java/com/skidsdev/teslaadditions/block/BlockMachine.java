package com.skidsdev.teslaadditions.block;

import com.skidsdev.teslaadditions.tile.TileEntityMachine;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockMachine extends BlockDirectional
{
	private static final PropertyBool RUNNING = PropertyBool.create("running");
	
	protected boolean isRunning = false;
	
	public BlockMachine(String regName)
	{
		super(regName, Material.IRON, 5.0f, 10.0f);
		this.setDefaultState(blockState.getBaseState().withProperty(RUNNING, false).withProperty(BlockHorizontal.FACING, EnumFacing.NORTH));
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, BlockHorizontal.FACING, RUNNING);
	}
	
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		boolean running = false;
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if (tileEntity != null && tileEntity instanceof TileEntityMachine)
		{
			TileEntityMachine machine = (TileEntityMachine)tileEntity;
			
			running = machine.getIsRunning();
		}
		
		return state.withProperty(RUNNING, running);
	}
}