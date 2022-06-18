package org.jukeboxmc.entity;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jukeboxmc.entity.hostile.*;
import org.jukeboxmc.entity.item.EntityItem;
import org.jukeboxmc.entity.passive.*;
import org.jukeboxmc.entity.projectile.EntityArrow;
import org.jukeboxmc.entity.projectile.EntityEgg;
import org.jukeboxmc.entity.projectile.EntityFishingHook;
import org.jukeboxmc.entity.projectile.EntitySnowball;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@AllArgsConstructor
public enum EntityType {

    HUMAN( EntityHuman.class, "minecraft:player" ),
    ITEM( EntityItem.class, "minecraft:item" ),
    ARROW( EntityArrow.class, "minecraft:arrow" ),
    SNOWBALL( EntitySnowball.class, "minecraft:snowball" ),
    FISHING_HOOK( EntityFishingHook.class, "minecraft:fishing_hook" ),
    EGG( EntityEgg.class, "minecraft:egg" ),
    NPC( EntityNPC.class, "minecraft:npc" ),
    ALLAY( EntityAllay.class, "minecraft:allay" ),
    AXOLOTL( EntityAxolotl.class, "minecraft:axolotl" ),
    BLAZE( EntityBlaze.class, "minecraft:blaze" ),
    CAT( EntityCat.class, "minecraft:cat" ),
    CAVE_SPIDER( EntityCaveSpider.class, "minecraft:cave_spider" ),
    COW( EntityCow.class, "minecraft:cow" ),
    CREEPER( EntityCreeper.class, "minecraft:creeper" ),
    DOLPHIN( EntityDolphin.class, "minecraft:dolphin" ),
    DONKEY( EntityDonkey.class, "minecraft:donkey" ),
    DROWNED( EntityDrowned.class, "minecraft:drowned" ),
    ELDER_GUARDIAN( EntityElderGuardian.class, "minecraft:elder_guardian" ),
    ENDERMAN( EntityEnderman.class, "minecraft:enderman" ),
    ENDERMITE( EntityEndermite.class, "minecraft:endermite" ),
    EVOCATION_ILLAGER( EntityEvocationIllager.class, "minecraft:evocation_illager" ),
    FOX( EntityFox.class, "minecraft:fox" ),
    FROG( EntityFrog.class, "minecraft:frog" ),
    GHAST( EntityGhast.class, "minecraft:ghast" ),
    GLOW_SQUID( EntityGlowSquid.class, "minecraft:glow_squid" ),
    GOAT( EntityGoat.class, "minecraft:goat" ),
    GUARDIAN( EntityGuardian.class, "minecraft:guardian" ),
    HOGLIN( EntityHoglin.class, "minecraft:hoglin" ),
    HORSE( EntityHorse.class, "minecraft:horse" ),
    HUSK( EntityHusk.class, "minecraft:husk" ),
    LLAMA( EntityLlama.class, "minecraft:llama" ),
    MAGMA_CUBE( EntityMagmaCube.class, "minecraft:magma_cube" ),
    MOOSHROOM( EntityMooshroom.class, "minecraft:mooshroom" ),
    MULE( EntityMule.class, "minecraft:mule" ),
    OCELOT( EntityOcelot.class, "minecraft:ocelot" ),
    PANDA( EntityPanda.class, "minecraft:panda" ),
    PARROT( EntityParrot.class, "minecraft:parrot" ),
    PIG( EntityPig.class, "minecraft:pig" ),
    PIGLIN( EntityPiglin.class, "minecraft:piglin" ),
    PIGLIN_BRUTE( EntityPiglinBrute.class, "minecraft:piglin_brute" ),
    POLAR_BEAR( EntityPolarBear.class, "minecraft:polar_bear" ),
    SHEEP( EntitySheep.class, "minecraft:sheep" ),
    SHULKER( EntityShulker.class, "minecraft:shulker" ),
    SILVERFISH( EntitySilverfish.class, "minecraft:silverfish" ),
    SKELETON( EntitySkeleton.class, "minecraft:skeleton" ),
    SKELETON_HORSE( EntitySkeletonHorse.class, "minecraft:skeleton_horse" ),
    SLIME( EntitySlime.class, "minecraft:slime" ),
    SPIDER( EntitySpider.class, "minecraft:spider" ),
    SQUID( EntitySquid.class, "minecraft:squid" ),
    STRAY( EntityStray.class, "minecraft:stray" ),
    STRIDER( EntityStrider.class, "minecraft:strider" ),
    TADPOLE( EntityTadpole.class, "minecraft:tadpole" ),
    VINDICATOR( EntityVindicator.class, "minecraft:vindicator" ),
    WANDERING_TRADER( EntityWanderingTrader.class, "minecraft:wandering_trader" ),
    WARDEN( EntityWarden.class, "minecraft:warden" ),
    WITCH( EntityWitch.class, "minecraft:witch" ),
    WITHER_SKELETON( EntityWitherSkeleton.class, "minecraft:wither_skeleton" ),
    ZOGLIN( EntityZoglin.class, "minecraft:zoglin" ),
    ZOMBIE( EntityZombie.class, "minecraft:zombie" ),
    ZOMBIE_HORSE( EntityZombieHorse.class, "minecraft:zombie_horse" ),
    ZOMBIE_PIGMAN( EntityZombiePigman.class, "minecraft:zombie_pigman" );

    private final Class<? extends Entity> entityClass;
    private final String identifier;

    @SneakyThrows
    public <E extends Entity> E createEntity() {
        return (E) this.entityClass.getConstructor().newInstance();
    }

    public String getIdentifier() {
        return this.identifier;
    }
}