package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityGlowSquid extends EntityLiving {

    @Override
    public String getName() {
        return "Glow Squid ";
    }

    @Override
    public float getWidth() {
        return 0.95f;
    }

    @Override
    public float getHeight() {
        return 0.95f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.GLOW_SQUID;
    }
}