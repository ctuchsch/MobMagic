package ctuchsch.mods.mobmagic.blocks;

import ctuchsch.mods.mobmagic.MobMagic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockEssenciteOre extends Block {

	public BlockEssenciteOre() {
		super(Material.rock);
		this.setHardness(2);
		this.setCreativeTab(MobMagic.tabCustom);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	
}
