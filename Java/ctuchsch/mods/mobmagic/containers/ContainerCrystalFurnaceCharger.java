package ctuchsch.mods.mobmagic.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ctuchsch.mods.mobmagic.slots.WandSlot;
import ctuchsch.mods.mobmagic.tileentities.TileCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;

public class ContainerCrystalFurnaceCharger extends Container {
	private TileCrystalFurnaceCharger charger;
	
	public int lastBunTime;
	public int lastCurrentItemBurnTime;
	
	public ContainerCrystalFurnaceCharger(InventoryPlayer playerInventory, TileCrystalFurnaceCharger charger) {
		this.charger = charger;
		
		this.addSlotToContainer(new Slot(charger, 0, 80, 10));
		this.addSlotToContainer(new Slot(charger, 1, 80, 58));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, i * 18 + 84));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}		
	}
	
	@Override
	public void addCraftingToCrafters(ICrafting craft) {
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.charger.burnTime);
		craft.sendProgressBarUpdate(this, 1, this.charger.currentItemBurnTime);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int value){
		switch(slot) {
			case 0: 
				this.charger.burnTime = value;
				break;
			case 1:
				this.charger.currentItemBurnTime = value;
				break;
		}
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return charger.isUseableByPlayer(player);
	}
	
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.crafters.size(); i++){
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			if(this.lastBunTime != this.charger.burnTime)
				craft.sendProgressBarUpdate(this, 0, this.charger.burnTime);
			if(this.lastCurrentItemBurnTime != this.charger.currentItemBurnTime)
				craft.sendProgressBarUpdate(this, 1, this.charger.currentItemBurnTime);					
		}
		
		this.lastBunTime = this.charger.burnTime;
		this.lastCurrentItemBurnTime = this.charger.currentItemBurnTime;
	}
}
