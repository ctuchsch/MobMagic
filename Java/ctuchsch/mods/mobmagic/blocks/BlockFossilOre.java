package ctuchsch.mods.mobmagic.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import ctuchsch.mods.mobmagic.MobMagic;

public class BlockFossilOre extends Block {

	public BlockFossilOre() {
		super(Material.rock);
		this.setHardness(1);
		this.setCreativeTab(MobMagic.tabCustom);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune) {
		return MobMagic.itemFossil;
	}
}
