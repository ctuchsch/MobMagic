package ctuchsch.mods.mobmagic.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

public class TileEssenceTank extends TileEntity {
	private String localizedName;
	private final int INV_SIZE = 1;
	private final int TANK_SIZE = 1600;
	private FluidTank[] inventory = new FluidTank[this.INV_SIZE];	
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}
	
	
}
