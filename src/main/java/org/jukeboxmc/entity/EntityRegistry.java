package org.jukeboxmc.entity;

import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.entity.passive.EntityFallingBlock;
import org.jukeboxmc.entity.passive.EntityHuman;
import org.jukeboxmc.entity.passive.EntityNPC;
import org.jukeboxmc.entity.projectile.EntityArrow;
import org.jukeboxmc.entity.projectile.EntityEgg;
import org.jukeboxmc.entity.projectile.EntityFishingHook;
import org.jukeboxmc.entity.projectile.EntitySnowball;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class EntityRegistry {

    private static final Map<EntityType, Class<? extends Entity>> ENTITYCLASS_FROM_ENTITYTYPE = new LinkedHashMap<>();

    public static void init() {
        register( EntityType.HUMAN, EntityHuman.class );
        register( EntityType.ITEM, EntityItem.class );
        register( EntityType.NPC, EntityNPC.class );
        register( EntityType.ARROW, EntityArrow.class );
        register( EntityType.FISHING_HOOK, EntityFishingHook.class );
        register( EntityType.SNOWBALL, EntitySnowball.class );
        register( EntityType.EGG, EntityEgg.class );
        register( EntityType.FALLING_BLOCK, EntityFallingBlock.class );
    }

    private static void register( EntityType entityType, Class<? extends Entity> entityClazz ) {
        ENTITYCLASS_FROM_ENTITYTYPE.put( entityType, entityClazz );
    }

    public static Class<? extends Entity> getEntityClass( EntityType entityType ) {
        return ENTITYCLASS_FROM_ENTITYTYPE.get( entityType );
    }

}
