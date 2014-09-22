package ctuchsch.mods.mobmagic.fluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TankEssence extends FluidTank {

	public TankEssence(int capacity) {
		super(capacity);
	}

	@Override
	public FluidTank readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("TankCapacity"))
			this.setCapacity(nbt.getInteger("TankCapacity"));
		return this;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		// write capacity
		nbt.setInteger("TankCapacity", this.capacity);

		return nbt;
	}

}
