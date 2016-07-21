package com.skidsdev.teslaadditions.block;

import javax.annotation.Nullable;

import com.skidsdev.teslaadditions.TeslaAdditions;
import com.skidsdev.teslaadditions.capability.TeslaAdditionsCapabilities;
import com.skidsdev.teslaadditions.tile.TileEntityMultiCable;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMultiCable extends BlockBase
{
	private static final PropertyBool CENTER = PropertyBool.create("center"); 
	
	private static final PropertyBool NORTH  = PropertyBool.create("north");
	private static final PropertyBool SOUTH  = PropertyBool.create("south");
	private static final PropertyBool EAST   = PropertyBool.create("east");
	private static final PropertyBool WEST   = PropertyBool.create("west");
	private static final PropertyBool UP     = PropertyBool.create("up");
	private static final PropertyBool DOWN   = PropertyBool.create("down");

	private static final PropertyBool INTERFACE_NORTH  = PropertyBool.create("interface_north");
	private static final PropertyBool INTERFACE_SOUTH  = PropertyBool.create("interface_south");
	private static final PropertyBool INTERFACE_EAST   = PropertyBool.create("interface_east");
	private static final PropertyBool INTERFACE_WEST   = PropertyBool.create("interface_west");
	private static final PropertyBool INTERFACE_UP     = PropertyBool.create("interface_up");
	private static final PropertyBool INTERFACE_DOWN   = PropertyBool.create("interface_down");
	
	public BlockMultiCable()
	{
		super("blockPowerCable", Material.IRON, 5.0f, 10.0f);
		this.setDefaultState(blockState.getBaseState().withProperty(CENTER, true)
				.withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(WEST, false).withProperty(EAST, false).withProperty(UP, false).withProperty(DOWN, false)
				.withProperty(INTERFACE_NORTH, false).withProperty(INTERFACE_SOUTH, false).withProperty(INTERFACE_EAST, false).withProperty(INTERFACE_WEST, false).withProperty(INTERFACE_UP, false).withProperty(INTERFACE_DOWN, false));
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		state = getActualState(state, worldIn, pos);
		
		boolean north = state.getValue(NORTH);
		boolean south = state.getValue(SOUTH);
		boolean east  = state.getValue(EAST);
		boolean west  = state.getValue(WEST);
		boolean up    = state.getValue(UP);
		boolean down  = state.getValue(DOWN);
		
		boolean interface_north = state.getValue(INTERFACE_NORTH);
		boolean interface_south = state.getValue(INTERFACE_SOUTH);
		boolean interface_east  = state.getValue(INTERFACE_EAST);
		boolean interface_west  = state.getValue(INTERFACE_WEST);
		boolean interface_up    = state.getValue(INTERFACE_UP);
		boolean interface_down  = state.getValue(INTERFACE_DOWN);
		
		boolean center = state.getValue(CENTER);
		
		double pixelInc = 1.0 / 16.0;
		
		double x1 = getBBCoord(west, (interface_north || interface_south || interface_up || interface_down), center) * pixelInc;
		double y1 = getBBCoord(down, (interface_east || interface_west || interface_north || interface_south), center) * pixelInc;
		double z1 = getBBCoord(north, (interface_east || interface_west || interface_up || interface_down), center) * pixelInc;
		
		double x2 = (16 - getBBCoord(east, (interface_north || interface_south || interface_up || interface_down), center)) * pixelInc;
		double y2 = (16 - getBBCoord(up, (interface_east || interface_west || interface_north || interface_south), center)) * pixelInc;
		double z2 = (16 - getBBCoord(south, (interface_east || interface_west || interface_up || interface_down), center)) * pixelInc;
		
		return new AxisAlignedBB(x1, y1, z1, x2, y2, z2);
	}
	
	private double getBBCoord(boolean arm, boolean interfaze, boolean center)
	{
		if (arm) return 0;
		else
		{
			if (interfaze) return 2;
			else
			{
				if (center) return 5;
				else return 6;
			}
		}		
	}
	
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote)
		{
			player.openGui(TeslaAdditions.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }
	
	@Override
	public boolean isFullCube(IBlockState state) { return false; }
	
	@Override
	public boolean hasTileEntity(IBlockState state) { return true; }
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState state)
	{
		return new TileEntityMultiCable();
	}
	
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, CENTER, NORTH, SOUTH, EAST, WEST, UP, DOWN, INTERFACE_NORTH, INTERFACE_SOUTH, INTERFACE_EAST, INTERFACE_WEST, INTERFACE_UP, INTERFACE_DOWN);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		boolean interface_north = false;
		boolean interface_south = false;
		boolean interface_east = false;
		boolean interface_west = false;
		boolean interface_up = false;
		boolean interface_down = false;
		
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if (tileEntity != null && tileEntity instanceof TileEntityMultiCable)
		{
			TileEntityMultiCable tileEntityMC = (TileEntityMultiCable)tileEntity;
			
			interface_north = tileEntityMC.hasInterface(EnumFacing.NORTH);
			interface_south = tileEntityMC.hasInterface(EnumFacing.SOUTH);
			interface_east  = tileEntityMC.hasInterface(EnumFacing.EAST);
			interface_west  = tileEntityMC.hasInterface(EnumFacing.WEST);
			interface_up    = tileEntityMC.hasInterface(EnumFacing.UP);
			interface_down  = tileEntityMC.hasInterface(EnumFacing.DOWN);
		}
		
		boolean north = interface_north || canConnect(worldIn, pos.north(), EnumFacing.NORTH);
		boolean south = interface_south || canConnect(worldIn, pos.south(), EnumFacing.SOUTH);
		boolean east  = interface_east  || canConnect(worldIn, pos.east(), EnumFacing.EAST);
		boolean west  = interface_west  || canConnect(worldIn, pos.west(), EnumFacing.WEST);
		boolean up    = interface_up    || canConnect(worldIn, pos.up(), EnumFacing.UP);
		boolean down  = interface_down  || canConnect(worldIn, pos.down(), EnumFacing.DOWN);
		
		boolean center = getRenderCenter(north, south, east, west, up, down);
		
		return state.withProperty(CENTER, center)
				.withProperty(NORTH, north).withProperty(SOUTH, south).withProperty(EAST, east).withProperty(WEST, west).withProperty(UP, up).withProperty(DOWN, down)
				.withProperty(INTERFACE_NORTH, interface_north).withProperty(INTERFACE_SOUTH, interface_south).withProperty(INTERFACE_EAST, interface_east)
				.withProperty(INTERFACE_WEST, interface_west).withProperty(INTERFACE_UP, interface_up).withProperty(INTERFACE_DOWN, interface_down);
	}
	
	private boolean getRenderCenter(boolean north, boolean south, boolean east, boolean west, boolean up, boolean down)
	{
		return !((north && south && !east && !west && !up && !down) ||
			(!north && !south && east && west && !up && !down) ||
			(!north && !south && !east && !west && up && down));
	}

	private boolean canConnect(IBlockAccess worldIn, BlockPos pos, EnumFacing facing)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		
		if (tileEntity != null)
		{
			return tileEntity.hasCapability(TeslaAdditionsCapabilities.MULTI_CABLE, facing.getOpposite());
		}
		
		return false;
	}
}
