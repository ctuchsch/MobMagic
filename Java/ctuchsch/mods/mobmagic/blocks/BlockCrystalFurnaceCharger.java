package ctuchsch.mods.mobmagic.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.items.ItemMagicDelinker;
import ctuchsch.mods.mobmagic.items.ItemMagicLinker;
import ctuchsch.mods.mobmagic.tileentities.TileCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrystalFurnaceCharger extends BlockContainer {
	private static boolean keepInventory;
	private final boolean active;
	private Random rand = new Random();

	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockCrystalFurnaceCharger(boolean active) {
		super(Material.rock);
		if(!active)
			setCreativeTab(MobMagic.tabCustom);
		this.active = active;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metaData) {
		return new TileCrystalFurnaceCharger();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(MobMagic.MODID + ":CrystalFurnaceCharger_Side");
		this.iconFront = (active) ? iconRegister.registerIcon(MobMagic.MODID + ":CrystalFurnaceCharger_FrontActive")
				: iconRegister.registerIcon(MobMagic.MODID + ":CrystalFurnaceCharger_Front");
		this.iconTop = iconRegister.registerIcon(MobMagic.MODID + ":CrystalFurnaceCharger_Top");
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
	
	public Item getItemDropped(int par1, Random random, int par3) {
		return Item.getItemFromBlock(MobMagic.blockCrystalFurnaceCharger);
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int num) {
		if (!keepInventory) {
			TileCrystalFurnaceCharger entity = (TileCrystalFurnaceCharger) world.getTileEntity(x, y, z);
			if (entity != null) {
				for (int i = 0; i < entity.getSizeInventory(); i++) {
					ItemStack itemstack = entity.getStackInSlot(i);
					if (itemstack != null) {
						float f = this.rand.nextFloat() * 0.8F + 0.1F;
						float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
						float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0) {
							int itemsToThrow = this.rand.nextInt(21) + 10;
							if (itemsToThrow > itemstack.stackSize)
								itemsToThrow = itemstack.stackSize;

							itemstack.stackSize -= itemsToThrow;
							EntityItem item = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1),
									(double) ((float) z + f2), new ItemStack(itemstack.getItem(), itemsToThrow,
											itemstack.getItemDamage()));

							if (itemstack.hasTagCompound())
								item.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());

							float f3 = 0.05F;
							item.motionX = (double) ((float) this.rand.nextGaussian() * f3);
							item.motionY = (double) ((float) this.rand.nextGaussian() * f3 + 0.2F);
							item.motionZ = (double) ((float) this.rand.nextGaussian() * f3);
							world.spawnEntityInWorld(item);
						}
					}
				}
				world.func_147453_f(x, y, z, block);
			}
		}
		super.breakBlock(world, x, y, z, block, num);
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
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY,
			float hitZ) {	
		player.openGui(MobMagic.instance, MobMagic.GUI_MOB_CrystalFurnaceCharger, world, x, y, z);
		return true;
	}
	
	public static void updateBlockState(boolean burning, World world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		keepInventory = true;
		if (burning)
			world.setBlock(x, y, z, MobMagic.blockCrystalFurnaceChargerActive);
		else
			world.setBlock(x, y, z, MobMagic.blockCrystalFurnaceCharger);
		keepInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, l, 2);

		if (tileentity != null) {
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int i) {
		return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(x, y, z));
	}

	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(MobMagic.blockCrystalFurnaceCharger);
	}
}
