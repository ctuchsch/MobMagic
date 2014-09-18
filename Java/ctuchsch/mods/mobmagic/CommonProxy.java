package ctuchsch.mods.mobmagic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import ctuchsch.mods.mobmagic.containers.ContainerItemWand;
import ctuchsch.mods.mobmagic.gui.GuiItemInventoryWand;
import ctuchsch.mods.mobmagic.items.ItemInventoryWand;

public class CommonProxy implements IGuiHandler {

	public void registerRenderers() {}
	
	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player,
			World world, int x, int y, int z) {
		
		// Hooray, no 'magic' numbers - we know exactly which Gui this refers to
		if (guiId == MobMagic.GUI_MOB_WAND) {
			// Use the player's held item to create the inventory
			return new ContainerItemWand(player, player.inventory,
					new ItemInventoryWand(player.getHeldItem()));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player,
			World world, int x, int y, int z) {
		
		if (guiId == MobMagic.GUI_MOB_WAND) {
			// We have to cast the new container as our custom class
			// and pass in currently held item for the inventory
			return new GuiItemInventoryWand((ContainerItemWand) new ContainerItemWand(
					player, player.inventory, new ItemInventoryWand(
							player.getHeldItem())));
		}
		return null;
	}
}