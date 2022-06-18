package org.jukeboxmc.entity.hostile;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityRavager extends EntityLiving {

    @Override
    public String getName() {
        return "Ravager";
    }

    @Override
    public float getWidth() {
        return 1.2f;
    }

    @Override
    public float getHeight() {
        return 1.9f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.RAVAGER;
    }
}