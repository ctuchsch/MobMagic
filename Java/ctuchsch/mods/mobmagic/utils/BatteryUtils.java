package ctuchsch.mods.mobmagic.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BatteryUtils {

	
	public static int getCapacity(ItemStack item){
		return item.getMaxDamage();
	}
	
	public static int getCurrentPower(ItemStack item){
		return item.getMaxDamage() - item.getItemDamage();
	}
	
	public static ItemStack drainPower(ItemStack item, int amountDrained, EntityPlayer player) {
		
		if (getCurrentPower(item) >= amountDrained){
			item.damageItem(amountDrained, player);
		}
		return item;
	}
	
	public static ItemStack addPower(ItemStack item, int amountCharged) {	
		int power = getCurrentPower(item);
		if(power + amountCharged <= getCapacity(item))
			item.setItemDamage(item.getItemDamage() - amountCharged);
		else
			item.setItemDamage(0);
		return item;
			
	}
	
	public static boolean needsPower(ItemStack item) {
		return getCurrentPower(item) != getCapacity(item);
	}
}
