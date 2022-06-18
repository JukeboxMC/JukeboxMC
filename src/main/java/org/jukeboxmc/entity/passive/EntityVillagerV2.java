package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityVillagerV2 extends EntityLiving {

    @Override
    public String getName() {
        return "Villager V2 ";
    }

    @Override
    public float getWidth() {
        return 0.6f;
    }

    @Override
    public float getHeight() {
        return 1.9f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.VILLAGER_V2;
    }
}