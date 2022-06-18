package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityPolarBear extends EntityLiving {

    @Override
    public String getName() {
        return "Polar Bear ";
    }

    @Override
    public float getWidth() {
        return 1.3f;
    }

    @Override
    public float getHeight() {
        return 1.4f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.POLAR_BEAR;
    }
}