package ctuchsch.mods.mobmagic;

import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Proxy {
	public void printMessageToPlayer(EntityPlayer player, String msg) {
	}
	
	public void registerRenderers() {
	}
	
	public World getMinecraft(MessageContext ctx) {
		return Minecraft.getMinecraft().theWorld;
	}
}
