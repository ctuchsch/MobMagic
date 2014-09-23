package ctuchsch.mods.mobmagic.items;

import ctuchsch.mods.mobmagic.MobMagic;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBucket;

public class ItemEssenceAcidBucket extends ItemBucket {
	
	public ItemEssenceAcidBucket(Block full){
		super(full);
		this.setCreativeTab(MobMagic.tabCustom);
	}

}
