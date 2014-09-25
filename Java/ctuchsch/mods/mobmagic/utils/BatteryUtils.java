package ctuchsch.mods.mobmagic.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BatteryUtils extends Item {

	
	public int getCapacity(ItemStack item){
		return getMaxDamage();
	}
	
	public int getCurrentPower(ItemStack item){
		return item.getMaxDamage() - item.getItemDamage();
	}
	
	public ItemStack drainPower(ItemStack item, int amountDrained, EntityPlayer player) {
		
		if (this.getCurrentPower(item) >= amountDrained){
			item.damageItem(amountDrained, player);
		}
		return item;
	}
	
	public ItemStack addPower(ItemStack item, int amountCharged, EntityPlayer player) {
		
		amountCharged = -amountCharged;
		item.damageItem(amountCharged, player);
		return item;
			
	}
}
