package ctuchsch.mods.mobmagic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ctuchsch.mods.mobmagic.entity.EntityProjectileCreeper;
import ctuchsch.mods.mobmagic.entity.EntityProjectileEnderman;
import ctuchsch.mods.mobmagic.renderers.RendererEssenceTank;
import ctuchsch.mods.mobmagic.renderers.RendererToolCharger;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;

public class ClientProxy extends Proxy {

	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileCreeper.class, new RenderSnowball(
				MobMagic.itemEssenceCreeperProjectile));
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileEnderman.class, new RenderSnowball(
				MobMagic.itemEssenceEndermanProjectile));
		ClientRegistry.bindTileEntitySpecialRenderer(TileEssenceTank.class, new RendererEssenceTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileToolCharger.class, new RendererToolCharger());
	}

	public static class Icons {
		public static IIcon essenceCreeperFlowing;
		public static IIcon essenceCreeperStill;
		public static IIcon essenceEndermanFlowing;
		public static IIcon essenceEndermanStill;
	}

	@SubscribeEvent
	public void textureHook(TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 0) {
			Icons.essenceEndermanFlowing = event.map.registerIcon(MobMagic.MODID + ":essenceEndermanFlowing");
			Icons.essenceEndermanStill = event.map.registerIcon(MobMagic.MODID + ":essenceEndermanStill");
			Icons.essenceCreeperFlowing = event.map.registerIcon(MobMagic.MODID + ":essenceCreeperFlowing");
			Icons.essenceCreeperStill = event.map.registerIcon(MobMagic.MODID + ":essenceCreeperStill");
			if (MobMagic.essenceEnderman != null)
				MobMagic.essenceEnderman.setIcons(Icons.essenceEndermanStill, Icons.essenceEndermanFlowing);
			if (MobMagic.essenceCreeper != null)
				MobMagic.essenceCreeper.setIcons(Icons.essenceCreeperStill, Icons.essenceCreeperFlowing);
		}
	}

	public void printMessageToPlayer(EntityPlayer player, String msg) {
		player.addChatMessage(new ChatComponentText(msg));
	}
}
