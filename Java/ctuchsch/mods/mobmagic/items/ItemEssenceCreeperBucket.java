package ctuchsch.mods.mobmagic.items;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBucket;

public class ItemEssenceCreeperBucket extends ItemBucket {
	
	public ItemEssenceCreeperBucket(Block full){
		super(full);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

}
