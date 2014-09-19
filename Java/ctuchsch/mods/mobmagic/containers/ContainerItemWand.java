package ctuchsch.mods.mobmagic.containers;

import ctuchsch.mods.mobmagic.blocks.BlockEssenceTank;
import ctuchsch.mods.mobmagic.items.ItemInventoryWand;
import ctuchsch.mods.mobmagic.slots.WandSlot;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerItemWand extends Container {

	public final ItemInventoryWand inventory;

	private static final int INV_START = ItemInventoryWand.INV_SIZE, INV_END = INV_START + 21, HOTBAR_START = INV_END + 1,
			HOTBAR_END = HOTBAR_START + 8;

	public ContainerItemWand(EntityPlayer par1Player, InventoryPlayer inventoryPlayer, ItemInventoryWand itemInventoryWand) {
		inventory = itemInventoryWand;
		int i;
		/*
		 * for(i = 0; i < itemInventoryWand.INV_SIZE; i++) { } this will need to
		 * be in a loop if I have more than 1 slot
		 */
		this.addSlotToContainer(new WandSlot(this.inventory, 0, 80, 29));

		// Add player inventory
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		// PLAYER ACTION BAR - uses default locations for standard action bar
		// texture file
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// If item is in our custom Inventory or armor slot
			if (par2 < INV_START) {
				// try to place in player inventory / action bar
				if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true))
					return null;
				slot.onSlotChange(itemstack1, itemstack);
			}
			// Item is in inventory / hotbar, try to place in custom inventory
			else {
				// item is in player's inventory, but not in action bar
				Block block = Block.getBlockFromItem(itemstack1.getItem());
				if (block instanceof BlockEssenceTank) {
					if (!this.mergeItemStack(itemstack1, 0, ItemInventoryWand.INV_SIZE, false))
						return null;
				} else if (par2 >= INV_START && par2 < HOTBAR_START) {
					if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false))
						return null;
				}
				// item in action bar - place in player inventory
				else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1) {
					if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
						return null;
					}
				}
			}
			//System.out.println(itemstack1.stackSize);
			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack) null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
		// this will prevent the player from interacting with the item that
		// opened the inventory:
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
			return null;
		}
		return super.slotClick(slot, button, flag, player);
	}
}
