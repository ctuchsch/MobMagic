package ctuchsch.mods.mobmagic.tileentities;

import net.minecraft.item.ItemStack;
import ctuchsch.mods.mobmagic.items.CrystalBatteryBase;

public class TileCrystalGeneraterBase extends TileMobMagicBase {

	protected ItemStack[] slots;
	protected int chargePacketSize = 8;
	
	public TileCrystalGeneraterBase(int invSize, int energyPacketSize) {
		this.slots = new ItemStack[invSize];
		this.chargePacketSize = energyPacketSize;
	}
	
	protected boolean needsEnergy(int slot) {
		if (slots[slot] != null && CrystalBatteryBase.canBeCharged(slots[0])) {
			return CrystalBatteryBase.needsPower(slots[0]);
		}
		return false;
	}

	protected void addPower(int slot) {
		if (slots[slot] != null && CrystalBatteryBase.canBeCharged(slots[0])) {
			CrystalBatteryBase.addPower(slots[0], this.chargePacketSize);
		}
	}
	
}
