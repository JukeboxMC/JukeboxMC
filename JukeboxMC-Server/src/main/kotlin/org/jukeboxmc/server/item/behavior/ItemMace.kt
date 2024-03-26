package org.jukeboxmc.server.item.behavior

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.item.Durability
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Particle
import org.jukeboxmc.server.entity.Attribute
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.sqrt

/**
 * @author Kaooot
 * @version 1.0
 */
class ItemMace(itemType: ItemType, countNetworkId: Boolean) : JukeboxItem(itemType, countNetworkId), Durability {

    override fun addToHand(player: JukeboxPlayer) {
        player.getAttribute(Attribute.ATTACK_DAMAGE).apply { this.setCurrentValue(7F) }
    }

    override fun removeFromHand(player: JukeboxPlayer) {
        player.getAttribute(Attribute.ATTACK_DAMAGE).apply {
            this.setCurrentValue(this.getMinValue())
        }
    }

    override fun getMaxDurability(): Int = 250

    fun attack(player: JukeboxPlayer, entity: JukeboxEntityLiving, damage: Float): Float {
        val fallingDistance = (player.getHighestPosition() - player.getY())
        if (fallingDistance <= 1.5f) {
            return damage
        }
        var modifiedDamage = damage
        if (player.getArmorInventory().getChestplate().getType() != ItemType.ELYTRA) {
            modifiedDamage *= 1 + (0.5f * fallingDistance)
        }
        if (entity.isOnGround()) {
            player.getWorld().playLevelSound(player.getLocation(), if (fallingDistance > 5.0f) SoundEvent.MACE_SMASH_HEAVY_GROUND else SoundEvent.MACE_SMASH_GROUND)
        } else {
            player.getWorld().playLevelSound(player.getLocation(), SoundEvent.MACE_SMASH_AIR)
        }
        for (living in player.getWorld().getEntities().filterIsInstance<JukeboxEntityLiving>()) {
            if (this.distanceXZ(player, living) > 2.5f) {
                continue
            }
            val v = living.getLocation().clone().subtract(player.getX(), player.getY(), player.getZ())
            val d = (2.5 - sqrt(v.squaredLength())) * 0.6000000238418579 * (1.0 - living.getAttribute(Attribute.KNOCKBACK_RESISTANCE).getCurrentValue())
            v.normalize().multiply(d.toFloat(), d.toFloat(), d.toFloat())
            if (d > 0.0) {
                living.setMovement(Vector(v.getX(), 0.6000000238418579.toFloat(), v.getZ()))
                player.getWorld().spawnParticle(Particle.PARTICLE_DESTROY_BLOCK, living.getLocation().add(0.0f, 0.5f, 0.0f), living.getLocation().getBlock().getNetworkId())
            }
        }
        return modifiedDamage
    }

    private fun distanceXZ(origin: Entity, entity: Entity): Float {
        val dx = entity.getX() - origin.getX()
        val dz = entity.getX() - origin.getZ()
        return sqrt(dx * dx + dz * dz)
    }
}