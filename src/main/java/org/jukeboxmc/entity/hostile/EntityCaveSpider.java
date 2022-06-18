package org.jukeboxmc.entity.hostile;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityCaveSpider extends EntityLiving {

    @Override
    public String getName() {
        return "Cave Spider ";
    }

    @Override
    public float getWidth() {
        return 0.7f;
    }

    @Override
    public float getHeight() {
        return 0.5f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CAVE_SPIDER;
    }
}