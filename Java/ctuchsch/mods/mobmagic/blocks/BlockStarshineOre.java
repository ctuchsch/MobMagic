package ctuchsch.mods.mobmagic.blocks;

import java.util.Random;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.items.ItemStarshineCrystal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockStarshineOre extends Block {

	public BlockStarshineOre() {
		super(Material.rock);
		this.setHardness(4);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHarvestLevel("pickaxe", 2);

	}

	// makes the ore drop the crystal instead of the ore itself
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune) {

		return MobMagic.itemStarshineCrystal;
	}

	
	
	/*
	 * deals with how many crystals drops
	 * 40% chance for 1 drop
	 * 40% chance for 2 drops
	 * 20% chance for 3 drops
	 * 
	 */
	public int quantityDropped(Random random) {
		
		int flag = random.nextInt(10) + 1;
		if (flag >= 1 && flag <= 4) {
			return 1;
		} else if (flag >= 5 && flag <= 8) {
			return 2;
		} else {
			return 3;
		}
	}
}
