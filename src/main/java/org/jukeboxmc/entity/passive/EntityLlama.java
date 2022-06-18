package org.jukeboxmc.entity.passive;

import org.jukeboxmc.entity.EntityLiving;
import org.jukeboxmc.entity.EntityType;

/**
 * @author Kaooot
 * @version 1.0
 */
public class EntityLlama extends EntityLiving {

    @Override
    public String getName() {
        return "Llama";
    }

    @Override
    public float getWidth() {
        return 0.9f;
    }

    @Override
    public float getHeight() {
        return 1.87f;
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.LLAMA;
    }
}