package ctuchsch.mods.mobmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ctuchsch.mods.mobmagic.MobMagic;

public class ItemStarshineCore extends CrystalBatteryBase {
	
	public ItemStarshineCore(int maximumPower){
		super();
		this.setMaxDamage(maximumPower);
		this.setCreativeTab(MobMagic.tabCustom);
	}
}
	
	
	
	
	