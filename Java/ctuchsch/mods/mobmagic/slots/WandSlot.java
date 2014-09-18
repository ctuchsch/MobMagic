package ctuchsch.mods.mobmagic.slots;

import ctuchsch.mods.mobmagic.items.ItemEssenceCreeperBucket;
import ctuchsch.mods.mobmagic.items.ItemEssenceEndermanBucket;
import ctuchsch.mods.mobmagic.items.ItemInventoryWand;
import ctuchsch.mods.mobmagic.items.ItemMobWand;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class WandSlot extends Slot {

	public WandSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) {
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		// Everything returns true except an instance of our Item
		return (itemstack.getItem() instanceof ItemEssenceCreeperBucket || itemstack.getItem() instanceof ItemEssenceEndermanBucket);
	}

}
