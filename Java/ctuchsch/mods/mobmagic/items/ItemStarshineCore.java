package ctuchsch.mods.mobmagic.items;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.utils.ChatUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemStarshineCore extends Item {
	
	public ItemStarshineCore(int maximumPower){
		super();
		this.setMaxDamage(maximumPower);
		this.setCreativeTab(MobMagic.tabCustom);
		this.maxStackSize = 1;
	}
	
	//purely testing to see if the durability works
	@Override
	public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player){
		int remainingDurability = this.getRemainingPower(item);
		int damageDone = 1000;
		
		ChatUtils.printMessageToPlayer("starting damage: "+ item.getItemDamage(), world, player);
		ChatUtils.printMessageToPlayer("starting durability: "+(item.getMaxDamage()-item.getItemDamage()), world, player);
		if(remainingDurability >= damageDone){
			item.damageItem(damageDone, player);
		}
		ChatUtils.printMessageToPlayer("ending damage: "+ item.getItemDamage(), world, player);
		ChatUtils.printMessageToPlayer("starting durability: "+(item.getMaxDamage()-item.getItemDamage()), world, player);

		return item;
	}
	
	public int getRemainingPower(ItemStack item){
		return item.getMaxDamage() - item.getItemDamage();
	}
}