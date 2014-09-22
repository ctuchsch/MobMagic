package ctuchsch.mods.mobmagic.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.items.ItemMagicDelinker;
import ctuchsch.mods.mobmagic.items.ItemMagicLinker;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockToolCharger extends BlockContainer {

	public BlockToolCharger() {
		super(Material.rock);
		setCreativeTab(CreativeTabs.tabMisc);
		this.setHardness(4.0F);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.blockIcon = iconRegister.registerIcon(MobMagic.MODID + ":blockToolCharger");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_) {
		return new TileToolCharger();
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
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_,
			int p_149646_5_) {
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY,
			float hitZ) {
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null && entity instanceof TileToolCharger) {
			TileToolCharger te = (TileToolCharger) entity;
			ItemStack stack = player.inventory.getCurrentItem();
			if (stack != null) {
				Item checkItem = stack.getItem();
				if (checkItem instanceof ItemMagicLinker)
					return tryLink(world, checkItem, player, te);
				if (checkItem instanceof ItemMagicDelinker)
					return tryDelink(world, player, te);
			}
		}

		player.openGui(MobMagic.instance, MobMagic.GUI_MOB_TOOLCHARGER, world, x, y, z);
		return true;
	}

	private boolean tryDelink(World world, EntityPlayer player, TileToolCharger te) {
		if (player.isSneaking()) {
			te.clearTanks(world, player);
			return true;
		}
		return false;
	}

	private boolean tryLink(World world, Item clickedItem, EntityPlayer player, TileToolCharger te) {
		ItemMagicLinker linker = (ItemMagicLinker) clickedItem;
		if (!player.isSneaking() && linker.linkedLocation != null) {
			boolean retval = te.linkTank(world, linker.linkedLocation.x, linker.linkedLocation.y, linker.linkedLocation.z,
					player);
			return retval;
		}
		return false;
	}

}
