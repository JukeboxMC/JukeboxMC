package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.item.EntityItem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityItemSpawnEvent extends EntityEvent {

    private final EntityItem entityItem;

    public EntityItemSpawnEvent( EntityItem entity ) {
        super( entity );
        this.entityItem = entity;
    }

    @Override
    public EntityItem getEntity() {
        return this.entityItem;
    }
}
