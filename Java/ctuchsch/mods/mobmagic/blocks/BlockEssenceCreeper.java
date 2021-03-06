package ctuchsch.mods.mobmagic.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ctuchsch.mods.mobmagic.MobMagic;

public class BlockEssenceCreeper extends BlockFluidClassic {
	
	  @SideOnly(Side.CLIENT)
      protected IIcon stillIcon;
      @SideOnly(Side.CLIENT)
      protected IIcon flowingIcon;     

	public BlockEssenceCreeper(Fluid fluid, Material material) {
		super(fluid, material);
		setCreativeTab(MobMagic.tabCustom);
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1)? stillIcon : flowingIcon;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
        stillIcon = register.registerIcon(MobMagic.MODID+":essenceCreeperStill");
        flowingIcon = register.registerIcon(MobMagic.MODID+":essenceCreeperFlowing");
	}
	
	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x,  y,  z).getMaterial().isLiquid()) return false;
        return super.canDisplace(world, x, y, z);
	}
	
	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		if(world.getBlock(x,y,z).getMaterial().isLiquid()) return false;
		return super.displaceIfPossible(world, x, y, z);
	}

}
