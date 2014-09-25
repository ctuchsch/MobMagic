package ctuchsch.mods.mobmagic.tileentities;

import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.blocks.BlockCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.items.CrystalBatteryBase;
import ctuchsch.mods.mobmagic.items.ItemStarshineCore;
import ctuchsch.mods.mobmagic.items.ItemStarshineCrystal;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileCrystalFurnaceCharger extends TileEntity implements ISidedInventory {
	private String localizedName;
	private static final int MAX_SLOTS = 2;
	private static final int[] slotsTop = new int[] { 0, 1 };
	private static final int[] slotsBottom = new int[] { 0, 1 };
	private static final int[] slotsSides = new int[] { 0, 1 };
	private ItemStack[] slots = new ItemStack[2];
	private int chargePacketSize = 8;

	public int burnTime;
	public int currentItemBurnTime;
	public int guiSpeed = 60;

	public TileCrystalFurnaceCharger() {
		super();
	}

	@Override
	public void readFromNBT(NBTTagCompound nbtTag) {
		super.readFromNBT(nbtTag);

		NBTTagList list = nbtTag.getTagList("Items", 10);
		this.slots = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound compound = list.getCompoundTagAt(i);
			int b = compound.getInteger("Slot");

			if (b >= 0 && b < this.slots.length)
				this.slots[b] = ItemStack.loadItemStackFromNBT(compound);
		}

		this.burnTime = (int) nbtTag.getInteger("BurnTime");
		this.currentItemBurnTime = (int) nbtTag.getInteger("CurrentItemBurnTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTag) {
		super.writeToNBT(nbtTag);
		nbtTag.setInteger("BurnTime", this.burnTime);
		nbtTag.setInteger("CurrentItemBurnTime", this.currentItemBurnTime);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < this.slots.length; i++) {
			if (this.slots[i] != null) {
				NBTTagCompound compound = new NBTTagCompound();
				compound.setInteger("Slot", i);
				this.slots[i].writeToNBT(compound);
				list.appendTag(compound);
			}
		}
		nbtTag.setTag("Items", list);
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

	private boolean needsEnergy() {
		if (slots[0] != null && CrystalBatteryBase.canBeCharged(slots[0])) {
			return CrystalBatteryBase.needsPower(slots[0]);
		}
		return false;
	}

	private void addPower() {
		if (slots[0] != null && CrystalBatteryBase.canBeCharged(slots[0])) {
			CrystalBatteryBase.addPower(slots[0], this.chargePacketSize);
		}
	}

	@Override
	public void updateEntity() {
		boolean saveState = false;
		boolean burning = isBurning();
		if (burning)
			--this.burnTime;
		if (!this.worldObj.isRemote) {
			if ((this.burnTime != 0 || this.slots[1] != null) && this.slots[0] != null) {
				if (this.burnTime == 0 && needsEnergy()) {
					this.currentItemBurnTime = this.burnTime = getItemBurnTime(this.slots[1]);

					if (this.burnTime > 0) {
						saveState = true;
						if (this.slots[1] != null) {
							--this.slots[1].stackSize;
							if (this.slots[1].stackSize == 0)
								this.slots[1] = null;
						}
					}
				}

				if (needsEnergy() && isBurning()) {
					addPower();
				}
			}
		}

		if (burning != this.burnTime > 0) {
			saveState = true;
			BlockCrystalFurnaceCharger.updateBlockState(isBurning(), worldObj, xCoord, yCoord, zCoord);
		}

		if (saveState)
			this.markDirty();
	}

	public boolean isBurning() {
		return this.burnTime > 0;
	}

	private int getItemBurnTime(ItemStack itemStack) {
		if (itemStack == null) {
			return 0;
		} else {
			Item item = itemStack.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.wooden_slab) {
					return 30;
				}

				if (block.getMaterial() == Material.wood) {
					return 60;
				}

				if (block == Blocks.coal_block) {
					return 2880;
				}
			}

			if (item instanceof ItemTool && ((ItemTool) item).getToolMaterialName().equals("WOOD"))
				return 40;
			if (item instanceof ItemSword && ((ItemSword) item).getToolMaterialName().equals("WOOD"))
				return 40;
			if (item instanceof ItemHoe && ((ItemHoe) item).getToolMaterialName().equals("WOOD"))
				return 40;
			if (item == Items.stick)
				return 20;
			if (item == Items.coal)
				return 320;
			if (item == Items.lava_bucket)
				return 480;
			if (item == Item.getItemFromBlock(Blocks.sapling))
				return 20;
			if (item == Items.blaze_rod)
				return 480;
			return (int) Math.floor(GameRegistry.getFuelValue(itemStack) * .2D);
		}
	}

	@Override
	public int getSizeInventory() {
		return this.MAX_SLOTS;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return this.slots[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int num) {
		if (this.slots[slot] != null) {
			ItemStack itemstack;

			if (this.slots[slot].stackSize <= num) {
				itemstack = this.slots[slot];
				this.slots[slot] = null;
			} else {
				itemstack = this.slots[slot].splitStack(num);

				if (this.slots[slot].stackSize == 0)
					this.slots[slot] = null;
			}
			return itemstack;
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if (this.slots[slot] != null) {
			ItemStack itemStack = this.slots[slot];
			this.slots[slot] = null;
			return itemStack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack item) {
		this.slots[slot] = item;
		if (item != null && item.stackSize > this.getInventoryStackLimit()) {
			item.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.localizedName : "CrystalFurnaceCharger";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.localizedName != null && this.localizedName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this)
			return false;
		else
			return player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
					(double) this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

	public boolean isItemStackFuel(ItemStack item) {
		return this.getItemBurnTime(item) == 0 ? false : true;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		if (slot == 1)
			return isItemStackFuel(item);
		if (slot == 0) 
		return CrystalBatteryBase.canBeCharged(item);
		return false;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? this.slotsBottom : (side == 1 ? this.slotsTop : this.slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if (slot == 0)
			return slots[0] == null && this.isItemValidForSlot(slot, item);

		return this.isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return slot == 0;
	}

	@Override
	public void markDirty() {
		super.markDirty();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public int getGuiPosScaled(int segments) {
		// int i = this.currentItemBurnTime - this.burnTime;
		// return (i % this.guiSpeed) * segments / this.guiSpeed;
		return (this.currentItemBurnTime - this.burnTime) * segments / this.currentItemBurnTime;
	}
}
