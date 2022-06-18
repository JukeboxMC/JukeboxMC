package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityTurtle extends EntityLiving {

    @Override
    public String getName() {
        return "Turtle";
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 0.2f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TURTLE;
    }
}