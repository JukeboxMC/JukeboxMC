package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityDamageByEntityEvent extends EntityDamageEvent {

    private final Entity damager;

    public EntityDamageByEntityEvent( Entity entity, Entity damager, float damage, DamageSource damageSource ) {
        super( entity, damage, damageSource );
        this.damager = damager;
    }

    public Entity getDamager() {
        return this.damager;
    }
}

