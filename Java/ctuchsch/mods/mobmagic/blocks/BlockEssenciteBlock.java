package ctuchsch.mods.mobmagic.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ctuchsch.mods.mobmagic.MobMagic;

public class BlockEssenciteBlock extends Block {
	
	
	public BlockEssenciteBlock() {
		super(Material.iron);
		this.setHardness(2);
		this.setCreativeTab(MobMagic.tabCustom);
		this.setHarvestLevel("pickaxe", 1);
		
	}
}
