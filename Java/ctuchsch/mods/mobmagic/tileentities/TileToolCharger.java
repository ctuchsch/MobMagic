package ctuchsch.mods.mobmagic.tileentities;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.fluids.TankEssence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class TileToolCharger extends TileEntity {

	private int TANK_SIZE = 16000;
	private int NUM_TANKS = 4;

	public EntityItem itemEntity;
	public ItemStack item;

	public TankEssence tanks[] = new TankEssence[NUM_TANKS];

	public TileToolCharger() {
		super();
		item = new ItemStack(MobMagic.mobWand);
		itemEntity = new EntityItem(this.worldObj, 0, 0, 0, item);
		for(int i = 0; i < NUM_TANKS; i++) {
			tanks[i] = new TankEssence(TANK_SIZE);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		itemEntity.readFromNBT(compound);
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		itemEntity.writeToNBT(compound);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		readFromNBT(packet.func_148857_g());
	}

	@Override
	public void updateEntity() {
		itemEntity.age++;
	}

	public FluidStack getFluid(int slot) {
		if (slot < NUM_TANKS)
			return this.tanks[slot].getFluid();
		return null;
	}

	public int getFluidAmount(int slot) {
		if (slot < NUM_TANKS)
			return this.tanks[slot].getFluidAmount();
		return 0;
	}

	public int getCapacity(int slot) {
		if (slot < NUM_TANKS)
			return this.tanks[slot].getCapacity();
		return 0;
	}

	public FluidTankInfo getInfo(int slot) {
		if (slot < NUM_TANKS)
			return this.tanks[slot].getInfo();
		return null;
	}

	public int fill(FluidStack resource, boolean doFill) {
		// check to see if we have a fluid of this type yet
		int i;
		for (i = 0; i < NUM_TANKS; i++) {
			if (this.tanks[i].getFluid() != null) {
				if (this.tanks[i].getFluid().isFluidEqual(resource))
					return this.tanks[i].fill(resource, doFill);
			}
		}

		// check if any tanks are empty
		for (i = 0; i < NUM_TANKS; i++) {
			if (this.tanks[i].getFluid() == null)
				return this.tanks[i].fill(resource, doFill);
		}

		return 0;
	}
}
