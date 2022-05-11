package org.jukeboxmc.entity;

import com.nukkitx.protocol.bedrock.data.entity.EntityEventType;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import org.apache.commons.math3.util.FastMath;
import org.jukeboxmc.Server;
import org.jukeboxmc.entity.attribute.Attribute;
import org.jukeboxmc.entity.attribute.AttributeType;
import org.jukeboxmc.event.entity.EntityDamageByEntityEvent;
import org.jukeboxmc.event.entity.EntityDamageEvent;
import org.jukeboxmc.event.entity.EntityHealEvent;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class EntityLiving extends Entity {

    protected int deadTimer = 0;
    protected int fireTicks = 0;
    protected float lastDamage = 0;
    protected byte attackCoolDown = 0;

    protected EntityDamageEvent.DamageSource lastDamageSource;
    protected Entity lastDamageEntity;

    protected final Map<AttributeType, Attribute> attributes = new HashMap<>();

    public EntityLiving() {
        this.addAttribute( AttributeType.HEALTH );
        this.addAttribute( AttributeType.ABSORPTION );
        this.addAttribute( AttributeType.ATTACK_DAMAGE );
        this.addAttribute( AttributeType.FOLLOW_RANGE );
        this.addAttribute( AttributeType.MOVEMENT );
        this.addAttribute( AttributeType.KNOCKBACK_RESISTENCE );
    }

    @Override
    public void update( long currentTick ) {
        if ( !( this.isDead || this.getHealth() <= 0 ) ) {
            super.update( currentTick );
        }

        if ( this.lastDamageEntity != null && this.lastDamageEntity.isDead() ) {
            this.lastDamageEntity = null;
        }

        if ( this.getHealth() < 1 ) {
            if ( this.deadTimer > 0 && this.deadTimer-- > 1 ) {
                this.despawn();
                this.isDead = true;
                this.deadTimer = 0;
            }
        } else {
            this.deadTimer = 0;
        }

        if ( this.isDead() || this.getHealth() <= 0 ) {
            return;
        }

        if ( this.attackCoolDown > 0 ) {
            this.attackCoolDown--;
        }

        if ( this.isOnLadder() ) {
            this.fallDistance = 0;
        }

        if ( this.fireTicks > 0 ) {
            if ( this.fireTicks % 20 == 0 ) {
                this.damage( new EntityDamageEvent( this, 1, EntityDamageEvent.DamageSource.ON_FIRE ) );
            }
            this.fireTicks--;
            if ( this.fireTicks == 0 ) {
                this.setBurning( false );
            }
        }
    }

    @Override
    public boolean damage( EntityDamageEvent event ) {
        if ( this.getHealth() <= 0 ) {
            return false;
        }

        float damage = this.applyArmorReduction( event, false );

        float absorption = this.getAbsorption();
        if ( absorption > 0 ) {
            damage = Math.max( damage - absorption, 0 );
        }

        if ( this.attackCoolDown > 0 && damage <= this.lastDamage ) {
            return false;
        }

        event.setFinalDamage( damage );
        if ( !super.damage( event ) ) {
            return false;
        }

        float damageToBeDealt;

        if ( damage != event.getFinalDamage() ) {
            damageToBeDealt = event.getFinalDamage();
        } else {
            damageToBeDealt = this.applyArmorReduction( event, true );
            absorption = this.getAbsorption();
            if ( absorption > 0 ) {
                float oldDamage = damageToBeDealt;
                damageToBeDealt = Math.max( damage - absorption, 0 );
                this.setAbsorption( absorption - ( oldDamage - damageToBeDealt ) );
            }
        }

        float health = ( this.getHealth() - damageToBeDealt );

        if ( health > 0 ) {
            EntityEventPacket entityEventPacket = new EntityEventPacket();
            entityEventPacket.setRuntimeEntityId( this.entityId );
            entityEventPacket.setType( EntityEventType.HURT );
            Server.getInstance().broadcastPacket( entityEventPacket );
        }

        if ( event instanceof EntityDamageByEntityEvent damageByEntityEvent ) {
            Entity damager = damageByEntityEvent.getDamager();
            if ( damager instanceof Player ) {
                //return ( (Player) damager ).getGameMode().equals( GameMode.SPECTATOR );
            }
            float diffX = this.getX() - damager.getX();
            float diffZ = this.getZ() - damager.getZ();

            float distance = (float) Math.sqrt( diffX * diffX + diffZ * diffZ );
            if ( distance > 0.0 ) {
                float baseModifier = 0.3F;

                distance = 1 / distance;

                Vector velocity = this.getVelocity();
                velocity = velocity.divide( 2f, 2f, 2f );
                velocity = velocity.add(
                        ( diffX * distance * baseModifier ),
                        baseModifier,
                        ( diffZ * distance * baseModifier )
                );

                if ( velocity.getY() > baseModifier ) {
                    velocity.setY( baseModifier );
                }

                this.setVelocity( velocity, true );
            }
        }

        this.lastDamage = damage;
        this.lastDamageSource = event.getDamageSource();
        this.lastDamageEntity = ( event instanceof EntityDamageByEntityEvent ) ? ( (EntityDamageByEntityEvent) event ).getDamager() : null;
        this.attackCoolDown = 10;
        this.setHealth( health <= 0 ? 0 : health );
        return true;
    }

    @Override
    protected void fall() {
        float damage = (float) FastMath.floor( this.fallDistance - 3f );
        if ( damage > 0 ) {
            this.damage( new EntityDamageEvent( this, damage, EntityDamageEvent.DamageSource.FALL ) );
        }
    }

    protected float applyArmorReduction( EntityDamageEvent event, boolean damageArmor ) {
        return event.getDamage();
    }

    public Entity getLastDamageEntity() {
        return this.lastDamageEntity;
    }

    public void setBurning( long value, TimeUnit timeUnit ) {
        int newFireTicks = (int) ( timeUnit.toMillis( value ) / 50 );
        if ( newFireTicks > this.fireTicks ) {
            this.fireTicks = newFireTicks;
            this.setBurning( true );
        } else if ( newFireTicks == 0 ) {
            this.fireTicks = 0;
            this.setBurning( false );
        }
    }

    public void addAttribute( AttributeType attributeType ) {
        this.attributes.put( attributeType, attributeType.getAttribute() );
    }

    public Attribute getAttribute( AttributeType attributeType ) {
        return this.attributes.get( attributeType );
    }

    public void setAttributes( AttributeType attributes, float value ) {
        Attribute attribute = this.attributes.get( attributes );
        if ( attribute == null ) {
            return;
        }
        attribute.setCurrentValue( value );
    }

    public float getAttributeValue( AttributeType attributeType ) {
        return this.getAttribute( attributeType ).getCurrentValue();
    }

    public Collection<Attribute> getAttributes() {
        return this.attributes.values();
    }

    public float getHealth() {
        return this.getAttributeValue( AttributeType.HEALTH );
    }

    public void setHealth( float value ) {
        if ( value < 1 ) {
            this.kill();
        }
        Attribute attribute = this.getAttribute( AttributeType.HEALTH );
        attribute.setCurrentValue( value );
    }

    public void setHeal( float value ) {
        this.setHealth( Math.min( 20, Math.max( 0, value ) ) );
    }

    public void setHeal( float value, EntityHealEvent.Cause cause ) {
        EntityHealEvent entityHealEvent = new EntityHealEvent( this, this.getHealth() + value, cause );
        Server.getInstance().getPluginManager().callEvent( entityHealEvent );
        if ( entityHealEvent.isCancelled() ) {
            return;
        }
        this.setHealth( Math.min( 20, Math.max( 0, entityHealEvent.getHeal() ) ) );
    }

    public float getAbsorption() {
        return this.getAttributeValue( AttributeType.ABSORPTION );
    }

    public void setAbsorption( float value ) {
        this.setAttributes( AttributeType.ABSORPTION, value );
    }

    public float getAttackDamage() {
        return this.getAttributeValue( AttributeType.ATTACK_DAMAGE );
    }

    public void setAttackDamage( float value ) {
        this.setAttributes( AttributeType.ATTACK_DAMAGE, value );
    }

    public float getFollowRange() {
        return this.getAttributeValue( AttributeType.FOLLOW_RANGE );
    }

    public void setFollowRange( float value ) {
        this.setAttributes( AttributeType.FOLLOW_RANGE, value );
    }

    public float getMovement() {
        return this.getAttributeValue( AttributeType.MOVEMENT );
    }

    public void setMovement( float value ) {
        this.setAttributes( AttributeType.MOVEMENT, value );
    }

    public float getKnockbackResistence() {
        return this.getAttributeValue( AttributeType.KNOCKBACK_RESISTENCE );
    }

    public void setKnockbackResistence( float value ) {
        this.setAttributes( AttributeType.KNOCKBACK_RESISTENCE, value );
    }

    protected void kill() {
        this.deadTimer = 20;

        EntityEventPacket entityEventPacket = new EntityEventPacket();
        entityEventPacket.setRuntimeEntityId( this.entityId );
        entityEventPacket.setType( EntityEventType.DEATH );
        Server.getInstance().broadcastPacket( entityEventPacket );

        this.fireTicks = 0;
        this.setBurning( false );
    }


}
