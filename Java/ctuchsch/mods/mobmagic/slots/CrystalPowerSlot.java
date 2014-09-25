package ctuchsch.mods.mobmagic.slots;

import ctuchsch.mods.mobmagic.items.CrystalBatteryBase;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class CrystalPowerSlot extends Slot {

	public CrystalPowerSlot(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack item) {
		return CrystalBatteryBase.canBeCharged(item);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

}
