package ctuchsch.mods.mobmagic.slots;

import ctuchsch.mods.mobmagic.blocks.BlockEssenceTank;
import ctuchsch.mods.mobmagic.items.ItemEssenceCreeperBucket;
import ctuchsch.mods.mobmagic.items.ItemEssenceEndermanBucket;
import ctuchsch.mods.mobmagic.items.ItemInventoryWand;
import ctuchsch.mods.mobmagic.items.ItemMobWand;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class WandSlot extends Slot {

	public WandSlot(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition) {
		super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
	}

	@Override
	public boolean isItemValid(ItemStack itemstack) {
		Block block = Block.getBlockFromItem(itemstack.getItem());
		return (block instanceof BlockEssenceTank);
	}

}
