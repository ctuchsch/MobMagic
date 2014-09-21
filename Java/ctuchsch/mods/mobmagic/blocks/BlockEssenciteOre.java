package ctuchsch.mods.mobmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockEssenciteOre extends Block {

	public BlockEssenciteOre() {
		super(Material.rock);
		this.setHardness(2);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	
}
