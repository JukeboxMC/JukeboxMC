package org.jukeboxmc.entity.hostile;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntitySpider extends EntityLiving {

    @Override
    public String getName() {
        return "Spider";
    }

    @Override
    public float getWidth() {
        return 1.4f;
    }

    @Override
    public float getHeight() {
        return 0.9f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SPIDER;
    }
}