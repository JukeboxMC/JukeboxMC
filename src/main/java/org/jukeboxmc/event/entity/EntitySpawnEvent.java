package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.Cancellable;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntitySpawnEvent extends EntityEvent implements Cancellable {

    public EntitySpawnEvent( Entity entity ) {
        super( entity );
    }

}
