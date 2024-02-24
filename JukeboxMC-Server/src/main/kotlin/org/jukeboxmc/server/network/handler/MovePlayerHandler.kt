package org.jukeboxmc.server.network.handler

import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.packet.MoveEntityAbsolutePacket
import org.cloudburstmc.protocol.bedrock.packet.MovePlayerPacket
import org.jukeboxmc.api.event.player.PlayerMoveEvent
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.fromVector3f
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.extensions.toVector3f
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.sqrt

class MovePlayerHandler : PacketHandler<MovePlayerPacket> {

    override fun handle(packet: MovePlayerPacket, server: JukeboxServer, player: JukeboxPlayer) {
        if (!player.isSpawned()) return

        val vector: Vector = Vector().fromVector3f(packet.position.sub(0F, player.getEyeHeight(), 0F))
        val toLocation = Location(
            player.getWorld(),
            vector.getX(),
            vector.getY(),
            vector.getZ(),
            packet.rotation.y,
            packet.rotation.x,
            player.getLocation().getDimension()
        )

        val playerMoveEvent = PlayerMoveEvent(player, player.getLocation(), toLocation)
        JukeboxServer.getInstance().getPluginManager().callEvent(playerMoveEvent)

        if (playerMoveEvent.isCancelled()) {
            playerMoveEvent.setTo(playerMoveEvent.getFrom())
        }

        player.setLastLocation(player.getLocation())
        player.setLocation(toLocation)
        player.setOnGround(packet.isOnGround)

        val to = playerMoveEvent.getTo()
        val fromLocation = player.getLocation()

        if (to.getX() != fromLocation.getX() ||
            to.getY() != fromLocation.getY() ||
            to.getZ() != fromLocation.getZ() ||
            to.getWorld() !== fromLocation.getWorld() ||
            to.getYaw() != fromLocation.getYaw() ||
            to.getPitch() != fromLocation.getPitch()) {
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
                       player.exhaust(0.1F * distance2  + swimmingValue)
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
}