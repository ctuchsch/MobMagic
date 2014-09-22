package ctuchsch.mods.mobmagic.renderers;

import org.lwjgl.opengl.GL11;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.models.ModelEssenceTank;
import ctuchsch.mods.mobmagic.models.ModelToolCharger;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import ctuchsch.mods.mobmagic.tileentities.TileToolCharger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class RendererToolCharger extends TileEntitySpecialRenderer {

	private final ModelToolCharger model;
	private final ResourceLocation block;

	public RendererToolCharger(ModelToolCharger model, ResourceLocation texture) {
		this.model = model;
		this.block = texture;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ptt) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		ResourceLocation textures = (block);
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);

		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
		this.model.renderTransparentParts((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);

		GL11.glPopMatrix();
		renderItem(te, x, y, z, ptt);

		renderFluid(te, x, y, z, ptt);
	}

	private void renderFluid(TileEntity te, double x, double y, double z, float ptt) {
		if (te instanceof TileToolCharger) {
			TileToolCharger myCharger = (TileToolCharger) te;
			if (myCharger.processingSlot != -1) {
				double pixel = (double) 1 / 16;
				GL11.glPushMatrix();
				GL11.glTranslated(x, y, z);
				GL11.glDisable(GL11.GL_LIGHTING);
				Tessellator t = Tessellator.instance;
				FluidStack fluid = myCharger.getFluid(myCharger.processingSlot);
				if (fluid != null) {
					double contOffset = 1;
					double quarter = myCharger.ProcessingSpeed / 4;
					double half = myCharger.ProcessingSpeed / 2;
					int segments = 14;
					double liquidHeight = 0D;
					if(myCharger.processingTicks <= quarter)
						liquidHeight = myCharger.processingTicks * segments / quarter;
					else if(myCharger.processingTicks > half + quarter)
						liquidHeight = segments - ((myCharger.processingTicks % (half + quarter)) * segments / quarter);
					else
						liquidHeight = segments;
					
					liquidHeight *= pixel;
					IIcon texture = fluid.getFluid().getIcon();

					double minU = texture.getInterpolatedU(0);
					double maxU = texture.getInterpolatedU(16);
					double minV = texture.getInterpolatedV(0);
					double maxV = texture.getInterpolatedV(16);

					bindTexture(TextureMap.locationBlocksTexture);

					t.startDrawingQuads();
					// front
					t.addVertexWithUV(0 + (pixel * contOffset), 0 + pixel, 0 + (pixel * contOffset), maxU, maxV);
					t.addVertexWithUV(0 + (pixel * contOffset), liquidHeight + pixel, 0 + (pixel * contOffset), maxU, minV);
					t.addVertexWithUV(1 - (pixel * contOffset), liquidHeight + pixel, 0 + (pixel * contOffset), minU, minV);
					t.addVertexWithUV(1 - (pixel * contOffset), 0 + pixel, 0 + (pixel * contOffset), minU, maxV);

					// top
					t.addVertexWithUV(0 + (pixel * contOffset), liquidHeight + pixel, 0 + (pixel * contOffset), maxU, maxV);
					t.addVertexWithUV(0 + (pixel * contOffset), liquidHeight + pixel, 1 - (pixel * contOffset), maxU, minV);
					t.addVertexWithUV(1 - (pixel * contOffset), liquidHeight + pixel, 1 - (pixel * contOffset), minU, minV);
					t.addVertexWithUV(1 - (pixel * contOffset), liquidHeight + pixel, 0 + (pixel * contOffset), minU, maxV);

					// back
					t.addVertexWithUV(1 - (pixel * contOffset), 0 + pixel, 1 - (pixel * contOffset), maxU, maxV);
					t.addVertexWithUV(1 - (pixel * contOffset), liquidHeight + pixel, 1 - (pixel * contOffset), maxU, minV);
					t.addVertexWithUV(0 + (pixel * contOffset), liquidHeight + pixel, 1 - (pixel * contOffset), minU, minV);
					t.addVertexWithUV(0 + (pixel * contOffset), 0 + pixel, 1 - (pixel * contOffset), minU, maxV);

					// side
					t.addVertexWithUV(1 - (pixel * contOffset), 0 + pixel, 0 + (pixel * contOffset), maxU, maxV);
					t.addVertexWithUV(1 - (pixel * contOffset), liquidHeight + pixel, 0 + (pixel * contOffset), maxU, minV);
					t.addVertexWithUV(1 - (pixel * contOffset), liquidHeight + pixel, 1 - (pixel * contOffset), minU, minV);
					t.addVertexWithUV(1 - (pixel * contOffset), 0 + pixel, 1 - (pixel * contOffset), minU, maxV);

					// side
					t.addVertexWithUV(0 + (pixel * contOffset), 0 + pixel, 1 - (pixel * contOffset), maxU, maxV);
					t.addVertexWithUV(0 + (pixel * contOffset), liquidHeight + pixel, 1 - (pixel * contOffset), maxU, minV);
					t.addVertexWithUV(0 + (pixel * contOffset), liquidHeight + pixel, 0 + (pixel * contOffset), minU, minV);
					t.addVertexWithUV(0 + (pixel * contOffset), 0 + pixel, 0 + (pixel * contOffset), minU, maxV);

					t.draw();

				}
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix();
			}
		}
	}

	private void renderItem(TileEntity te, double x, double y, double z, float ptt) {
		double pixel = (double) 1 / 16;
		if (te instanceof TileToolCharger) {
			TileToolCharger myCharger = (TileToolCharger) te;
			if (myCharger.itemEntity != null) {
				GL11.glPushMatrix();
				GL11.glTranslated(x + (8 * pixel), y + (5 * pixel), z + (8 * pixel));
				float scale = .9F;
				GL11.glScalef(scale, scale, scale);
				RenderManager.instance.renderEntityWithPosYaw(myCharger.itemEntity, 0, 0, 0, 0, ptt);
				GL11.glColor3f(1.0F, 1.0F, 1.0F);

				GL11.glPopMatrix();
			}
		}
	}
}
