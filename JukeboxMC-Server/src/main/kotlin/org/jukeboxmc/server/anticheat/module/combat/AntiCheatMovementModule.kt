package org.jukeboxmc.server.anticheat.module.combat

import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.data.PlayerAuthInputData
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityAbsolutePacket
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket
import org.jukeboxmc.api.event.anticheat.CheatDetectedEvent
import org.jukeboxmc.api.event.player.*
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.anticheat.module.AntiCheatModule
import org.jukeboxmc.server.extensions.fromVector3f
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.extensions.toVector3f
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.sqrt

/**
 * @author Kaooot
 * @version 1.0
 */
class AntiCheatMovementModule : AntiCheatModule {

    fun handle(player: JukeboxPlayer, packet: PlayerAuthInputPacket, server: JukeboxServer): Boolean {
        for (playerAuthInputData in packet.inputData) {
            if (playerAuthInputData == PlayerAuthInputData.START_FLYING && !player.allowFlying() && (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)) {
                this.getHelper().callEvent(player, CheatDetectedEvent.CheatType.MOVEMENT, player.getName() + " failed VanillaFly")
            }
        }

        this.processMove(player, packet)
        this.callEvents(player, packet, server)
        return true
    }

    private fun processMove(player: JukeboxPlayer, packet: PlayerAuthInputPacket) {
        var yaw = packet.rotation.y % 360
        if (yaw < 0) {
            yaw += 360
        }

        val vector: Vector = Vector().fromVector3f(packet.position.sub(0F, player.getEyeHeight(), 0F))
        val toLocation = Location(
            player.getWorld(),
            vector.getX(),
            vector.getY(),
            vector.getZ(),
            yaw,
            packet.rotation.x % 360,
            player.getLocation().getDimension()
        )

        val playerMoveEvent = PlayerMoveEvent(player, player.getLocation(), toLocation)
        JukeboxServer.getInstance().getPluginManager().callEvent(playerMoveEvent)

        if (playerMoveEvent.isCancelled()) {
            playerMoveEvent.setTo(playerMoveEvent.getFrom())
        }

        player.setLastLocation(player.getLocation())
        player.setLocation(toLocation)

        val to = playerMoveEvent.getTo()
        val fromLocation = player.getLocation()

        if (to.getX() != fromLocation.getX() ||
            to.getY() != fromLocation.getY() ||
            to.getZ() != fromLocation.getZ() ||
            to.getWorld() !== fromLocation.getWorld() ||
            to.getYaw() != fromLocation.getYaw() ||
            to.getPitch() != fromLocation.getPitch()
        ) {
            player.teleport(playerMoveEvent.getFrom())
        } else {
            val moveX = toLocation.getX() - player.getLastLocation().getX()
            val moveZ = toLocation.getZ() - player.getLastLocation().getZ()

            val distance = sqrt((moveX * moveX + moveZ * moveZ).toDouble()).toFloat()
            if (distance >= 0.05) {
                val swimmingValue = if (player.isSwimming() || player.isInWater()) 0.01f * distance else 0f
                var distance2 = distance
                if (swimmingValue != 0f) distance2 = 0f

                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if (player.isSprinting()) {
                        player.exhaust(0.1F * distance2 + swimmingValue)
                    } else {
                        player.exhaust(swimmingValue)
                    }
                }
            }

            for (onlinePlayer in player.getServer().getOnlinePlayers()) {
                if (onlinePlayer !== player) {
                    if (player.isSpawned() && onlinePlayer.toJukeboxPlayer().isSpawned()) {
                        this.move(player, onlinePlayer.toJukeboxPlayer())
                    }
                }
            }
        }
    }

    private fun move(target: JukeboxPlayer, player: JukeboxPlayer) {
        val moveAbsolutePacket = MoveEntityAbsolutePacket()
        moveAbsolutePacket.runtimeEntityId = target.getEntityId()
        moveAbsolutePacket.position = target.getLocation().toVector3f().add(0F, target.getEyeHeight(), 0F)
        moveAbsolutePacket.rotation =
            Vector3f.from(target.getLocation().getPitch(), target.getLocation().getYaw(), target.getLocation().getYaw())
        moveAbsolutePacket.isOnGround = target.isOnGround()
        moveAbsolutePacket.isTeleported = false
        player.sendPacket(moveAbsolutePacket)
    }

    private fun callEvents(player: JukeboxPlayer, packet: PlayerAuthInputPacket, server: JukeboxServer) {
        for (inputData in packet.inputData) {
            when (inputData) {
                PlayerAuthInputData.START_SPRINTING -> {
                    this.callPlayerToggleSprint(true, player, server)
                }

                PlayerAuthInputData.STOP_SPRINTING -> {
                    this.callPlayerToggleSprint(false, player, server)
                }

                PlayerAuthInputData.START_SNEAKING -> {
                    this.callPlayerToggleSneak(true, player, server)
                }

                PlayerAuthInputData.STOP_SNEAKING -> {
                    this.callPlayerToggleSneak(false, player, server)
                }

                PlayerAuthInputData.START_SWIMMING -> {
                    this.callPlayerToggleSwim(true, player, server)
                }

                PlayerAuthInputData.STOP_SWIMMING -> {
                    this.callPlayerToggleSwim(false, player, server)
                }

                PlayerAuthInputData.START_JUMPING -> {
                    this.callPlayerJump(player, server)
                }

                PlayerAuthInputData.START_FLYING -> {
                    this.callPlayerToggleFly(true, player, server)
                }

                PlayerAuthInputData.STOP_FLYING -> {
                    this.callPlayerToggleFly(false, player, server)
                }

                PlayerAuthInputData.START_GLIDING -> {
                    this.callPlayerToggleGlide(true, player, server)
                }

                PlayerAuthInputData.STOP_GLIDING -> {
                    this.callPlayerToggleGlide(false, player, server)
                }

                else -> {
                    continue
                }
            }
        }
    }

    private fun callPlayerToggleSprint(sprinting: Boolean, player: JukeboxPlayer, server: JukeboxServer) {
        val playerToggleSprintEvent = PlayerToggleSprintEvent(player, sprinting)
        server.getPluginManager().callEvent(playerToggleSprintEvent)
        if (playerToggleSprintEvent.isCancelled()) {
            player.updateMetadata()
        } else {
            player.setSprinting(sprinting)
        }
    }

    private fun callPlayerToggleSneak(sneaking: Boolean, player: JukeboxPlayer, server: JukeboxServer) {
        val playerToggleSneakEvent = PlayerToggleSneakEvent(player, sneaking)
        server.getPluginManager().callEvent(playerToggleSneakEvent)
        if (playerToggleSneakEvent.isCancelled()) {
            player.updateMetadata()
        } else {
            player.setSneaking(sneaking)
        }
    }

    private fun callPlayerToggleSwim(swimming: Boolean, player: JukeboxPlayer, server: JukeboxServer) {
        val playerToggleSwimEvent = PlayerToggleSwimEvent(player, swimming)
        server.getPluginManager().callEvent(playerToggleSwimEvent)
        if (playerToggleSwimEvent.isCancelled()) {
            player.updateMetadata()
        } else {
            player.setSwimming(swimming)
        }
    }

    private fun callPlayerJump(player: JukeboxPlayer, server: JukeboxServer) {
        val playerJumpEvent = PlayerJumpEvent(player)
        server.getPluginManager().callEvent(playerJumpEvent)
        if (player.getGameMode() == GameMode.SURVIVAL) return
        if (player.isSprinting() && !player.isSwimming()) {
            player.exhaust(0.2F)
        } else {
            player.exhaust(0.05F)
        }
    }

    private fun callPlayerToggleFly(flying: Boolean, player: JukeboxPlayer, server: JukeboxServer) {
        val playerToggleFlyEvent = PlayerToggleFlyEvent(player, flying)
        server.getPluginManager().callEvent(playerToggleFlyEvent)
        if (playerToggleFlyEvent.isCancelled()) {
            player.updateMetadata()
        } else {
            player.setFlying(flying)
        }
    }

    private fun callPlayerToggleGlide(gliding: Boolean, player: JukeboxPlayer, server: JukeboxServer) {
        val playerToggleGlideEvent = PlayerToggleGlideEvent(player, gliding)
        server.getPluginManager().callEvent(playerToggleGlideEvent)
        if (playerToggleGlideEvent.isCancelled()) {
            player.updateMetadata()
        } else {
            player.setGliding(gliding)
        }
    }
}