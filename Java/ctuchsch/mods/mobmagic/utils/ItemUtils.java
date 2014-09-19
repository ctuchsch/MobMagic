package ctuchsch.mods.mobmagic.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemUtils {

	public static ItemStack consumeItem(ItemStack stack) {
		if (stack.stackSize == 1) {
			final Item item = stack.getItem();
			if (item.hasContainerItem(stack)) return item.getContainerItem(stack);
			return null;
		}
		stack.splitStack(1);

		return stack;
	}	
}
