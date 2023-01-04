package org.jukeboxmc.event.entity;

import lombok.ToString;
import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString
public class EntityDamageEvent extends EntityEvent implements Cancellable {

    private float damage;
    private float finalDamage;
    private final DamageSource damageSource;

    public EntityDamageEvent( Entity entity, float damage, DamageSource damageSource ) {
        super( entity );
        this.damage = damage;
        this.damageSource = damageSource;
    }

    public float getDamage() {
        return this.damage;
    }

    public void setDamage( float damage ) {
        this.damage = damage;
    }

    public float getFinalDamage() {
        return this.finalDamage;
    }

    public void setFinalDamage( float finalDamage ) {
        this.finalDamage = finalDamage;
    }

    public DamageSource getDamageSource() {
        return this.damageSource;
    }

    public enum DamageSource {

        ENTITY_ATTACK,
        FALL,
        VOID,
        PROJECTILE,
        DROWNING,
        CACTUS,
        LAVA,
        ON_FIRE,
        FIRE,
        ENTITY_EXPLODE,
        MAGIC_EFFECT,
        STARVE,
        API,
        COMMAND,

    }
}
