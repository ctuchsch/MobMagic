package ctuchsch.mods.mobmagic.utils;

import net.minecraft.nbt.NBTTagCompound;

public class Location {
	public final int x;
	public final int y;
	public final int z;

	public Location(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void writeToNBT(NBTTagCompound compound) {
		compound.setInteger("locationX", x);
		compound.setInteger("locationY", y);
		compound.setInteger("locationZ", z);
	}

	public static Location readFromNBT(NBTTagCompound compound) {
		int x;
		int y;
		int z;
		if (compound != null) {
			if (compound.hasKey("locationX") && compound.hasKey("locationY") && compound.hasKey("locationZ")) {
				x = compound.getInteger("locationX");
				y = compound.getInteger("locationY");
				z = compound.getInteger("locationZ");
				return new Location(x, y, z);
			}
		}
		return null;
	}
}
