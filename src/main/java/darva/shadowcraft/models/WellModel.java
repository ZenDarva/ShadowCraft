// Date: 11/21/2013 12:56:05 PM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package darva.shadowcraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WellModel extends ModelBase
{
  //fields
    ModelRenderer Shape1;
    ModelRenderer Shape2;
    ModelRenderer Shape3;
    ModelRenderer Shape3Dup;
    ModelRenderer Shape4;
    ModelRenderer Shape5;
    ModelRenderer Shape5Dup;
    ModelRenderer Shape6;
  
  public WellModel()
  {
    textureWidth = 64;
    textureHeight = 64;
    
      Shape1 = new ModelRenderer(this, 0, 24);
      Shape1.addBox(6F, 18F, -7F, 1, 6, 14);
      Shape1.setRotationPoint(0F, 0F, 0F);
      Shape1.setTextureSize(64, 64);
      Shape1.mirror = true;
      setRotation(Shape1, 0F, 0F, 0F);
      Shape2 = new ModelRenderer(this, 0, 24);
      Shape2.addBox(-7F, 18F, -7F, 1, 6, 14);
      Shape2.setRotationPoint(0F, 0F, 0F);
      Shape2.setTextureSize(64, 64);
      Shape2.mirror = true;
      setRotation(Shape2, 0F, 0F, 0F);
      Shape3 = new ModelRenderer(this, 0, 0);
      Shape3.addBox(-6F, 18F, 6F, 12, 6, 1);
      Shape3.setRotationPoint(0F, 0F, 0F);
      Shape3.setTextureSize(64, 64);
      Shape3.mirror = true;
      setRotation(Shape3, 0F, 0F, 0F);
      Shape3Dup = new ModelRenderer(this, 0, 0);
      Shape3Dup.addBox(-6F, 18F, -7F, 12, 6, 1);
      Shape3Dup.setRotationPoint(0F, 0F, 0F);
      Shape3Dup.setTextureSize(64, 64);
      Shape3Dup.mirror = true;
      setRotation(Shape3Dup, 0F, 0F, 0F);
      Shape4 = new ModelRenderer(this, -1, 45);
      Shape4.addBox(-6F, 23F, -6F, 12, 1, 12);
      Shape4.setRotationPoint(0F, 0F, 0F);
      Shape4.setTextureSize(64, 64);
      Shape4.mirror = true;
      setRotation(Shape4, 0F, 0F, 0F);
      Shape5 = new ModelRenderer(this, 27, 0);
      Shape5.addBox(6F, 2F, -1F, 1, 16, 2);
      Shape5.setRotationPoint(0F, 0F, 0F);
      Shape5.setTextureSize(64, 64);
      Shape5.mirror = true;
      setRotation(Shape5, 0F, 0F, 0F);
      Shape5Dup = new ModelRenderer(this, 27, 0);
      Shape5Dup.addBox(-7F, 2F, -1F, 1, 16, 2);
      Shape5Dup.setRotationPoint(0F, 0F, 0F);
      Shape5Dup.setTextureSize(64, 64);
      Shape5Dup.mirror = true;
      setRotation(Shape5Dup, 0F, 0F, 0F);
      Shape6 = new ModelRenderer(this, 7, 19);
      Shape6.addBox(0F, 2F, -1F, 12, 1, 2);
      Shape6.setRotationPoint(-6F, 0F, 0F);
      Shape6.setTextureSize(64, 64);
      Shape6.mirror = true;
      setRotation(Shape6, 0F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Shape1.render(f5);
    Shape2.render(f5);
    Shape3.render(f5);
    Shape3Dup.render(f5);
    Shape4.render(f5);
    Shape5.render(f5);
    Shape5Dup.render(f5);
    Shape6.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

/* (non-Javadoc)
 * @see net.minecraft.client.model.ModelBase#setRotationAngles(float, float, float, float, float, float, net.minecraft.entity.Entity)
 */
@Override
public void setRotationAngles(float par1, float par2, float par3, float par4,
		float par5, float par6, Entity par7Entity) {
	// TODO Auto-generated method stub
	super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
}
  
  

}
