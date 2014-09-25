package ctuchsch.mods.mobmagic.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBucket;
import ctuchsch.mods.mobmagic.MobMagic;

public class ItemEssenceEndermanBucket extends ItemBucket {
	
	public ItemEssenceEndermanBucket(Block full){
		super(full);
		this.setCreativeTab(MobMagic.tabCustom);
		this.setMaxStackSize(1);
	}

}
