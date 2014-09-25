package ctuchsch.mods.mobmagic.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.blocks.BlockEssenceTank;
import ctuchsch.mods.mobmagic.entity.EntityProjectileCreeper;
import ctuchsch.mods.mobmagic.entity.EntityProjectileEnderman;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;

public class ItemMobWand extends Item {
	private final int CreeperCost = 700;
	private final int EndermanCost = 500;

	public ItemMobWand() {
		super();
		this.setCreativeTab(MobMagic.tabCustom);
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
		if (invItem != null && invItem.hasTagCompound()) {
			invSlot = invItem.getItem();
			Block itemAsBlock = Block.getBlockFromItem(invSlot);
			if (itemAsBlock instanceof BlockEssenceTank) {
				// get the tile entity from item nbt
				TileEssenceTank tank = new TileEssenceTank();
				tank.readFromNBT(invItem.getTagCompound());
				FluidStack avalibleFluid = tank.tank.getFluid();
				if (avalibleFluid != null) {
					if (avalibleFluid.isFluidEqual(new FluidStack(MobMagic.essenceCreeper, 1)))
						FireCreeper(world, player, avalibleFluid, tank);
					if (avalibleFluid.isFluidEqual(new FluidStack(MobMagic.essenceEnderman, 1)))
						FireEnderman(world, player, avalibleFluid, tank);
					tank.writeToNBT(invItem.getTagCompound());
					tank.markDirty();
				}
			}
		}
	}

	private void FireCreeper(World world, EntityPlayer player, FluidStack avalibleFluid, TileEssenceTank tank) {
		if (avalibleFluid.amount >= CreeperCost) {
			if (!player.capabilities.isCreativeMode)
				tank.tank.drain(CreeperCost, true);
			world.spawnEntityInWorld(new EntityProjectileCreeper(world, player));
		}
	}

	private void FireEnderman(World world, EntityPlayer player, FluidStack avalibleFluid, TileEssenceTank tank) {
		if (avalibleFluid.amount >= EndermanCost) {
			if (!player.capabilities.isCreativeMode)
				tank.tank.drain(EndermanCost, true);
			world.spawnEntityInWorld(new EntityProjectileEnderman(world, player));
		}
	}

	private ItemStack processShiftClick(World world, EntityPlayer player, ItemStack stack) {
		// open the inventory:
		player.openGui(MobMagic.instance, MobMagic.GUI_MOB_WAND, player.worldObj, (int) player.posX, (int) player.posY,
				(int) player.posZ);
		return stack;
	}

}
