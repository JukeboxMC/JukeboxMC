package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityFrog extends EntityLiving {

    @Override
    public String getName() {
        return "Frog";
    }

    @Override
    public float getWidth() {
        return 0.5f;
    }

    @Override
    public float getHeight() {
        return 0.55f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.FROG;
    }
}