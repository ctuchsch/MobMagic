package ctuchsch.mods.mobmagic.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBucket;
import ctuchsch.mods.mobmagic.MobMagic;

public class ItemEssenceAcidBucket extends ItemBucket {
	
	public ItemEssenceAcidBucket(Block full){
		super(full);
		this.setCreativeTab(MobMagic.tabCustom);
		this.setMaxStackSize(1);
	}

}
