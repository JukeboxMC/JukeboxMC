package org.jukeboxmc.entity.hostile;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityMagmaCube extends EntityLiving {

    @Override
    public String getName() {
        return "Magma Cube ";
    }

    @Override
    public float getWidth() {
        return 2.08f;
    }

    @Override
    public float getHeight() {
        return 2.08f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.MAGMA_CUBE;
    }
}