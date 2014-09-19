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
		this.blockIcon = iconRegister.registerIcon(MobMagic.MODID + ":essenceTank_side");
		this.iconFront = iconRegister.registerIcon(MobMagic.MODID + ":essenceTank_front");
		this.iconTop = iconRegister.registerIcon(MobMagic.MODID + ":essenceTank_top");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata) {
		if (metadata == 0 && side == 3)
			return this.iconFront;
		if (side == 1 || side == 0)
			return this.iconTop;
		if (side != metadata)
			return this.blockIcon;
		else
			return this.iconFront;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}

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
				System.out.println(((TileEssenceTank) entity).tank.getFluidAmount() + " " + world.isRemote);
				ItemStack usedItem = player.inventory.getCurrentItem();
				if (usedItem != null)
					return tryUseFluidContainer(world, player, usedItem, (TileEssenceTank) entity);
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
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
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
						if (!item.hasTagCompound()) {
							item.setTagCompound(new NBTTagCompound());
						}
						myTank.tank.writeToNBT(item.getTagCompound());
						dropBlockAsItem(world, x, y, z, item);
					}
				}
			}
		}
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	private void setDefaultDirection(World world, int x, int y, int z) {
		if (!world.isRemote) {
			Block l = world.getBlock(x, y, z - 1);
			Block il = world.getBlock(x, y, z + 1);
			;
			Block jl = world.getBlock(x - 1, y, z);
			;
			Block kl = world.getBlock(x + 1, y, z);
			;

			byte b0 = 3;

			if (l.func_149730_j() && !il.func_149730_j()) {
				b0 = 3;
			}

			if (il.func_149730_j() && !l.func_149730_j()) {
				b0 = 2;
			}

			if (jl.func_149730_j() && !kl.func_149730_j()) {
				b0 = 5;
			}

			if (kl.func_149730_j() && !jl.func_149730_j()) {
				b0 = 4;
			}

			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}

}
