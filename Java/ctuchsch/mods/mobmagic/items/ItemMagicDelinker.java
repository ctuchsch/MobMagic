package ctuchsch.mods.mobmagic.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ItemMagicDelinker extends Item {
	public ItemMagicDelinker() {
		super();
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return true;
	}
	
}
