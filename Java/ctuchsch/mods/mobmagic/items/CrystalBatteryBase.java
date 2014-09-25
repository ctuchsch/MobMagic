package ctuchsch.mods.mobmagic.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrystalBatteryBase extends Item {

	public static boolean isBattery(ItemStack item) {
		return item.getItem() instanceof CrystalBatteryBase;
	}
	
	public static boolean canBeCharged(ItemStack item) {
		return true;
	}
	
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
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List tooltips, boolean someBool) {
		tooltips.add("Energy: "+getCurrentPower(item)+"/"+getCapacity(item));
	}
}
