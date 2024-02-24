package org.jukeboxmc.server.item.behavior

import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.entity.EntityLiving
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.entity.hostile.*
import org.jukeboxmc.server.entity.neutral.*
import org.jukeboxmc.server.entity.passive.*
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

/**
 * @author Kaooot
 * @version 1.0
 */
class ItemSpawnEgg(private val itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId) {

    override fun interact(player: JukeboxPlayer, blockFace: BlockFace, clickedPosition: Vector, clickedBlock: Block?): Boolean {
        if (clickedBlock == null) {
            return false
        }

        var entity: EntityLiving? = null

        when (this.itemType) {
            ItemType.ALLAY_SPAWN_EGG -> entity = JukeboxEntityAllay()
            ItemType.AXOLOTL_SPAWN_EGG -> entity = JukeboxEntityAxolotl()
            ItemType.BAT_SPAWN_EGG -> entity = JukeboxEntityBat()
            ItemType.CAMEL_SPAWN_EGG -> entity = JukeboxEntityCamel()
            ItemType.CAT_SPAWN_EGG -> entity = JukeboxEntityCat()
            ItemType.CHICKEN_SPAWN_EGG -> entity = JukeboxEntityChicken()
            ItemType.COD_SPAWN_EGG -> entity = JukeboxEntityCod()
            ItemType.COW_SPAWN_EGG -> entity = JukeboxEntityCow()
            ItemType.DONKEY_SPAWN_EGG -> entity = JukeboxEntityDonkey()
            ItemType.FROG_SPAWN_EGG -> entity = JukeboxEntityFrog()
            ItemType.GLOW_SQUID_SPAWN_EGG -> entity = JukeboxEntityGlowSquid()
            ItemType.HORSE_SPAWN_EGG -> entity = JukeboxEntityHorse()
            ItemType.MOOSHROOM_SPAWN_EGG -> entity = JukeboxEntityMooshroom()
            ItemType.MULE_SPAWN_EGG -> entity = JukeboxEntityMule()
            ItemType.OCELOT_SPAWN_EGG -> entity = JukeboxEntityOcelot()
            ItemType.PARROT_SPAWN_EGG -> entity = JukeboxEntityParrot()
            ItemType.PIG_SPAWN_EGG -> entity = JukeboxEntityPig()
            ItemType.PUFFERFISH_SPAWN_EGG -> entity = JukeboxEntityPufferfish()
            ItemType.RABBIT_SPAWN_EGG -> entity = JukeboxEntityRabbit()
            ItemType.SALMON_SPAWN_EGG -> entity = JukeboxEntitySalmon()
            ItemType.SHEEP_SPAWN_EGG -> entity = JukeboxEntitySheep()
            ItemType.SKELETON_HORSE_SPAWN_EGG -> entity = JukeboxEntitySkeletonHorse()
            ItemType.SNIFFER_SPAWN_EGG -> entity = JukeboxEntitySniffer()
            ItemType.SNOW_GOLEM_SPAWN_EGG -> entity = JukeboxEntitySnowGolem()
            ItemType.SQUID_SPAWN_EGG -> entity = JukeboxEntitySquid()
            ItemType.STRIDER_SPAWN_EGG -> entity = JukeboxEntityStrider()
            ItemType.TADPOLE_SPAWN_EGG -> entity = JukeboxEntityTadpole()
            ItemType.TROPICAL_FISH_SPAWN_EGG -> entity = JukeboxEntityTropicalFish()
            ItemType.TURTLE_SPAWN_EGG -> entity = JukeboxEntityTurtle()
            ItemType.VILLAGER_SPAWN_EGG -> entity = JukeboxEntityVillager()
            ItemType.WANDERING_TRADER_SPAWN_EGG -> entity = JukeboxEntityWanderingTrader()
            ItemType.ZOMBIE_HORSE_SPAWN_EGG -> entity = JukeboxEntityZombieHorse()
            ItemType.BEE_SPAWN_EGG -> entity = JukeboxEntityBee()
            ItemType.CAVE_SPIDER_SPAWN_EGG -> entity = JukeboxEntityCaveSpider()
            ItemType.DOLPHIN_SPAWN_EGG -> entity = JukeboxEntityDolphin()
            ItemType.DROWNED_SPAWN_EGG -> entity = JukeboxEntityDrowned()
            ItemType.ENDERMAN_SPAWN_EGG -> entity = JukeboxEntityEnderman()
            ItemType.FOX_SPAWN_EGG -> entity = JukeboxEntityFox()
            ItemType.GOAT_SPAWN_EGG -> entity = JukeboxEntityGoat()
            ItemType.IRON_GOLEM_SPAWN_EGG -> entity = JukeboxEntityIronGolem()
            ItemType.LLAMA_SPAWN_EGG -> entity = JukeboxEntityLlama()
            ItemType.PANDA_SPAWN_EGG -> entity = JukeboxEntityPanda()
            ItemType.PIGLIN_SPAWN_EGG -> entity = JukeboxEntityPiglin()
            ItemType.POLAR_BEAR_SPAWN_EGG -> entity = JukeboxEntityPolarBear()
            ItemType.SPIDER_SPAWN_EGG -> entity = JukeboxEntitySpider()
            ItemType.TRADER_LLAMA_SPAWN_EGG -> entity = JukeboxEntityTraderLlama()
            ItemType.WOLF_SPAWN_EGG -> entity = JukeboxEntityWolf()
            ItemType.ZOMBIE_PIGMAN_SPAWN_EGG -> entity = JukeboxEntityZombifiedPiglin()
            ItemType.BLAZE_SPAWN_EGG -> entity = JukeboxEntityBlaze()
            ItemType.CREEPER_SPAWN_EGG -> entity = JukeboxEntityCreeper()
            ItemType.ELDER_GUARDIAN_SPAWN_EGG -> entity = JukeboxEntityElderGuardian()
            ItemType.ENDER_DRAGON_SPAWN_EGG -> entity = JukeboxEntityEnderDragon()
            ItemType.ENDERMITE_SPAWN_EGG -> entity = JukeboxEntityEndermite()
            ItemType.EVOKER_SPAWN_EGG -> entity = JukeboxEntityEvoker()
            ItemType.GHAST_SPAWN_EGG -> entity = JukeboxEntityGhast()
            ItemType.GUARDIAN_SPAWN_EGG -> entity = JukeboxEntityGuardian()
            ItemType.HOGLIN_SPAWN_EGG -> entity = JukeboxEntityHoglin()
            ItemType.HUSK_SPAWN_EGG -> entity = JukeboxEntityHusk()
            ItemType.MAGMA_CUBE_SPAWN_EGG -> entity = JukeboxEntityMagmaCube()
            ItemType.PHANTOM_SPAWN_EGG -> entity = JukeboxEntityPhantom()
            ItemType.PIGLIN_BRUTE_SPAWN_EGG -> entity = JukeboxEntityPiglinBrute()
            ItemType.PILLAGER_SPAWN_EGG -> entity = JukeboxEntityPillager()
            ItemType.RAVAGER_SPAWN_EGG -> entity = JukeboxEntityRavager()
            ItemType.SHULKER_SPAWN_EGG -> entity = JukeboxEntityShulker()
            ItemType.SILVERFISH_SPAWN_EGG -> entity = JukeboxEntitySilverfish()
            ItemType.SKELETON_SPAWN_EGG -> entity = JukeboxEntitySkeleton()
            ItemType.SLIME_SPAWN_EGG -> entity = JukeboxEntitySlime()
            ItemType.STRAY_SPAWN_EGG -> entity = JukeboxEntityStray()
            ItemType.VEX_SPAWN_EGG -> entity = JukeboxEntityVex()
            ItemType.VINDICATOR_SPAWN_EGG -> entity = JukeboxEntityVindicator()
            ItemType.WARDEN_SPAWN_EGG -> entity = JukeboxEntityWarden()
            ItemType.WITCH_SPAWN_EGG -> entity = JukeboxEntityWitch()
            ItemType.WITHER_SPAWN_EGG -> entity = JukeboxEntityWither()
            ItemType.WITHER_SKELETON_SPAWN_EGG -> entity = JukeboxEntityWitherSkeleton()
            ItemType.ZOGLIN_SPAWN_EGG -> entity = JukeboxEntityZoglin()
            ItemType.ZOMBIE_SPAWN_EGG -> entity = JukeboxEntityZombie()
            ItemType.ZOMBIE_VILLAGER_SPAWN_EGG -> entity = JukeboxEntityZombieVillager()
            else -> {}
        }

        if (entity != null) {
            entity.setLocation(clickedBlock.getRelative(blockFace).getLocation().add(0.5f, -entity.getEyeHeight(), 0.5f))
            entity.spawn()

            return true
        }

        return false
    }
}