package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.math.Vector;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityVelocityEvent extends EntityEvent implements Cancellable {

    private Vector velocity;

    public EntityVelocityEvent( Entity entity, Vector velocity ) {
        super( entity );
        this.velocity = velocity;
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void setVelocity( Vector velocity ) {
        this.velocity = velocity;
    }
}
