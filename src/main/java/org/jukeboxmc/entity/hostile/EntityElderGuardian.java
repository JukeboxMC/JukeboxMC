package org.jukeboxmc.entity.hostile;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityElderGuardian extends EntityLiving {

    @Override
    public String getName() {
        return "Elder Guardian ";
    }

    @Override
    public float getWidth() {
        return 1.99f;
    }

    @Override
    public float getHeight() {
        return 1.99f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ELDER_GUARDIAN;
    }
}