package ctuchsch.mods.mobmagic.tileentities;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.fluids.TankEssence;
import ctuchsch.mods.mobmagic.utils.ChatUtils;
import ctuchsch.mods.mobmagic.utils.Location;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class TileToolCharger extends TileEntity implements ISidedInventory {
	private String localizedName;
	private static final double MAX_LINK_DIST = 100D;
	private static final int MAX_TANKS = 6;
	private int TANKS_ALLOWED = 6;
	public int processingSlot = -1;
	public int processingTicks = 0;
	public int ProcessingSpeed = 800;

	public EntityItem itemEntity;
	public ItemStack item;

	public Location tanks[] = new Location[MAX_TANKS];

	public TileToolCharger() {
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if (nbt.hasKey("MyItem")) {
			NBTTagCompound itemComp = (NBTTagCompound) nbt.getTag("MyItem");
			item = ItemStack.loadItemStackFromNBT(itemComp);
			if (item != null)
				itemEntity = new EntityItem(this.worldObj, 0, 0, 0, item);
		}
		if (nbt.hasKey("ProcessingSlot")) {
			processingSlot = nbt.getInteger("ProcessingSlot");
			if (nbt.hasKey("ProcessingTicks"))
				processingTicks = nbt.getInteger("ProcessingTicks");
		}

		NBTTagList list = nbt.getTagList("Locations", 10);
		this.tanks = new Location[MAX_TANKS];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound compound = list.getCompoundTagAt(i);
			int j = compound.getInteger("Slot");
			if (j >= 0 && j < tanks.length)
				tanks[j] = Location.readFromNBT(compound);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if (item != null) 
			nbt.setTag("MyItem", item.writeToNBT(new NBTTagCompound()));		

		if (processingSlot != -1) {
			nbt.setInteger("ProcessingSlot", processingSlot);
			nbt.setInteger("ProcessingTicks", processingTicks);
		}

		NBTTagList list = new NBTTagList();
		for (int i = 0; i < tanks.length; i++) {
			if (tanks[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setInteger("Slot", i);
				tanks[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}
		nbt.setTag("Locations", list);
	}

	private void changeItem(ItemStack item) {
		this.item = item;
		if (item != null)
			this.itemEntity = new EntityItem(this.worldObj, 0, 0, 0, item);
		else
			this.itemEntity = null;
		this.markDirty();
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		this.writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
		this.readFromNBT(packet.func_148857_g());
	}

	@Override
	public void updateEntity() {
		if (itemEntity != null)
			itemEntity.age++;
		if (processingSlot != -1) {
			processingTicks++;
			if (processingTicks >= ProcessingSpeed) {
				processingSlot = -1;
				processingTicks = 0;
			}
		}
	}

	public void setAllowed(int Allowed) {
		if (Allowed <= MAX_TANKS)
			this.TANKS_ALLOWED = Allowed;
	}

	private int getEmptyLink() {
		for (int i = 0; i < this.TANKS_ALLOWED; i++) {
			if (this.tanks[i] == null)
				return i;
		}
		return -1;
	}

	public boolean linkTank(World world, int x, int y, int z, EntityPlayer player) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (this.getDistanceFrom(x, y, z) <= MAX_LINK_DIST) {
			if (tile instanceof TileEssenceTank) {
				int locationTest = getLocation(x, y, z);
				if (locationTest != -1) {
					ChatUtils.printMessageToPlayer("Tank is already linked.", world, player);
					return false;
				}

				int slot = getEmptyLink();
				if (slot != -1) {
					tanks[slot] = new Location(x, y, z);
					ChatUtils.printMessageToPlayer("Tank location linked.", world, player);
					this.markDirty();
					return true;
				}
			}
		}
		return false;
	}

	private int getLocation(int x, int y, int z) {
		Location l;
		for (int i = 0; i < this.TANKS_ALLOWED; i++) {
			l = tanks[i];
			if (l != null) {
				if (l.x == x && l.y == y && l.z == z)
					return i;
			}
		}
		return -1;
	}

	public boolean validateTank(int x, int y, int z) {
		TileEntity tile = this.getWorldObj().getTileEntity(x, y, z);
		if (tile != null) {
			if (tile instanceof TileEssenceTank) {
				if (tile.getDistanceFrom(this.xCoord, this.yCoord, this.zCoord) <= MAX_LINK_DIST)
					return true;
			}
		}

		return false;
	}

	public double getScaledFluidAmount(int slot, int segments) {
		TileEssenceTank tank = getTank(slot);
		if (tank != null)
			return tank.getScaledFluidLevel(segments);
		return 0;
	}

	public int getFluidAmount(int slot) {
		TileEssenceTank tank = getTank(slot);
		if (tank != null)
			return tank.tank.getFluidAmount();
		return 0;
	}

	public int getCapcity(int slot) {
		TileEssenceTank tank = getTank(slot);
		if (tank != null)
			return tank.tank.getCapacity();
		return 0;
	}

	public FluidStack getFluid(int slot) {
		TileEssenceTank tank = getTank(slot);
		if (tank != null)
			return tank.tank.getFluid();
		return null;
	}

	public FluidStack drain(int slot, int maxDrain, boolean doDrain) {
		TileEssenceTank tank = getTank(slot);
		if (tank != null) {
			return tank.tank.drain(maxDrain, doDrain);
		}
		return null;
	}

	public TileEssenceTank getTank(int slot) {
		if (slot <= TANKS_ALLOWED) {
			Location l = tanks[slot];
			if (l != null) {
				if (validateTank(l.x, l.y, l.z)) {
					return (TileEssenceTank) this.getWorldObj().getTileEntity(l.x, l.y, l.z);
				}
			}
		}
		return null;
	}

	public int getNumActiveLinks() {
		int retval = 0;
		for (int i = 0; i < TANKS_ALLOWED; i++) {
			Location l = tanks[i];
			if (l != null) {
				if (validateTank(l.x, l.y, l.z))
					retval++;
			}
		}
		return retval;
	}

	public void clearTanks(World world, EntityPlayer player) {
		for (int i = 0; i < this.MAX_TANKS; i++) {
			tanks[i] = null;
		}
		ChatUtils.printMessageToPlayer("All links cleared.", world, player);
		this.markDirty();
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot == 0)
			return item;
		else
			return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int num) {
		ItemStack retval;
		if (slot == 0) {
			retval = item;
			if (this.item.stackSize <= num)
				changeItem(null);
			else {
				retval = item.splitStack(num);

				if (item.stackSize == 0)
					changeItem(null);
			}
			return retval;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (item != null) {
			ItemStack itemstack = item;
			changeItem(null);
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack myItem) {
		changeItem(myItem);
		if (item != null && item.stackSize > this.getInventoryStackLimit()) {
			item.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.localizedName : "ToolCharger";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.localizedName != null && this.localizedName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
			return false;
		else
			return player.getDistanceSq((double) xCoord, (double) yCoord, (double) zCoord) <= 64.0D;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack p_94041_2_) {
		if (slot == 0)
			return true;
		else
			return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
		return true;
	}

	public void processSlot(int id) {
		if (processingSlot == -1)
			processingSlot = id;
	}
}
