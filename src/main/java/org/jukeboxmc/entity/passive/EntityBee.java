package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityBee extends EntityLiving {

    @Override
    public String getName() {
        return "Bee";
    }

    @Override
    public float getWidth() {
        return 0.55f;
    }

    @Override
    public float getHeight() {
        return 0.5f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.BEE;
    }
}