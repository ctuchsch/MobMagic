package ctuchsch.mods.mobmagic.blocks;

import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockEssenceTank extends BlockContainer {

	protected BlockEssenceTank() {		
		super(Material.rock);
		this.setCreativeTab(CreativeTabs.tabMisc);
		this.setHardness(4.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		// TODO Auto-generated method stub
		return new TileEssenceTank();
	}	

}
