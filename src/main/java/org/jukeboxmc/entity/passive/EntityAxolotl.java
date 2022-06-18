package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityAxolotl extends EntityLiving {

    @Override
    public String getName() {
        return "Axolotl";
    }

    @Override
    public float getWidth() {
        return 0.75f;
    }

    @Override
    public float getHeight() {
        return 0.42f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.AXOLOTL;
    }
}