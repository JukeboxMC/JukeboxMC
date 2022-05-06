package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityDespawnEvent extends EntityEvent implements Cancellable {

    public EntityDespawnEvent( Entity entity ) {
        super( entity );
    }
}
