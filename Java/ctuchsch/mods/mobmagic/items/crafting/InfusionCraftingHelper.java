package ctuchsch.mods.mobmagic.items.crafting;

import java.util.List;
import java.util.ArrayList;

import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class InfusionCraftingHelper {
	private static List<InfusionRecipie> infusionRecipies = new ArrayList<InfusionRecipie>();

	public static InfusionCraftingHelper instance = new InfusionCraftingHelper();
	
	public static void addInfusionRecipe(ItemStack outputItem, ItemStack inputItem, int duration, FluidStack... fluidStacks) {
		if (fluidStacks.length <= 0 || fluidStacks.length > TileToolCharger.MAX_FLUID_CRAFTING_SLOTS) 
			throw new RuntimeException("Invalid shapeless recipe!");
		
		if(outputItem == null)
			throw new RuntimeException("Invalid infusion crafting output item.");
		
		InfusionRecipie recipie = new InfusionRecipie(outputItem, inputItem, duration, fluidStacks);
		
		InfusionCraftingHelper.instance.infusionRecipies.add(recipie);
	}
	
	public static void addInfusionRecipe(ItemStack outputItem, ItemStack inputItem, FluidStack... fluidStacks) {
		InfusionCraftingHelper.addInfusionRecipe(outputItem,inputItem,800,fluidStacks);
	}
	
	public static InfusionRecipie getCraftingResult(ItemStack inputItem, List<FluidStack> fluidStacks) {		
		for(InfusionRecipie r : InfusionCraftingHelper.instance.infusionRecipies) {
			if(r.canCraftWith(inputItem, fluidStacks))
				return r;
		}
		return null;
	}
	
	public static InfusionRecipie getCraftingResult(ItemStack inputItem, FluidStack... fluidStacks){		
		for(InfusionRecipie r : InfusionCraftingHelper.instance.infusionRecipies) {
			if(r.canCraftWith(inputItem, fluidStacks))
				return r;
		}
		return null;
	}

}
