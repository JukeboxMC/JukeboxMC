package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityAllay extends EntityLiving {

    @Override
    public String getName() {
        return "Allay";
    }

    @Override
    public float getWidth() {
        return 0.35f;
    }

    @Override
    public float getHeight() {
        return 0.6f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ALLAY;
    }
}