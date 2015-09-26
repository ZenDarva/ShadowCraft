package darva.shadowcraft;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class ShadowParticle extends EntityFX {

	private float shadowParticleScale;
	public ShadowParticle(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.motionX *= 0.10000000149011612D;
        this.motionY *= 0.10000000149011612D;
        this.motionZ *= 0.10000000149011612D;
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
        this.particleRed = .15f;
        this.particleBlue = .37f;
        this.particleGreen =.05f;
        this.particleScale *= 0.75F;
        this.particleScale *= 1.0f;
        this.shadowParticleScale = this.particleScale;
        this.particleMaxAge = (int)(4.0D / (Math.random() * 0.8D + 0.2D));
        this.particleMaxAge = (int)((float)this.particleMaxAge * 1.0f);
        this.noClip = true;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.client.particle.EntityFX#renderParticle(net.minecraft.client.renderer.Tessellator, float, float, float, float, float, float)
	 */
	@Override
	public void renderParticle(Tessellator par1Tessellator, float par2,
			float par3, float par4, float par5, float par6, float par7) {
		float f6 = ((float)this.particleAge + par2) / (float)this.particleMaxAge * 32.0F;

        if (f6 < 0.0F)
        {
            f6 = 0.0F;
        }

        if (f6 > 1.0F)
        {
            f6 = 1.0F;
        }

        this.particleScale = this.shadowParticleScale * f6;
        super.renderParticle(par1Tessellator, par2, par3, par4, par5, par6, par7);
	}
	
	   public void onUpdate()
	    {
	        this.prevPosX = this.posX;
	        this.prevPosY = this.posY;
	        this.prevPosZ = this.posZ;

	        if (this.particleAge++ >= this.particleMaxAge)
	        {
	            this.setDead();
	        }

	        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
	        this.motionY += 0.004D;
	        this.moveEntity(this.motionX, this.motionY, this.motionZ);

	        if (this.posY == this.prevPosY)
	        {
	            this.motionX *= 1.1D;
	            this.motionZ *= 1.1D;
	        }

	        this.motionX *= 0.9599999785423279D;
	        this.motionY *= 0.9599999785423279D;
	        this.motionZ *= 0.9599999785423279D;

	        if (this.onGround)
	        {
	            this.motionX *= 0.699999988079071D;
	            this.motionZ *= 0.699999988079071D;
	        }
	    }

}
