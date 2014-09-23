package ctuchsch.mods.mobmagic.blocks;

import ctuchsch.mods.mobmagic.MobMagic;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockEssenciteBlock extends Block {
	
	
	public BlockEssenciteBlock() {
		super(Material.iron);
		this.setHardness(2);
		this.setCreativeTab(MobMagic.tabCustom);
		this.setHarvestLevel("pickaxe", 1);
		
	}
}
