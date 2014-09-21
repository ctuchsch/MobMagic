package ctuchsch.mods.mobmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockEssenciteBlock extends Block {
	
	
	public BlockEssenciteBlock() {
		super(Material.iron);
		this.setHardness(2);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHarvestLevel("pickaxe", 1);
		
	}
}
