package ctuchsch.mods.mobmagic;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ctuchsch.mods.mobmagic.containers.ContainerItemWand;
import ctuchsch.mods.mobmagic.gui.GuiItemInventoryWand;
import ctuchsch.mods.mobmagic.items.ItemInventoryWand;
import ctuchsch.mods.mobmagic.renderers.RendererEssenceTank;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;

public class CommonProxy extends Proxy {
	public World getWorld(MessageContext ctx){
		return ctx.getServerHandler().playerEntity.worldObj;
	}
}