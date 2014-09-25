package ctuchsch.mods.mobmagic.tileentities;

import ctuchsch.mods.mobmagic.fluids.TankEssence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidTank;

public class TileEssenceTank extends TileMobMagicBase {
	private String localizedName;
	private final int INV_SIZE = 1;
	private final int TANK_SIZE = 16000;
	public TankEssence tank;

	public TileEssenceTank() {
		super();
		this.tank = new TankEssence(TANK_SIZE);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		tank = (TankEssence) tank.readFromNBT(compound);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		tank.writeToNBT(compound);
	}
	
	public double getScaledFluidLevel(int segments) {
		return tank.getFluidAmount() * segments / TANK_SIZE;
	}
}
