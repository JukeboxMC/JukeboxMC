package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityCollisionWithEntityEvent extends EntityEvent implements Cancellable {

    private final Entity collideWith;

    public EntityCollisionWithEntityEvent( Entity entity, Entity collideWith ) {
        super( entity );
        this.collideWith = collideWith;
    }

    public Entity getCollideWith() {
        return this.collideWith;
    }
}
