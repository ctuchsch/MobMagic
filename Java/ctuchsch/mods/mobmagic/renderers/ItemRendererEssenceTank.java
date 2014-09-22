package ctuchsch.mods.mobmagic.renderers;

import org.lwjgl.opengl.GL11;

import ctuchsch.mods.mobmagic.fluids.TankEssence;
import ctuchsch.mods.mobmagic.models.ModelEssenceTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class ItemRendererEssenceTank implements IItemRenderer {
	private ModelEssenceTank model;
	private ResourceLocation texture;
	
	public ItemRendererEssenceTank(ModelEssenceTank model, ResourceLocation texture) {
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
			GL11.glTranslated(-.3F, 2.1F, .7F);
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
		
		TankEssence tank = new TankEssence(0);
		if(item.hasTagCompound()) {
			tank = (TankEssence)tank.readFromNBT(item.getTagCompound());
		}
		if(tank.getFluidAmount() > 0 && tank.getFluid() != null && tank.getCapacity() > 0)
			drawFluid(tank, type);		
	}
	
	public void drawFluid(FluidTank tank, ItemRenderType type){
		double pixel = (double) 1 / 16;
		int segments = 14;
		double liquidHeight =  (tank.getFluidAmount() * segments / tank.getCapacity()) * pixel;
		
		if (liquidHeight > 0) {
			GL11.glPushMatrix();
			
			if(type == ItemRenderType.INVENTORY) {		
				GL11.glTranslated(-.02F, -.1F, 0F);				
				liquidHeight =  (tank.getFluidAmount() * 8 / tank.getCapacity()) * pixel;
			}
			if(type == ItemRenderType.EQUIPPED)
				GL11.glTranslated(-.2F, -.3F, -.2F);
			if(type == ItemRenderType.EQUIPPED_FIRST_PERSON)
				GL11.glTranslated(-.6F, .60F, .05F);
			if(type == ItemRenderType.ENTITY)
				GL11.glTranslated(-.48F, 0F, -.5F);
			
			GL11.glDisable(GL11.GL_LIGHTING);
			Tessellator t = Tessellator.instance;
			double contOffset = 4;			
			
			FluidStack fluid = tank.getFluid();
			if (fluid.getFluid().getIcon() == null) {
				return;
			}
			IIcon texture = fluid.getFluid().getIcon();
			
			
			double minU = texture.getInterpolatedU(0);
			double maxU = texture.getInterpolatedU(16);
			double minV =  texture.getInterpolatedV(0);
			double maxV = texture.getInterpolatedV(16);

			Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationBlocksTexture);					
			
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

}
