package org.jukeboxmc.server.entity

import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.server.entity.hostile.*
import org.jukeboxmc.server.entity.item.*
import org.jukeboxmc.server.entity.neutral.*
import org.jukeboxmc.server.entity.passive.*
import org.jukeboxmc.server.entity.projectile.*

class EntityRegistry {

    companion object {
        private val entityClassFromType: MutableMap<EntityType, Class<out JukeboxEntity>> = mutableMapOf()

        init {
            //Hostile
            register(EntityType.BLAZE, JukeboxEntityBlaze::class.java)
            register(EntityType.BOGGED, JukeboxEntityBogged::class.java)
            register(EntityType.BREEZE, JukeboxEntityBreeze::class.java)
            register(EntityType.CREEPER, JukeboxEntityCreeper::class.java)
            register(EntityType.ELDER_GUARDIAN, JukeboxEntityElderGuardian::class.java)
            register(EntityType.ENDER_DRAGON, JukeboxEntityEnderDragon::class.java)
            register(EntityType.ENDERMITE, JukeboxEntityEndermite::class.java)
            register(EntityType.EVOKER, JukeboxEntityEvoker::class.java)
            register(EntityType.GHAST, JukeboxEntityGhast::class.java)
            register(EntityType.GUARDIAN, JukeboxEntityGuardian::class.java)
            register(EntityType.HOGLIN, JukeboxEntityHoglin::class.java)
            register(EntityType.HUSK, JukeboxEntityHusk::class.java)
            register(EntityType.MAGMA_CUBE, JukeboxEntityMagmaCube::class.java)
            register(EntityType.PHANTOM, JukeboxEntityPhantom::class.java)
            register(EntityType.PIGLIN_BRUTE, JukeboxEntityPiglinBrute::class.java)
            register(EntityType.PILLAGER, JukeboxEntityPillager::class.java)
            register(EntityType.RAVAGER, JukeboxEntityRavager::class.java)
            register(EntityType.SHULKER, JukeboxEntityShulker::class.java)
            register(EntityType.SILVERFISH, JukeboxEntitySilverfish::class.java)
            register(EntityType.SKELETON, JukeboxEntitySkeleton::class.java)
            register(EntityType.SLIME, JukeboxEntitySlime::class.java)
            register(EntityType.STRAY, JukeboxEntityStray::class.java)
            register(EntityType.VEX, JukeboxEntityVex::class.java)
            register(EntityType.VINDICATOR, JukeboxEntityVindicator::class.java)
            register(EntityType.WARDEN, JukeboxEntityWarden::class.java)
            register(EntityType.WITCH, JukeboxEntityWitch::class.java)
            register(EntityType.WITHER, JukeboxEntityWither::class.java)
            register(EntityType.WITHER_SKELETON, JukeboxEntityWitherSkeleton::class.java)
            register(EntityType.ZOGLIN, JukeboxEntityZoglin::class.java)
            register(EntityType.ZOMBIE, JukeboxEntityZombie::class.java)
            register(EntityType.ZOMBIE_VILLAGER, JukeboxEntityZombieVillager::class.java)

            //Item
            register(EntityType.ITEM, JukeboxEntityItem::class.java)

            //Neutral
            register(EntityType.BEE, JukeboxEntityBee::class.java)
            register(EntityType.CAVE_SPIDER, JukeboxEntityCaveSpider::class.java)
            register(EntityType.DOLPHIN, JukeboxEntityDolphin::class.java)
            register(EntityType.DROWNED, JukeboxEntityDrowned::class.java)
            register(EntityType.ENDERMAN, JukeboxEntityEnderman::class.java)
            register(EntityType.FOX, JukeboxEntityFox::class.java)
            register(EntityType.GOAT, JukeboxEntityGoat::class.java)
            register(EntityType.IRON_GOLEM, JukeboxEntityIronGolem::class.java)
            register(EntityType.LLAMA, JukeboxEntityLlama::class.java)
            register(EntityType.PANDA, JukeboxEntityPanda::class.java)
            register(EntityType.PIGLIN, JukeboxEntityPiglin::class.java)
            register(EntityType.POLAR_BEAR, JukeboxEntityPolarBear::class.java)
            register(EntityType.SPIDER, JukeboxEntitySpider::class.java)
            register(EntityType.TRADER_LLAMA, JukeboxEntityTraderLlama::class.java)
            register(EntityType.WOLF, JukeboxEntityWolf::class.java)
            register(EntityType.ZOMBIFIED_PIGLIN, JukeboxEntityZombifiedPiglin::class.java)

            //Passive
            register(EntityType.ALLAY, JukeboxEntityAllay::class.java)
            register(EntityType.ARMADILLO, JukeboxEntityArmadillo::class.java)
            register(EntityType.AXOLOTL, JukeboxEntityAxolotl::class.java)
            register(EntityType.BAT, JukeboxEntityBat::class.java)
            register(EntityType.CAMEL, JukeboxEntityCamel::class.java)
            register(EntityType.CAT, JukeboxEntityCat::class.java)
            register(EntityType.CHICKEN, JukeboxEntityChicken::class.java)
            register(EntityType.COD, JukeboxEntityCod::class.java)
            register(EntityType.COW, JukeboxEntityCow::class.java)
            register(EntityType.DONKEY, JukeboxEntityDonkey::class.java)
            register(EntityType.FROG, JukeboxEntityFrog::class.java)
            register(EntityType.GLOW_SQUID, JukeboxEntityGlowSquid::class.java)
            register(EntityType.HORSE, JukeboxEntityHorse::class.java)
            register(EntityType.HUMAN, JukeboxEntityHuman::class.java)
            register(EntityType.MOOSHROOM, JukeboxEntityMooshroom::class.java)
            register(EntityType.MULE, JukeboxEntityMule::class.java)
            register(EntityType.OCELOT, JukeboxEntityOcelot::class.java)
            register(EntityType.PARROT, JukeboxEntityParrot::class.java)
            register(EntityType.PIG, JukeboxEntityPig::class.java)
            register(EntityType.PUFFERFISH, JukeboxEntityPufferfish::class.java)
            register(EntityType.RABBIT, JukeboxEntityRabbit::class.java)
            register(EntityType.SALMON, JukeboxEntitySalmon::class.java)
            register(EntityType.SHEEP, JukeboxEntitySheep::class.java)
            register(EntityType.SKELETON_HORSE, JukeboxEntitySkeletonHorse::class.java)
            register(EntityType.SNIFFER, JukeboxEntitySniffer::class.java)
            register(EntityType.SNOW_GOLEM, JukeboxEntitySnowGolem::class.java)
            register(EntityType.SQUID, JukeboxEntitySquid::class.java)
            register(EntityType.STRIDER, JukeboxEntityStrider::class.java)
            register(EntityType.TADPOLE, JukeboxEntityTadpole::class.java)
            register(EntityType.TROPICAL_FISH, JukeboxEntityTropicalFish::class.java)
            register(EntityType.TURTLE, JukeboxEntityTurtle::class.java)
            register(EntityType.VILLAGER, JukeboxEntityVillager::class.java)
            register(EntityType.WANDERING_TRADER, JukeboxEntityWanderingTrader::class.java)
            register(EntityType.ZOMBIE_HORSE, JukeboxEntityZombieHorse::class.java)

            //Projectile
            register(EntityType.ARROW, JukeboxEntityArrow::class.java)
            register(EntityType.EGG, JukeboxEntityEgg::class.java)
            register(EntityType.FISHING_HOOK, JukeboxEntityFishingHook::class.java)
            register(EntityType.UNKNOWN, JukeboxEntityProjectile::class.java)
            register(EntityType.SNOWBALL, JukeboxEntitySnowball::class.java)
        }

        private fun register(entityType: EntityType, entityClass: Class<out JukeboxEntity>) {
            this.entityClassFromType[entityType] = entityClass
        }

        fun entityClassExists(entityType: EntityType): Boolean {
            return this.entityClassFromType.containsKey(entityType)
        }

        fun getEntityClass(entityType: EntityType): Class<out JukeboxEntity> {
            return this.entityClassFromType[entityType]!!
        }
    }

}