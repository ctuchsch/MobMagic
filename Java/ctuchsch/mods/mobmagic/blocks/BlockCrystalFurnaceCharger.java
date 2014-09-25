package ctuchsch.mods.mobmagic.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ctuchsch.mods.mobmagic.MobMagic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrystalFurnaceCharger extends BlockContainer {
	private static boolean keepInventory;
	// private final boolean active;

	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	public BlockCrystalFurnaceCharger() {
		super(Material.rock);
		setCreativeTab(MobMagic.tabCustom);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metaData) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(MobMagic.MODID + ":CrystalFurnaceCharger_Side");
		this.iconFront = iconRegister.registerIcon(MobMagic.MODID + ":CrystalFurnaceCharger_FrontActive");
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

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		this.setDefaultDirection(world, x, y, z);
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
}
