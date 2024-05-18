package org.jukeboxmc.server.anticheat

import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.event.anticheat.CheatDetectedEvent
import org.jukeboxmc.api.math.AxisAlignedBB
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.Player
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author Kaooot
 * @version 1.0
 */
class AntiCheatHelper(private val config: AntiCheatConfigDataProvider) {

    companion object {
        val INSTANCE = AntiCheatHelper(AntiCheatConfigDataProvider())
    }

    fun isBlockInLineOfSight(player: Player, target: Entity): Boolean {
        val direction = this.getDirectionVector(player.getPitch(), player.getYaw())
        val eyeHeightPos = player.getLocation().clone().add(0.0f, player.getEyeHeight(), 0.0f)

        if (this.createBoundingBox(player, eyeHeightPos).intersectsWith(target.getBoundingBox())) {
            return false
        }

        for (i in 1..this.config.getMaxCombatReach().toInt()) {
            val directionPos = direction.clone().multiply(i.toFloat(), i.toFloat(), i.toFloat()).add(eyeHeightPos.getX(), eyeHeightPos.getY(), eyeHeightPos.getZ())

            if (this.createBoundingBox(player, directionPos).intersectsWith(target.getBoundingBox())) {
                return false
            }

            if (player.getWorld().getBlock(directionPos).getType() != BlockType.AIR && player.getWorld().getBlock(directionPos).isSolid()) {
                return true
            }
        }
        return false
    }

    fun isInLineOfSight(player: Player, entity: Entity): Boolean {
        val direction = this.getDirectionVector(player.getPitch(), player.getYaw())
        val eyeHeightPos = player.getLocation().clone().add(0.0f, player.getEyeHeight(), 0.0f)

        if (player.getBoundingBox().intersectsWith(entity.getBoundingBox())) {
            return true
        }

        for (i in 1..this.config.getMaxCombatReach().toInt()) {
            val directionPos = direction.clone().multiply(i.toFloat(), i.toFloat(), i.toFloat()).add(eyeHeightPos.getX(), eyeHeightPos.getY(), eyeHeightPos.getZ())
            if (this.createBoundingBox(player, directionPos).intersectsWith(entity.getBoundingBox())) {
                return true
            }
        }
        return false
    }

    fun callEvent(player: Player, type: CheatDetectedEvent.CheatType, reason: String) {
        val event = CheatDetectedEvent(player, type, reason)
        JukeboxMC.getServer().getPluginManager().callEvent(event)

        if (event.isLogReason()) {
            JukeboxMC.getServer().getLogger().warn("[AntiCheat] $reason")
        }

        if (event.isNotify()) {
            for (onlinePlayer in JukeboxMC.getServer().getOnlinePlayers()) {
                if (!onlinePlayer.hasPermission(this.config.getNotifyPermission())) {
                    continue
                }
                onlinePlayer.sendMessage("[AntiCheat] $reason")
            }
        }
    }

    fun getConfig() = this.config

    private fun createBoundingBox(entity: Entity, position: Vector): AxisAlignedBB =
        AxisAlignedBB(
            position.getX() - entity.getWidth() / 2, position.getY(), position.getZ() - entity.getWidth() / 2,
            position.getX() + entity.getWidth() / 2, position.getY() + entity.getHeight(), position.getZ() + entity.getWidth() / 2
        )

    private fun getDirectionVector(pitchRot: Float, yawRot: Float): Vector {
        val pitch = ((pitchRot + 90) * PI) / 180
        val yaw = ((yawRot + 90) * PI) / 180
        val x = sin(pitch) * cos(yaw)
        val z = sin(pitch) * sin(yaw)
        val y = cos(pitch)
        return Vector(x.toFloat(), y.toFloat(), z.toFloat()).normalize()
    }
}