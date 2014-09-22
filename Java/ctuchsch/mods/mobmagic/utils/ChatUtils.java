package ctuchsch.mods.mobmagic.utils;

import ctuchsch.mods.mobmagic.MobMagic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class ChatUtils {

	public static void printMessageToPlayer(String msg, World world, EntityPlayer player) {
		if(world.isRemote)
			MobMagic.proxy.printMessageToPlayer(player, msg);
	}
	
}
