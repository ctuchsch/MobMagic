package ctuchsch.mods.mobmagic.items;

import org.lwjgl.input.Keyboard;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.entity.EntityProjectileCreeper;
import ctuchsch.mods.mobmagic.entity.EntityProjectileEnderman;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemMobWand extends Item {
	public ItemMobWand() {
		super();
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setMaxStackSize(1);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 1; // return any value greater than zero
	}

	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {

		ItemInventoryWand inventory = new ItemInventoryWand(item);
		ItemStack invItem = inventory.getStackInSlot(0);

		if (!world.isRemote) {
			if (player.isSneaking()) {
				return processShiftClick(world, player, item);
			} else {
				this.doInventoryAction(world, item, inventory, player, 0);
			}
		}
		return item;
	}

	private void doInventoryAction(World world, ItemStack item, ItemInventoryWand inventory, EntityPlayer player, int slot) {
		ItemStack invItem = inventory.getStackInSlot(slot);
		Item invSlot;
		if (invItem != null) {
			invSlot = invItem.getItem();
			if (invSlot instanceof ItemEssenceCreeperBucket)
				FireCreeper(world, player);
			if(invSlot instanceof ItemEssenceEndermanBucket)
				FireEnderman(world, player);
		}

	}

	private void FireCreeper(World world, EntityPlayer player) {
		world.spawnEntityInWorld(new EntityProjectileCreeper(world, player));
	}
	
	private void FireEnderman(World world, EntityPlayer player) {
		world.spawnEntityInWorld(new EntityProjectileEnderman(world, player));
	}

	private ItemStack processShiftClick(World world, EntityPlayer player, ItemStack stack) {
		// open the inventory:
		player.openGui(MobMagic.instance, MobMagic.GUI_MOB_WAND, player.worldObj, (int) player.posX, (int) player.posY,
				(int) player.posZ);
		return stack;
	}

}
