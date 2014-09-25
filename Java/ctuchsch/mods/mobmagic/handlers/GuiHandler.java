package ctuchsch.mods.mobmagic.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.containers.ContainerCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.containers.ContainerItemWand;
import ctuchsch.mods.mobmagic.containers.ContainerToolCharger;
import ctuchsch.mods.mobmagic.gui.GuiCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.gui.GuiItemInventoryWand;
import ctuchsch.mods.mobmagic.gui.GuiToolCharger;
import ctuchsch.mods.mobmagic.items.ItemInventoryWand;
import ctuchsch.mods.mobmagic.tileentities.TileCrystalFurnaceCharger;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {

		// Hooray, no 'magic' numbers - we know exactly which Gui this refers to
		if (guiId == MobMagic.GUI_MOB_WAND) {
			// Use the player's held item to create the inventory
			return new ContainerItemWand(player, player.inventory, new ItemInventoryWand(player.getHeldItem()));
		}
		
		TileEntity entity = world.getTileEntity(x, y, z);
		if (entity != null) {
			if (guiId == MobMagic.GUI_MOB_TOOLCHARGER) {
				if (entity instanceof TileToolCharger)
					return new ContainerToolCharger(player.inventory, (TileToolCharger)entity);
			}
			
			if(guiId == MobMagic.GUI_MOB_CrystalFurnaceCharger){
				if(entity instanceof TileCrystalFurnaceCharger)
					return new ContainerCrystalFurnaceCharger(player.inventory, (TileCrystalFurnaceCharger)entity);
			}
		}
						
		return null;
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {		
		if (guiId == MobMagic.GUI_MOB_WAND) {
			// We have to cast the new container as our custom class
			// and pass in currently held item for the inventory
			return new GuiItemInventoryWand((ContainerItemWand) new ContainerItemWand(player, player.inventory,
					new ItemInventoryWand(player.getHeldItem())));
		}
		TileEntity entity = world.getTileEntity(x, y, z);		
		if (entity != null) {			
			if (guiId == MobMagic.GUI_MOB_TOOLCHARGER) {
				if (entity instanceof TileToolCharger)
					return new GuiToolCharger(player.inventory, (TileToolCharger) entity);
			}
			if(guiId == MobMagic.GUI_MOB_CrystalFurnaceCharger) {
				if(entity instanceof TileCrystalFurnaceCharger)
					return new GuiCrystalFurnaceCharger(player.inventory, (TileCrystalFurnaceCharger)entity);
			}
		}
		return null;
	}

}
