package org.jukeboxmc.server.command.internal

import org.jukeboxmc.api.command.Command
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ParameterType
import org.jukeboxmc.api.command.annotation.*
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.server.entity.hostile.*
import org.jukeboxmc.server.entity.neutral.*
import org.jukeboxmc.server.entity.passive.*

/**
 * @author Kaooot
 * @version 1.0
 */
@Name("summon")
@Description("Summons an entity.")
@Permission("jukeboxmc.command.summon")
@Parameters(
    parameter = [
        Parameter(
            "entityType", enumValues = arrayOf(
                "allay", "armadillo", "axolotl", "bat", "bogged", "breeze", "camel", "cat", "chicken", "cod", "cow", "donkey", "frog", "glow_squid", "horse", "mooshroom", "mule", "ocelot", "parrot", "pig", "pufferfish", "rabbit", "salmon",
                "sheep", "skeleton_horse", "sniffer", "snow_golem", "squid", "strider", "tadpole", "tropical_fish", "turtle", "villager", "wandering_trader", "zombie_horse", "bee", "cave_spider", "dolphin", "drowned", "enderman", "fox", "goat",
                "iron_golem", "llama", "panda", "piglin", "polar_bear", "spider", "trader_llama", "wolf", "zombified_piglin", "blaze", "creeper", "endermite", "evoker", "ghast", "guardian", "hoglin", "husk", "magma_cube", "phantom", "piglin_brute",
                "pillager", "ravager", "shulker", "silverfish", "skeleton", "slime", "stray", "vex", "vindicator", "warden", "witch", "wither_skeleton", "zoglin", "zombie", "zombie_villager", "elder_guardian", "ender_dragon", "wither"
            )
        ),
        Parameter("spawnPos", parameterType = ParameterType.BLOCK_POSITION)
    ]
)
class SummonCommand : Command {

    override fun execute(commandSender: CommandSender, command: String, args: Array<String>) {
        if (commandSender !is Player) {
            commandSender.sendMessage("§cThis command must be executed by a player")
            return
        }

        if (args.size == 4) {
            val argument = args[0]

            if (EntityType.entries.none { it.name.equals(argument, ignoreCase = true) }) {
                commandSender.sendMessage("§cThe provided entity type is invalid")
                return
            }

            val entityType = EntityType.valueOf(argument.uppercase())
            val entity = this.entityByType(entityType)

            if (entity == null) {
                commandSender.sendMessage("§cThis entity cannot be spawned")
                return
            }

            val x: Float? = if (args[1] == "~") commandSender.getLocation().getX() else null
            val y: Float? = if (args[2] == "~") commandSender.getLocation().getY() else null
            val z: Float? = if (args[3] == "~") commandSender.getLocation().getZ() else null
            val vector = Vector(x ?: args[1].toFloat(), y ?: args[2].toFloat(), z ?: args[3].toFloat())

            entity.setLocation(Location(commandSender.getWorld(), vector))
            entity.spawn()

            commandSender.sendMessage("Summoned 1 entity of type " + entity.getName())
        }
    }

