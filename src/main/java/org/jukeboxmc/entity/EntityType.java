package org.jukeboxmc.entity;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.entity.passive.EntityHuman;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@AllArgsConstructor
public enum EntityType {

    HUMAN( EntityHuman.class ),
    ITEM( EntityItem.class );

    private final Class<? extends Entity> entityClass;

    @SneakyThrows
    public <E extends Entity> E createEntity() {
        return (E) this.entityClass.newInstance();
    }
}
