package darva.shadowcraft.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import darva.shadowcraft.Main;
import darva.shadowcraft.item.ShadowArmor;

public class EntityBlast extends EntityThrowable {
    /* (non-Javadoc)
	 * @see net.minecraft.entity.projectile.EntityThrowable#getGravityVelocity()
	 */
	@Override
	protected float getGravityVelocity() {
		
		return 0;
	}

	public EntityBlast(World par1World)
    {
        super(par1World);
    }

    public EntityBlast(World par1World, EntityLivingBase par2EntityLivingBase)
    {
        super(par1World, par2EntityLivingBase);
    }

    public EntityBlast(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {
    	ItemStack armor;
    	ShadowArmor sh;
    	int damage = 0;
        if (par1MovingObjectPosition.entityHit != null)
        {
            byte b0 = 0;

            if (par1MovingObjectPosition.entityHit instanceof EntityBlaze)
            {
                b0 = 3;
            }
            armor = Main.getArmor((EntityPlayer) this.getThrower());
            sh = (ShadowArmor) armor.getItem();
            
            switch (armor.stackTagCompound.getInteger("Blast"))
            {
            case 1:
            	damage = 6;
            	break;
            case 2:
            	damage = 8;
            	break;
            case 3:
            	damage = 12;
            	break;
            }
            
            par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)damage);
        }

        for (int i = 0; i < 8; ++i)
        {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
        }
    }

}
