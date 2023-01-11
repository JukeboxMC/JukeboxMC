package org.jukeboxmc.event.entity;

import org.jukeboxmc.entity.Entity;
import org.jukeboxmc.event.Cancellable;
import org.jukeboxmc.potion.Effect;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityRemoveEffectEvent extends EntityEvent implements Cancellable {

    private final Effect effect;

    public EntityRemoveEffectEvent( Entity entity, Effect effect ) {
        super( entity );
        this.effect = effect;
    }

    public Effect getEffect() {
        return this.effect;
    }
}
