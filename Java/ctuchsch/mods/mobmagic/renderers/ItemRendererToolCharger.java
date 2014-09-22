package ctuchsch.mods.mobmagic.renderers;

import org.lwjgl.opengl.GL11;

import ctuchsch.mods.mobmagic.models.ModelToolCharger;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererToolCharger implements IItemRenderer {

	private ModelToolCharger model;
	private ResourceLocation texture;
	
	public ItemRendererToolCharger(ModelToolCharger model, ResourceLocation texture) {
		this.model = model;
		this.texture = texture;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		if(type == ItemRenderType.INVENTORY)		
			GL11.glTranslated(0F, 1F, 0F);	
		if(type == ItemRenderType.EQUIPPED)
			GL11.glTranslated(.3F, 1.2F, .3F);
		if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
			GL11.glTranslated(-.3F, 2F, .7F);
		if(type == ItemRenderType.ENTITY)
			GL11.glTranslated(0F, 1.5F, 0F);
		GL11.glRotatef(180F, 0, 0, 1F);
		this.model.render((Entity)null, 0, 0, 0, 0, 0, 0.0625F);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
		this.model.renderTransparentParts((Entity)null, 0, 0, 0, 0, 0, 0.0625F);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glPopMatrix();
	}

}
