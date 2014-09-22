package ctuchsch.mods.mobmagic.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL40;

import ctuchsch.mods.mobmagic.MobMagic;
import ctuchsch.mods.mobmagic.models.ModelEssenceTank;
import ctuchsch.mods.mobmagic.tileentities.TileEssenceTank;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class RendererEssenceTank extends TileEntitySpecialRenderer {

	private final ModelEssenceTank model;
	private final ResourceLocation tank;
	public RendererEssenceTank(ModelEssenceTank model, ResourceLocation texture) {
		this.model = model;
		this.tank = texture;	
	}

	private void adjustRotatePivotViaMeta(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		GL11.glPushMatrix();
		GL11.glRotatef(meta * (-90), 0.0F, 0.0F, 1.0F);
		GL11.glPopMatrix();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float ptt) {
		//te.getWorldObj().scheduleBlockUpdate(te.xCoord, te.yCoord, te.zCoord, MobMagic.blockEssenceTank, 10);
		if (te instanceof TileEssenceTank) {
			TileEssenceTank myEntity = (TileEssenceTank) te;
			// The PushMatrix tells the renderer to "start" doing something.
			GL11.glPushMatrix();
			// This is setting the initial location.
			GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
			// This is the texture of your block. It's pathed to be the same
			// place
			ResourceLocation textures = (tank);
			Minecraft.getMinecraft().renderEngine.bindTexture(textures);

			// This rotation part is very important! Without it, your model will
			// render upside-down! And for some reason you DO need PushMatrix
			// again!
			// GL11.glPushMatrix();
			GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
			// A reference to your Model file. Again, very important.
			this.model.render((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glEnable(GL11.GL_BLEND);
			// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_COLOR);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDepthMask(false);
			this.model.renderTransparentParts((Entity) null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
			GL11.glDepthMask(true);
			// Tell it to stop rendering for both the PushMatrix's
			GL11.glDisable(GL11.GL_BLEND);
			// GL11.glPopMatrix();
			GL11.glPopMatrix();
			drawFluid(x, y, z, ptt, myEntity);
		}
	}

	private void drawFluid(double x, double y, double z, float ptt, TileEssenceTank te) {
		double pixel = (double) 1 / 16;
		double liquidHeight = te.getScaledFluidLevel(14) * pixel;
		// System.out.println("hi");
		if (liquidHeight > 0) {
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			GL11.glDisable(GL11.GL_LIGHTING);
			Tessellator t = Tessellator.instance;
			double contOffset = 4;			
			
			FluidStack fluid = te.tank.getFluid();
			if (fluid.getFluid().getIcon() == null) {
				return;
			}
			IIcon texture = fluid.getFluid().getIcon();
			
			
			double minU = texture.getInterpolatedU(0);
			double maxU = texture.getInterpolatedU(16);
			double minV =  texture.getInterpolatedV(0);
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
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}
	
	public static ResourceLocation getFluidSheet(FluidStack liquid) {
		if (liquid == null) return TextureMap.locationBlocksTexture;
		return getFluidSheet(liquid.getFluid());
	}

	/**
	 * @param liquid
	 */
	public static ResourceLocation getFluidSheet(Fluid liquid) {
		return TextureMap.locationBlocksTexture;
	}

}
