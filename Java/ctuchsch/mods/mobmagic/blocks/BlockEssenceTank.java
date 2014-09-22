package ctuchsch.mods.mobmagic.blocks;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.fluids.TankEssence;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import ctuchsch.mods.mobmagic.utils.ItemUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockEssenceTank extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockEssenceTank() {
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHardness(4.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEssenceTank();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(MobMagic.MODID + ":essenceTankIcon");
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile != null && itemStack.hasTagCompound()) {
			if (tile instanceof TileEssenceTank) {
				TileEssenceTank myEntity = (TileEssenceTank) tile;
				myEntity.tank = (TankEssence) myEntity.tank.readFromNBT(itemStack.getTagCompound());
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY,
			float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null) {
			if (entity instanceof TileEssenceTank) {
				TileEssenceTank te = (TileEssenceTank) entity;
				System.out.println(te.tank.getFluidAmount() + " " + world.isRemote);
				ItemStack usedItem = player.inventory.getCurrentItem();
				if (usedItem != null)
					return tryUseFluidContainer(world, player, usedItem, (TileEssenceTank) entity);
				else {
					if (player.isSneaking()) {
						te.tank.drain(te.tank.getCapacity(), true);
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean tryFillItem(World world, EntityPlayer player, ItemStack current, TileEssenceTank tank) {
		FluidStack available = tank.tank.getFluid();
		if (available == null || available.amount <= 0)
			return false;

		ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, current);
		FluidStack containedFluid = FluidContainerRegistry.getFluidForFilledItem(filled);
		if (containedFluid != null) {
			if (!player.capabilities.isCreativeMode) {
				if (current.stackSize > 1) {
					if (!player.inventory.addItemStackToInventory(filled))
						return false;
					player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtils.consumeItem(current));
				} else {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, filled);
				}
			}
			tank.tank.drain(containedFluid.amount, true);
			return true;
		}

		return false;
	}

	private boolean tryEmptyItem(World world, EntityPlayer player, ItemStack usedItem, TileEssenceTank tank) {
		FluidStack containedFluid = FluidContainerRegistry.getFluidForFilledItem(usedItem);
		if (containedFluid != null) {
			int qty = tank.tank.fill(containedFluid, true);
			if (qty != 0 && !player.capabilities.isCreativeMode) {
				player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemUtils.consumeItem(usedItem));
			}
			return true;
		}
		return false;
	}

	private boolean tryUseFluidContainer(World world, EntityPlayer player, ItemStack usedItem, TileEssenceTank tank) {
		boolean retval = tryEmptyItem(world, player, usedItem, tank) || tryFillItem(world, player, usedItem, tank);
		tank.markDirty();
		return retval;
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		if (willHarvest && !player.capabilities.isCreativeMode) {
			final TileEntity te = world.getTileEntity(x, y, z);

			boolean result = super.removedByPlayer(world, player, x, y, z, willHarvest);

			if (result) {
				if (te != null) {
					if (te instanceof TileEssenceTank) {
						TileEssenceTank myTank = (TileEssenceTank) te;
						ItemStack item = new ItemStack(MobMagic.blockEssenceTank);
						if (myTank.tank.getCapacity() > 0) {
							if (!item.hasTagCompound()) {
								item.setTagCompound(new NBTTagCompound());
							}
							myTank.tank.writeToNBT(item.getTagCompound());							
						}
						dropBlockAsItem(world, x, y, z, item);
					}
				}
			}
		}
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_,
			int p_149646_5_) {
		return true;
	}

}
