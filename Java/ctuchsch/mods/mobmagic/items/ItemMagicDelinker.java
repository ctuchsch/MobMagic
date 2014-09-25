package ctuchsch.mods.mobmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import ctuchsch.mods.mobmagic.MobMagic;

public class ItemMagicDelinker extends Item {
	public ItemMagicDelinker() {
		super();
		setCreativeTab(MobMagic.tabCustom);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
		return true;
	}
	
}
