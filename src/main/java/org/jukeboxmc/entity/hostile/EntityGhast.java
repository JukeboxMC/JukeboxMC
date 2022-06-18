package org.jukeboxmc.entity.hostile;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityGhast extends EntityLiving {

    @Override
    public String getName() {
        return "Ghast";
    }

    @Override
    public float getWidth() {
        return 4.02f;
    }

    @Override
    public float getHeight() {
        return 4.0f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GHAST;
    }
}