    private fun entityByType(type: EntityType): Entity? {
        return when (type) {
            EntityType.ALLAY -> JukeboxEntityAllay()
            EntityType.ARMADILLO -> JukeboxEntityArmadillo()
            EntityType.AXOLOTL -> JukeboxEntityAxolotl()
            EntityType.BAT -> JukeboxEntityBat()
            EntityType.BOGGED -> JukeboxEntityBogged()
            EntityType.BREEZE -> JukeboxEntityBreeze()
            EntityType.CAMEL -> JukeboxEntityCamel()
            EntityType.CAT -> JukeboxEntityCat()
            EntityType.CHICKEN -> JukeboxEntityChicken()
            EntityType.COD -> JukeboxEntityCod()
            EntityType.COW -> JukeboxEntityCow()
            EntityType.DONKEY -> JukeboxEntityDonkey()
            EntityType.FROG -> JukeboxEntityFrog()
            EntityType.GLOW_SQUID -> JukeboxEntityGlowSquid()
            EntityType.HORSE -> JukeboxEntityHorse()
            EntityType.MOOSHROOM -> JukeboxEntityMooshroom()
            EntityType.MULE -> JukeboxEntityMule()
            EntityType.OCELOT -> JukeboxEntityOcelot()
            EntityType.PARROT -> JukeboxEntityParrot()
            EntityType.PIG -> JukeboxEntityPig()
            EntityType.PUFFERFISH -> JukeboxEntityPufferfish()
            EntityType.RABBIT -> JukeboxEntityRabbit()
            EntityType.SALMON -> JukeboxEntitySalmon()
            EntityType.SHEEP -> JukeboxEntitySheep()
            EntityType.SKELETON_HORSE -> JukeboxEntitySkeletonHorse()
            EntityType.SNIFFER -> JukeboxEntitySniffer()
            EntityType.SNOW_GOLEM -> JukeboxEntitySnowGolem()
            EntityType.SQUID -> JukeboxEntitySquid()
            EntityType.STRIDER -> JukeboxEntityStrider()
            EntityType.TADPOLE -> JukeboxEntityTadpole()
            EntityType.TROPICAL_FISH -> JukeboxEntityTropicalFish()
            EntityType.TURTLE -> JukeboxEntityTurtle()
            EntityType.VILLAGER -> JukeboxEntityVillager()
            EntityType.WANDERING_TRADER -> JukeboxEntityWanderingTrader()
            EntityType.ZOMBIE_HORSE -> JukeboxEntityZombieHorse()
            EntityType.BEE -> JukeboxEntityBee()
            EntityType.CAVE_SPIDER -> JukeboxEntityCaveSpider()
            EntityType.DOLPHIN -> JukeboxEntityDolphin()
            EntityType.DROWNED -> JukeboxEntityDrowned()
            EntityType.ENDERMAN -> JukeboxEntityEnderman()
            EntityType.FOX -> JukeboxEntityFox()
            EntityType.GOAT -> JukeboxEntityGoat()
            EntityType.IRON_GOLEM -> JukeboxEntityIronGolem()
            EntityType.LLAMA -> JukeboxEntityLlama()
            EntityType.PANDA -> JukeboxEntityPanda()
            EntityType.PIGLIN -> JukeboxEntityPiglin()
            EntityType.POLAR_BEAR -> JukeboxEntityPolarBear()
            EntityType.SPIDER -> JukeboxEntitySpider()
            EntityType.TRADER_LLAMA -> JukeboxEntityTraderLlama()
            EntityType.WOLF -> JukeboxEntityWolf()
            EntityType.ZOMBIFIED_PIGLIN -> JukeboxEntityZombifiedPiglin()
            EntityType.BLAZE -> JukeboxEntityBlaze()
            EntityType.CREEPER -> JukeboxEntityCreeper()
            EntityType.ENDERMITE -> JukeboxEntityEndermite()
            EntityType.EVOKER -> JukeboxEntityEvoker()
            EntityType.GHAST -> JukeboxEntityGhast()
            EntityType.GUARDIAN -> JukeboxEntityGuardian()
            EntityType.HOGLIN -> JukeboxEntityHoglin()
            EntityType.HUSK -> JukeboxEntityHusk()
            EntityType.MAGMA_CUBE -> JukeboxEntityMagmaCube()
            EntityType.PHANTOM -> JukeboxEntityPhantom()
            EntityType.PIGLIN_BRUTE -> JukeboxEntityPiglinBrute()
            EntityType.PILLAGER -> JukeboxEntityPillager()
            EntityType.RAVAGER -> JukeboxEntityRavager()
            EntityType.SHULKER -> JukeboxEntityShulker()
            EntityType.SILVERFISH -> JukeboxEntitySilverfish()
            EntityType.SKELETON -> JukeboxEntitySkeleton()
            EntityType.SLIME -> JukeboxEntitySlime()
            EntityType.STRAY -> JukeboxEntityStray()
            EntityType.VEX -> JukeboxEntityVex()
            EntityType.VINDICATOR -> JukeboxEntityVindicator()
            EntityType.WARDEN -> JukeboxEntityWarden()
            EntityType.WITCH -> JukeboxEntityWitch()
            EntityType.WITHER_SKELETON -> JukeboxEntityWitherSkeleton()
            EntityType.ZOGLIN -> JukeboxEntityZoglin()
            EntityType.ZOMBIE -> JukeboxEntityZombie()
            EntityType.ZOMBIE_VILLAGER -> JukeboxEntityZombieVillager()
            EntityType.ELDER_GUARDIAN -> JukeboxEntityElderGuardian()
            EntityType.ENDER_DRAGON -> JukeboxEntityEnderDragon()
            EntityType.WITHER -> JukeboxEntityWither()
            else -> null
        }
    }
}