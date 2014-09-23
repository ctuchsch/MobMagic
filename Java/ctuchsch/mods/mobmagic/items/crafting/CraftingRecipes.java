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
		
		//Essence Tank
		GameRegistry.addRecipe(new ItemStack(MobMagic.blockEssenceTank),
				"xxx",
				" o ",
				"xxx",
				'x', Blocks.wooden_slab, 'o', Blocks.glass);
		
		//Mob Wand
		GameRegistry.addRecipe(new ItemStack(MobMagic.mobWand),
				" xx",
				" ox",
				"o  ",
				'x', MobMagic.itemEssenciteIngot, 'o', Items.stick);
		
		//Essencite Panel
		GameRegistry.addRecipe(new ItemStack(MobMagic.itemEssencitePanel),
				"xx",
				"xx",
				'x', MobMagic.itemEssenciteIngot);
		
		//Essencite Strut
		GameRegistry.addRecipe(new ItemStack(MobMagic.itemEssenciteStrut, 3),
				"x x",
				"x x",
				"x x",
				'x', MobMagic.itemEssenciteIngot);
		
		//Essence Infuser Frame
		GameRegistry.addRecipe(new ItemStack(MobMagic.itemInfusionFrame),
				"xox",
				"ooo",
				"xyx",
				'x', MobMagic.itemEssenciteStrut,
				'o', Blocks.glass_pane,
				'y', MobMagic.itemEssenciteIngot);
		
		//Infusion Pedestal
		GameRegistry.addRecipe(new ItemStack(MobMagic.itemInfusionPedestal),
				"   ",
				" x ",
				"xxx",
				'x', new ItemStack(Blocks.stone_slab, 1, 7));
		
		//Magic Linker
		GameRegistry.addRecipe(new ItemStack(MobMagic.itemMagicLinker),
				" xx",
				" xx",
				"x  ",
				'x', MobMagic.itemEssenciteIngot);
		
		//Magic Delinker
		GameRegistry.addRecipe(new ItemStack(MobMagic.itemMagicDelinker), 
				" x ",
				"x x",
				" x ",
				'x', MobMagic.itemEssenciteIngot);
		
		//Linking Core
		GameRegistry.addRecipe(new ItemStack(MobMagic.itemLinkingCore),
				"x y",
				" o ",
				"y x",
				'x', MobMagic.itemMagicLinker,
				'y', MobMagic.itemMagicDelinker,
				'o', MobMagic.itemFossil);
		
		//SHAPELESS RECIPES----
		
		//Essencite Ingots (from block)
		GameRegistry.addShapelessRecipe(new ItemStack(MobMagic.itemEssenciteIngot, 9), MobMagic.blockEssenciteBlock);
		
		//Tool Charger
		GameRegistry.addShapelessRecipe(new ItemStack(MobMagic.blockToolCharger), MobMagic.itemInfusionFrame, MobMagic.itemInfusionPedestal, MobMagic.itemLinkingCore);
	}
}
