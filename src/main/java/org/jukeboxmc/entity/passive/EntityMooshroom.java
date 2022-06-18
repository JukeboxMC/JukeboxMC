package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityMooshroom extends EntityLiving {

    @Override
    public String getName() {
        return "Mooshroom";
    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        return 1.3f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.MOOSHROOM;
    }
}