package ctuchsch.mods.mobmagic.items.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import ctuchsch.mods.mobmagic.MobMagic;

public class CraftingRecipes {
	public static void addRecipes(){
				
		//SHAPED RECIPES----
	
		//Essencite Blocks
		GameRegistry.addRecipe(new ItemStack(MobMagic.blockEssenciteBlock), 
				"###", 
				"###", 
				"###", 
				'#', MobMagic.itemEssenciteIngot);
		
		GameRegistry.addRecipe(new ItemStack(MobMagic.blockEssenceTank),
				"xxx",
				" o ",
				"xxx",
				'x', Blocks.wooden_slab, 'o', Blocks.glass);
		
		GameRegistry.addRecipe(new ItemStack(MobMagic.mobWand),
				" xx",
				" ox",
				"o  ",
				'x', MobMagic.itemEssenciteIngot, 'o', Items.stick);
		
		
		
		//SHAPELESS RECIPES----
		
		//Essencite Ingots (from block)
		GameRegistry.addShapelessRecipe(new ItemStack(MobMagic.itemEssenciteIngot, 9), MobMagic.blockEssenciteBlock);
	}
}
