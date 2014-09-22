package ctuchsch.mods.mobmagic.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ctuchsch.mods.mobmagic.slots.WandSlot;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;

public class ContainerToolCharger extends Container {
	private TileToolCharger charger;
	
	public ContainerToolCharger(InventoryPlayer playerInventory, TileToolCharger charger) {
		this.charger = charger;
		
		this.addSlotToContainer(new Slot(charger, 0, 80, 28));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 84));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
		
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return charger.isUseableByPlayer(player);
	}
}
