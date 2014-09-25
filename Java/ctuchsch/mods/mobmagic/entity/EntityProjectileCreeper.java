package ctuchsch.mods.mobmagic.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityProjectileCreeper extends EntityThrowable {

	private float explosionRadius = 1.5F;
	private boolean damageBlocks = true;	
	
	public EntityProjectileCreeper(World world) {
		super(world);
	}

	public EntityProjectileCreeper(World world, EntityLivingBase player) {
		super(world, player);
	}

	public EntityProjectileCreeper(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(pos.entityHit != null) {
			
		}
			
		this.worldObj.createExplosion(this,  this.posX, this.posY, this.posZ, explosionRadius, damageBlocks);
		this.setDead();
	}	
	
	@Override
    protected float getGravityVelocity()
    {
        return 0.14F;
    }
}
