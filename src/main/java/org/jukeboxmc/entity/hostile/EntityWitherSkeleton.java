package org.jukeboxmc.entity.hostile;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityWitherSkeleton extends EntityLiving {

    @Override
    public String getName() {
        return "Wither Skeleton ";
    }

    @Override
    public float getWidth() {
        return 0.72f;
    }

    @Override
    public float getHeight() {
        return 2.01f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.WITHER_SKELETON;
    }
}