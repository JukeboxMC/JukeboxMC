package org.jukeboxmc.entity.hostile;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityPiglinBrute extends EntityLiving {

    @Override
    public String getName() {
        return "Piglin Brute ";
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.9f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.PIGLIN_BRUTE;
    }
}