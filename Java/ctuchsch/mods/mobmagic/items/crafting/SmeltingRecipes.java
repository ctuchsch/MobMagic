package ctuchsch.mods.mobmagic.items.crafting;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.items.ItemStarshineCore;

public class SmeltingRecipes {

	public static void addRecipes() {
		GameRegistry.addSmelting(MobMagic.itemStarshineCrystal, new ItemStack(MobMagic.itemStarshineCore), 1.0F);
		GameRegistry.addSmelting(MobMagic.blockEssenciteOre, new ItemStack(MobMagic.itemEssenciteIngot), 0.7F);
		GameRegistry.addSmelting(MobMagic.itemEssenciteDust,new ItemStack(MobMagic.itemEssenciteIngot), 0.35F);
	}

}
