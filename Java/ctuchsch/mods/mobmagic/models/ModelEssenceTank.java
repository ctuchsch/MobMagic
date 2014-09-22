package ctuchsch.mods.mobmagic.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEssenceTank extends ModelBase
{
  //fields
    ModelRenderer jarTop;
    ModelRenderer jarBottom;
    ModelRenderer Tank;
  
  public ModelEssenceTank()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      jarTop = new ModelRenderer(this, 0, 0);
      jarTop.addBox(0F, 0F, 0F, 16, 1, 16);
      jarTop.setRotationPoint(-8F, 8F, -8F);
      jarTop.setTextureSize(64, 64);
      jarTop.mirror = true;
      setRotation(jarTop, 0F, 0F, 0F);
      jarBottom = new ModelRenderer(this, 0, 0);
      jarBottom.addBox(0F, 0F, 0F, 16, 1, 16);
      jarBottom.setRotationPoint(-8F, 23F, -8F);
      jarBottom.setTextureSize(64, 64);
      jarBottom.mirror = true;
      setRotation(jarBottom, 0F, 0F, 0F);
      Tank = new ModelRenderer(this, 0, 17);
      Tank.addBox(-5F, 0F, -5F, 10, 14, 10);
      Tank.setRotationPoint(0F, 9F, 0F);
      Tank.setTextureSize(64, 64);
      Tank.mirror = true;
      setRotation(Tank, 0F, 0F, 0F);      
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    jarTop.render(f5);
    jarBottom.render(f5);       
  }
  
  public void renderTransparentParts(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
	  Tank.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5,entity);
  }

}
