package ctuchsch.mods.mobmagic;

import net.minecraft.client.renderer.entity.RenderSnowball;
import cpw.mods.fml.client.registry.RenderingRegistry;
import ctuchsch.mods.mobmagic.entity.EntityProjectileCreeper;
import ctuchsch.mods.mobmagic.entity.EntityProjectileEnderman;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileCreeper.class, new RenderSnowball(MobMagic.itemEssenceCreeperProjectile));
		RenderingRegistry.registerEntityRenderingHandler(EntityProjectileEnderman.class, new RenderSnowball(MobMagic.itemEssenceEndermanProjectile));
	}
}
