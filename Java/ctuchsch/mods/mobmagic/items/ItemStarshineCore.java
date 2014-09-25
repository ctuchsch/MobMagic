package ctuchsch.mods.mobmagic.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ctuchsch.mods.mobmagic.MobMagic;

public class ItemStarshineCore extends Item {
	
	public ItemStarshineCore(int maximumPower){
		super();
		this.setMaxDamage(maximumPower);
		this.setCreativeTab(MobMagic.tabCustom);
		this.maxStackSize = 1;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player) {
		item.setItemDamage(10000);
		return item;
	}
}
	
	
	
	
	