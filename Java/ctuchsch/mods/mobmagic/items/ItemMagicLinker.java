package ctuchsch.mods.mobmagic.items;

import ctuchsch.mods.mobmagic.CommonProxy;
import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import ctuchsch.mods.mobmagic.utils.ChatUtils;
import ctuchsch.mods.mobmagic.utils.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemMagicLinker extends Item {
	public Location linkedLocation;
	private ItemStack saveStack;

	public ItemMagicLinker() {
		super();
		this.setCreativeTab(MobMagic.tabCustom);
		this.setMaxStackSize(1);
	}

	public void setLinked(ItemStack item, int x, int y, int z) {
		saveStack = item;
		linkedLocation = new Location(x, y, z);
		writeToNBT(saveStack.getTagCompound());
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		boolean setLoc = false;
		MovingObjectPosition pos = this.getMovingObjectPositionFromPlayer(world, player, true);
		if (pos != null) {
			TileEntity entity = world.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
			if (entity != null && entity instanceof TileEssenceTank) {
				setLinked(item, pos.blockX, pos.blockY, pos.blockZ);
				if (world.isRemote)
					ChatUtils.printMessageToPlayer("Linked to tank.", world, player);
				setLoc = true;
			}

		}
		if (!setLoc && linkedLocation != null && player.isSneaking()) {
			linkedLocation = null;
			if (world.isRemote)
				ChatUtils.printMessageToPlayer("Link location cleared.", world, player);
		}
		return item;
	}

	private void writeToNBT(NBTTagCompound nbt) {
		if (nbt == null)
			saveStack.setTagCompound(new NBTTagCompound());
		linkedLocation.writeToNBT(saveStack.getTagCompound());
	}

	private void readFromNBT(NBTTagCompound nbt) {
		this.linkedLocation = Location.readFromNBT(nbt);
	}

	public void clearLinked() {
		this.linkedLocation = null;
	}

	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return true;
	}

}
