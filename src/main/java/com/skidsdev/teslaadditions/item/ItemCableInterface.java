package com.skidsdev.teslaadditions.item;

import com.skidsdev.teslaadditions.capability.IMultiCable;
import com.skidsdev.teslaadditions.capability.TeslaAdditionsCapabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCableInterface extends ItemBase
{
	public ItemCableInterface()
	{
		super("itemCableInterface");
	}
	
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		BlockPos cablePos = pos.offset(facing);
		
		TileEntity cableTileEntity = worldIn.getTileEntity(cablePos);
		
		EnumFacing interfaceFacing = facing.getOpposite();
		
		if (cableTileEntity != null && cableTileEntity.hasCapability(TeslaAdditionsCapabilities.MULTI_CABLE, interfaceFacing))
		{
			IMultiCable cable = cableTileEntity.getCapability(TeslaAdditionsCapabilities.MULTI_CABLE, interfaceFacing);
			
			if (!cable.hasCableInterface(interfaceFacing))
			{
				cable.addCableInterface(interfaceFacing);
				stack.stackSize--;
				
				if (stack.stackSize <= 0) { player.inventory.setInventorySlotContents(player.inventory.currentItem, null); }
			}
		}
		
		return EnumActionResult.SUCCESS;
	}
}
