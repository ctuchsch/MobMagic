package ctuchsch.mods.mobmagic.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBucket;

public class ItemEssenceEndermanBucket extends ItemBucket {
	
	public ItemEssenceEndermanBucket(Block full){
		super(full);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

}
