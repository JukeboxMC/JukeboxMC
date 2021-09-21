package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityHealEvent extends EntityEvent implements Cancellable {

    private float heal;
    private Cause cause;

    public EntityHealEvent( Entity entity, float heal, Cause cause ) {
        super( entity );
        this.heal = heal;
        this.cause = cause;
    }

    public float getHeal() {
        return this.heal;
    }

    public void setHeal( float heal ) {
        this.heal = heal;
    }

    public Cause getCause() {
        return this.cause;
    }

    public void setCause( Cause cause ) {
        this.cause = cause;
    }

    public enum Cause {
        SATURATION,
        REGENERATION_EFFECT,
        HEALING_EFFECT
    }
}
