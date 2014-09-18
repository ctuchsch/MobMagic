package ctuchsch.mods.mobmagic.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityProjectileEnderman extends EntityThrowable {

	private int dropHeight = 8;

	public EntityProjectileEnderman(World world) {
		super(world);
	}

	public EntityProjectileEnderman(World world, EntityLivingBase player) {
		super(world, player);
	}

	public EntityProjectileEnderman(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if (pos.entityHit != null) {
			Entity temp = pos.entityHit;
			if (temp instanceof EntityLivingBase) {
				EntityLivingBase target = (EntityLivingBase) temp;
				target.setPositionAndUpdate(target.posX, target.posY + dropHeight, target.posZ);
			}
		}
		if (!this.worldObj.isRemote)
			this.setDead();
	}
}
