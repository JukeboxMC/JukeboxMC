package org.jukeboxmc.entity.projectile;

import org.cloudburstmc.protocol.bedrock.data.SoundEvent;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityMoveable;
import org.jukeboxmc.event.entity.EntityDamageByEntityEvent;
import org.jukeboxmc.event.entity.EntityDamageEvent;
import org.jukeboxmc.event.entity.ProjectileHitEntityEvent;
import org.jukeboxmc.math.AxisAlignedBB;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Sound;

import java.util.Collection;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class EntityProjectile extends EntityMoveable {

    protected EntityLiving shooter;
    protected Entity hitEntity;
    public boolean hadCollision = false;

    @Override
    public void update( long currentTick ) {
        if ( this.isClosed() ) {
            return;
        }
        super.update( currentTick );

        if ( !this.isDead ) {
            if ( this.hitEntity != null ) {
                this.location = this.hitEntity.getLocation().add( 0, this.hitEntity.getEyeHeight() + this.getHeight(), 0 );
            } else {
                Location location = this.location;
                if ( !this.isCollided ) {
                    this.velocity.setY( this.velocity.getY() - this.getGravity() );
                }

                Vector moveVector = new Vector( location.getX() + this.velocity.getX(), location.getY() + this.velocity.getY(), location.getZ() + this.velocity.getZ() );
                Collection<Entity> nearbyEntities = location.getWorld().getNearbyEntities( this.boundingBox.addCoordinates( this.velocity.getX(), this.velocity.getY(), this.velocity.getZ() ).expand( 1, 1, 1 ), location.getDimension(), this );
                float nearDistance = Integer.MAX_VALUE;
                Entity hitEntity = null;
                for ( Entity entity : nearbyEntities ) {
                    if ( ( entity == this.shooter && this.age < 20 ) ) {
                        continue;
                    }
                    AxisAlignedBB axisAlignedBB = entity.getBoundingBox().grow( 0.3f, 0.3f, 0.3f );
                    Vector onLineVector = axisAlignedBB.calculateIntercept( location, moveVector );
                    if ( onLineVector == null ) {
                        continue;
                    }
                    float distance = location.distanceSquared( onLineVector );
                    if ( distance < nearDistance) {
                        nearDistance = distance;
                        hitEntity = entity;
                    }
                }

                if ( hitEntity != null ) {
                    ProjectileHitEntityEvent projectileHitEntityEvent = new ProjectileHitEntityEvent( hitEntity, this );
                    this.getWorld().getServer().getPluginManager().callEvent( projectileHitEntityEvent );

                    if ( projectileHitEntityEvent.isCancelled() ) {
                        return;
                    }
                    float damage = this.getDamage();

                    EntityDamageByEntityEvent event = new EntityDamageByEntityEvent( hitEntity, this.shooter, damage, EntityDamageEvent.DamageSource.PROJECTILE );
                    if ( hitEntity.damage( event ) ) {
                        this.applyCustomKnockback( hitEntity );
                        this.applyCustomDamageEffects( hitEntity );
                        if ( this instanceof EntityArrow ) {
                            if ( this.shooter instanceof Player player ) {
                                player.playSound( Sound.RANDOM_BOWHIT );
                            }
                        }
                        this.updateMetadata(this.metadata.setLong( EntityDataTypes.TARGET_EID, hitEntity.getEntityId() ));
                    }
                    this.onCollidedWithEntity( hitEntity );
                    this.hitEntity = hitEntity;
                    this.updateMovement();
                }

                this.move( this.velocity );

                if ( this.isCollided && !this.hadCollision ) {
                    this.hadCollision = true;

                    this.velocity.setX( 0 );
                    this.velocity.setY( 0 );
                    this.velocity.setZ( 0 );
                    if ( this instanceof EntityArrow ) {
                        this.getWorld().playSound( this.location, SoundEvent.BOW_HIT );
                    }
                    this.updateMovement();
                    return;
                } else if ( !this.isCollided && this.hadCollision ) {
                    this.hadCollision = false;
                }
                if ( !this.hadCollision || Math.abs( this.velocity.getX() ) > 0.00001 || Math.abs( this.velocity.getY() ) > 0.00001 || Math.abs( this.velocity.getZ() ) > 0.00001 ) {
                    double f = Math.sqrt( ( this.velocity.getX() * this.velocity.getX() ) + ( this.velocity.getZ() * this.velocity.getZ() ) );
                    this.setYaw( (float) ( Math.atan2( this.velocity.getX(), this.velocity.getZ() ) * 180 / Math.PI ) );
                    this.setPitch( (float) ( Math.atan2( this.velocity.getY(), f ) * 180 / Math.PI ) );
                }

                this.updateMovement();
            }
        }
    }

    @Override
    public boolean damage( EntityDamageEvent event ) {
        return event.getDamageSource().equals( EntityDamageEvent.DamageSource.VOID ) && super.damage( event );
    }

    @Override
    public boolean canCollideWith( Entity entity ) {
        return ( entity instanceof EntityLiving ) && !this.onGround;
    }

    public float getDamage() {
        return 0;
    }

    protected void applyCustomDamageEffects( Entity hitEntity ) {

    }

    protected void applyCustomKnockback( Entity hitEntity ) {

    }

    public void onCollidedWithEntity( Entity entity ) {

    }

    public EntityLiving getShooter() {
        return this.shooter.isDead() ? null : this.shooter;
    }

    public void setShooter( EntityLiving shooter ) {
        this.shooter = shooter;
        this.metadata.setLong( EntityDataTypes.OWNER_EID, shooter.getEntityId() );
    }
}
