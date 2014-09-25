package ctuchsch.mods.mobmagic.items;

import net.minecraft.item.Item;
import ctuchsch.mods.mobmagic.MobMagic;

public class ItemStarshineCore extends Item {
	
	public ItemStarshineCore(int maximumPower){
		super();
		this.setMaxDamage(maximumPower);
		this.setCreativeTab(MobMagic.tabCustom);
		this.maxStackSize = 1;
	}
}
	
	
	
	
	