package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.Event;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public abstract class EntityEvent extends Event {

    private Entity entity;

    public EntityEvent( Entity entity ) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void setEntity( Entity entity ) {
        this.entity = entity;
    }
}
