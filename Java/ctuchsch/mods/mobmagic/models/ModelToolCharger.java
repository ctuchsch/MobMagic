package ctuchsch.mods.mobmagic.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelToolCharger extends ModelBase {
	// fields
	ModelRenderer Bottom;
	ModelRenderer Top1;
	ModelRenderer Piller1;
	ModelRenderer Piller2;
	ModelRenderer Piller3;
	ModelRenderer Piller4;
	ModelRenderer Base1;
	ModelRenderer Base2;
	ModelRenderer Top2;
	ModelRenderer top3;
	ModelRenderer top4;
	ModelRenderer GlassTop;
	ModelRenderer GlassSide1;
	ModelRenderer GlassSide2;
	ModelRenderer GlassSide3;
	ModelRenderer GlassSide4;

	public ModelToolCharger() {
		textureWidth = 64;
		textureHeight = 64;

		Bottom = new ModelRenderer(this, 0, 0);
		Bottom.addBox(-8F, 0F, -8F, 16, 1, 16);
		Bottom.setRotationPoint(0F, 23F, 0F);
		Bottom.setTextureSize(64, 64);
		Bottom.mirror = true;
		setRotation(Bottom, 0F, 0F, 0F);
		Top1 = new ModelRenderer(this, 0, 34);
		Top1.addBox(-8F, 0F, -8F, 16, 1, 1);
		Top1.setRotationPoint(0F, 8F, 0F);
		Top1.setTextureSize(64, 64);
		Top1.mirror = true;
		setRotation(Top1, 0F, 0F, 0F);
		Piller1 = new ModelRenderer(this, 0, 18);
		Piller1.addBox(0F, 0F, 0F, 1, 14, 1);
		Piller1.setRotationPoint(7F, 9F, 7F);
		Piller1.setTextureSize(64, 64);
		Piller1.mirror = true;
		setRotation(Piller1, 0F, 0F, 0F);
		Piller2 = new ModelRenderer(this, 0, 18);
		Piller2.addBox(0F, 0F, 0F, 1, 14, 1);
		Piller2.setRotationPoint(-8F, 9F, 7F);
		Piller2.setTextureSize(64, 64);
		Piller2.mirror = true;
		setRotation(Piller2, 0F, 0F, 0F);
		Piller3 = new ModelRenderer(this, 0, 18);
		Piller3.addBox(0F, 0F, 0F, 1, 14, 1);
		Piller3.setRotationPoint(-8F, 9F, -8F);
		Piller3.setTextureSize(64, 64);
		Piller3.mirror = true;
		setRotation(Piller3, 0F, 0F, 0F);
		Piller4 = new ModelRenderer(this, 0, 18);
		Piller4.addBox(0F, 0F, 0F, 1, 14, 1);
		Piller4.setRotationPoint(7F, 9F, -8F);
		Piller4.setTextureSize(64, 64);
		Piller4.mirror = true;
		setRotation(Piller4, 0F, 0F, 0F);
		Base1 = new ModelRenderer(this, 30, 17);
		Base1.addBox(-4F, 0F, -4F, 8, 1, 8);
		Base1.setRotationPoint(0F, 22F, 0F);
		Base1.setTextureSize(64, 64);
		Base1.mirror = true;
		setRotation(Base1, 0F, 0F, 0F);
		Base2 = new ModelRenderer(this, 30, 26);
		Base2.addBox(-2F, 0F, -2F, 4, 1, 4);
		Base2.setRotationPoint(0F, 21F, 0F);
		Base2.setTextureSize(64, 64);
		Base2.mirror = true;
		setRotation(Base2, 0F, 0F, 0F);
		Top2 = new ModelRenderer(this, 0, 34);
		Top2.addBox(-8F, 0F, 0F, 16, 1, 1);
		Top2.setRotationPoint(0F, 8F, 7F);
		Top2.setTextureSize(64, 64);
		Top2.mirror = true;
		setRotation(Top2, 0F, 0F, 0F);
		top3 = new ModelRenderer(this, 0, 19);
		top3.addBox(0F, 0F, -7F, 1, 1, 14);
		top3.setRotationPoint(-8F, 8F, 0F);
		top3.setTextureSize(64, 64);
		top3.mirror = true;
		setRotation(top3, 0F, 0F, 0F);
		top4 = new ModelRenderer(this, 0, 19);
		top4.addBox(0F, 0F, -7F, 1, 1, 14);
		top4.setRotationPoint(7F, 8F, 0F);
		top4.setTextureSize(64, 64);
		top4.mirror = true;
		setRotation(top4, 0F, 0F, 0F);
		GlassTop = new ModelRenderer(this, 0, 49);
		GlassTop.addBox(-7F, 0F, -7F, 14, 1, 14);
		GlassTop.setRotationPoint(0F, 8F, 0F);
		GlassTop.setTextureSize(64, 64);
		GlassTop.mirror = true;
		setRotation(GlassTop, 0F, 0F, 0F);
		GlassSide1 = new ModelRenderer(this, 0, 49);
		GlassSide1.addBox(-7F, -7F, 0F, 14, 14, 1);
		GlassSide1.setRotationPoint(0F, 16F, 7F);
		GlassSide1.setTextureSize(64, 64);
		GlassSide1.mirror = true;
		setRotation(GlassSide1, 0F, 0F, 0F);
		GlassSide2 = new ModelRenderer(this, 0, 49);
		GlassSide2.addBox(-7F, -7F, 0F, 14, 14, 1);
		GlassSide2.setRotationPoint(0F, 16F, -8F);
		GlassSide2.setTextureSize(64, 64);
		GlassSide2.mirror = true;
		setRotation(GlassSide2, 0F, 0F, 0F);
		GlassSide3 = new ModelRenderer(this, 0, 36);
		GlassSide3.addBox(0F, -7F, -7F, 1, 14, 14);
		GlassSide3.setRotationPoint(-8F, 16F, 0F);
		GlassSide3.setTextureSize(64, 64);
		GlassSide3.mirror = true;
		setRotation(GlassSide3, 0F, 0F, 0F);
		GlassSide4 = new ModelRenderer(this, 0, 36);
		GlassSide4.addBox(0F, -7F, -7F, 1, 14, 14);
		GlassSide4.setRotationPoint(7F, 16F, 0F);
		GlassSide4.setTextureSize(64, 64);
		GlassSide4.mirror = true;
		setRotation(GlassSide4, 0F, 0F, 0F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		Bottom.render(f5);
		Top1.render(f5);
		Piller1.render(f5);
		Piller2.render(f5);
		Piller3.render(f5);
		Piller4.render(f5);
		Base1.render(f5);
		Base2.render(f5);
		Top2.render(f5);
		top3.render(f5);
		top4.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z) {
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	}

	public void renderTransparentParts(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		GlassTop.render(f5);
		GlassSide1.render(f5);
		GlassSide2.render(f5);
		GlassSide3.render(f5);
		GlassSide4.render(f5);
	}

}
