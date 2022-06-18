package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityRabbit extends EntityLiving {

    @Override
    public String getName() {
        return "Rabbit";
    }

    @Override
    public float getWidth() {
        return 0.67f;
    }

    @Override
    public float getHeight() {
        return 0.67f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.RABBIT;
    }
}