package ctuchsch.mods.mobmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ctuchsch.mods.mobmagic.MobMagic;

public class BlockEssenciteOre extends Block {

	public BlockEssenciteOre() {
		super(Material.rock);
		this.setHardness(2);
		this.setCreativeTab(MobMagic.tabCustom);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	
}
