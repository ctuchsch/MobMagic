package ctuchsch.mods.mobmagic.items.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;

public class InfusionRecipie {
	private List<FluidStack> fluids = new ArrayList<FluidStack>();
	private ItemStack inputItem;
	private int duration = 800;
	private ItemStack outputItem;

	public InfusionRecipie(ItemStack outputItem, ItemStack inputItem, int duration, FluidStack... fluidStacks) {
		this(outputItem, inputItem);
		this.addFluids(fluidStacks);
		if (duration > 80)
			this.duration = duration;
	}

	public InfusionRecipie(ItemStack outputItem, ItemStack inputItem, FluidStack... fluidStacks) {
		this.inputItem = inputItem;
		this.outputItem = outputItem;
		for (int i = 0; i < fluidStacks.length; i++) {
			fluids.add(fluidStacks[i]);
		}
	}

	public InfusionRecipie(ItemStack outputItem, ItemStack inputItem) {
		this.inputItem = inputItem;
		this.outputItem = outputItem;
	}

	public void addFluids(FluidStack... fluids) {
		for (FluidStack f : fluids)
			addFluid(f);
	}

	public void addFluid(FluidStack fluid) {
		if (fluid.amount > 0) {
			if (this.containsFluid(fluid))
				addToFluidAmount(fluid);

			if (fluids.size() < TileToolCharger.MAX_FLUID_CRAFTING_SLOTS)
				fluids.add(fluid);
		}
	}

	private void addToFluidAmount(FluidStack fluid) {
		for (int i = 0; i < fluids.size(); i++) {
			if (fluids.get(i).isFluidEqual(fluid)) {
				fluids.get(i).amount += fluid.amount;
				return;
			}
		}
	}

	public boolean containsFluid(FluidStack fluid) {
		for (int i = 0; i < fluids.size(); i++) {
			if (fluids.get(i).isFluidEqual(fluid))
				return true;
		}
		return false;
	}

	public boolean containsFulidAndMinimumAmount(FluidStack fluid) {
		for (int i = 0; i < fluids.size(); i++) {
			FluidStack f = fluids.get(i);
			if (f.isFluidEqual(fluid) && f.amount <= fluid.amount)
				return true;
		}
		return false;
	}

	public boolean canCraftWith(ItemStack inputItem, List<FluidStack> fluids) {
		List<FluidStack> matchedStacks = new ArrayList<FluidStack>();
		if (inputItem != null && inputItem.stackSize == 1 && fluids.size() > 0) {
			if (this.inputItem.isItemEqual(inputItem)) {
				for (FluidStack localStack : this.fluids) {
					boolean stepValid = false;
					for (FluidStack fluidsToCheck : fluids) {
						if (localStack.isFluidEqual(fluidsToCheck) && fluidsToCheck.amount >= localStack.amount) {
							matchedStacks.add(fluidsToCheck);
							stepValid = true;
							break;
						}
					}
					if (!stepValid)
						return false;
				}
				if (matchedStacks.size() == fluids.size())
					return true;
			}
		}
		return false;
	}

	public boolean canCraftWith(ItemStack inputItem, FluidStack... fluids) {
		List<FluidStack> matchedStacks = new ArrayList<FluidStack>();
		if (inputItem != null && inputItem.stackSize == 1 && fluids.length > 0) {
			if (this.inputItem.isItemEqual(inputItem)) {
				for (FluidStack localStack : this.fluids) {
					boolean stepValid = false;
					for (FluidStack fluidsToCheck : fluids) {
						if (localStack.isFluidEqual(fluidsToCheck) && fluidsToCheck.amount >= localStack.amount) {
							matchedStacks.add(fluidsToCheck);
							stepValid = true;
							break;
						}
					}
					if (!stepValid)
						return false;
				}
				if (matchedStacks.size() == fluids.length)
					return true;
			}
		}
		return false;
	}
	
	public ItemStack getInputItem() {
		return this.inputItem.copy();
	}
	
	public ItemStack getOutpuItem() {
		return this.outputItem.copy();
	}
	
	public void setDuration(int duration) {
		if(duration > 80)
			this.duration = duration;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public List<FluidStack> getFluids() {
		List<FluidStack> retval = new ArrayList<FluidStack>();
		for(FluidStack f : this.fluids)
			retval.add(f);
		return retval;
	}
}
