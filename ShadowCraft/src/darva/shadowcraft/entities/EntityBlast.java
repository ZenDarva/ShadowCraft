package darva.shadowcraft.entities;

import java.sql.Date;
import java.util.Random;
import java.util.Timer;

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
    @Override
	public void onEntityUpdate() {
		// TODO Auto-generated method stub
		super.onEntityUpdate();
		
		if (this.worldObj.isRemote)
			return;
		Random rnd = new Random(System.currentTimeMillis());
		
		if (rnd.nextInt(10) > 2)
		{
			int x,y,z;
			x = (int) this.posX;
			y = (int) this.posY;
			z = (int) this.posZ;
			
			setBlock(this.worldObj,x+1,y,z, 1);
			setBlock(this.worldObj,x-1,y,z, 1);
			setBlock(this.worldObj,x,y+1,z, 1);
			setBlock(this.worldObj,x,y-1,z, 1);
			setBlock(this.worldObj,x,y,z+1, 1);
			setBlock(this.worldObj,x,y,z-1, 1);
		}
	}
    
    private void setBlock(World world, int x, int y, int z, int meta)
	{
		if (world.isAirBlock(x, y, z) && world.getBlockId(x, y, z) != Main.shadowBlock.blockID)
		{
			//Possible, and sane to replace this block with shadows.
			world.setBlock(x, y, z, Main.shadowBlock.blockID, meta, 1|2);
			world.scheduleBlockUpdate(x, y, z, Main.shadowBlock.blockID, 10);
		}
	}

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
    protected void onImpact(MovingObjectPosition mop)
    {
    	ItemStack armor;
    	ShadowArmor sh;
    	int damage = 0;
        if (mop.entityHit != null)
        {
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
            
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float)damage);
        }

        for (int i = 0; i < 8; ++i)
        {
            this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

        if (!this.worldObj.isRemote)
        {
            this.setDead();
            setBlock(this.worldObj, mop.blockX, mop.blockY-1, mop.blockZ, 9);
        }
    }

}
