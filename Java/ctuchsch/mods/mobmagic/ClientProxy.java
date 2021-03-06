package ctuchsch.mods.mobmagic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.TextureStitchEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ctuchsch.mods.mobmagic.entity.EntityProjectileCreeper;
import ctuchsch.mods.mobmagic.entity.EntityProjectileEnderman;
import ctuchsch.mods.mobmagic.models.ModelEssenceTank;
import ctuchsch.mods.mobmagic.models.ModelToolCharger;
import ctuchsch.mods.mobmagic.renderers.ItemRendererEssenceTank;
import ctuchsch.mods.mobmagic.renderers.ItemRendererToolCharger;
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
		ModelEssenceTank essenceTankModel = new ModelEssenceTank();
		ResourceLocation essenceTankTexture = new ResourceLocation(MobMagic.MODID + ":textures/models/TankEssence.png");

		ClientRegistry.bindTileEntitySpecialRenderer(TileEssenceTank.class, new RendererEssenceTank(essenceTankModel,
				essenceTankTexture));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MobMagic.blockEssenceTank),
				new ItemRendererEssenceTank(essenceTankModel, essenceTankTexture));
		ModelToolCharger toolChargerModel = new ModelToolCharger();
		ResourceLocation toolChargerTexture = new ResourceLocation(MobMagic.MODID, "textures/models/ModelToolCharger.png");
		ClientRegistry.bindTileEntitySpecialRenderer(TileToolCharger.class, new RendererToolCharger(toolChargerModel,
				toolChargerTexture));
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(MobMagic.blockToolCharger),
				new ItemRendererToolCharger(toolChargerModel, toolChargerTexture));

	}

	public static class Icons {
		public static IIcon essenceCreeperFlowing;
		public static IIcon essenceCreeperStill;
		public static IIcon essenceEndermanFlowing;
		public static IIcon essenceEndermanStill;
		public static IIcon essenceSpiderStill;
		public static IIcon essenceSpiderFlowing;
		public static IIcon essenceAcidStill;
		public static IIcon essenceAcidFlowing;
	}

	@SubscribeEvent
	public void textureHook(TextureStitchEvent.Pre event) {
		if (event.map.getTextureType() == 0) {
			Icons.essenceEndermanFlowing = event.map.registerIcon(MobMagic.MODID + ":essenceEndermanFlowing");
			Icons.essenceEndermanStill = event.map.registerIcon(MobMagic.MODID + ":essenceEndermanStill");
			Icons.essenceCreeperFlowing = event.map.registerIcon(MobMagic.MODID + ":essenceCreeperFlowing");
			Icons.essenceCreeperStill = event.map.registerIcon(MobMagic.MODID + ":essenceCreeperStill");
			Icons.essenceSpiderFlowing = event.map.registerIcon(MobMagic.MODID + ":essenceSpiderFlowing");
			Icons.essenceSpiderStill = event.map.registerIcon(MobMagic.MODID + ":essenceSpiderStill");
			Icons.essenceAcidFlowing = event.map.registerIcon(MobMagic.MODID + ":essenceAcidFlowing");
			Icons.essenceAcidStill = event.map.registerIcon(MobMagic.MODID + ":essenceAcidStill");
			
			MobMagic.essenceEnderman.setIcons(Icons.essenceEndermanStill, Icons.essenceEndermanFlowing);
			MobMagic.essenceCreeper.setIcons(Icons.essenceCreeperStill, Icons.essenceCreeperFlowing);
			MobMagic.essenceSpider.setIcons(Icons.essenceSpiderStill, Icons.essenceSpiderFlowing);
			MobMagic.essenceAcid.setIcons(Icons.essenceAcidStill, Icons.essenceAcidFlowing);
		}
	}

	public void printMessageToPlayer(EntityPlayer player, String msg) {
		player.addChatMessage(new ChatComponentText(msg));
	}
}
