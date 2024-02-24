package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.data.LevelEvent
import org.cloudburstmc.protocol.bedrock.data.PlayerActionType
import org.cloudburstmc.protocol.bedrock.packet.PlayerActionPacket
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.event.player.*
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.extensions.fromVector3i
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.ceil

class PlayerActionHandler : PacketHandler<PlayerActionPacket> {

    override fun handle(packet: PlayerActionPacket, server: JukeboxServer, player: JukeboxPlayer) {
        val breakPosition = Vector().fromVector3i(packet.blockPosition)
        when (packet.action) {
            PlayerActionType.START_SNEAK -> {
                val playerToggleSneakEvent = PlayerToggleSneakEvent(player, true)
                server.getPluginManager().callEvent(playerToggleSneakEvent)
                if (playerToggleSneakEvent.isCancelled()) {
                    player.updateMetadata()
                } else {
                    player.setSneaking(true)
                }
            }

            PlayerActionType.STOP_SNEAK -> {
                val playerToggleSneakEvent = PlayerToggleSneakEvent(player, false)
                server.getPluginManager().callEvent(playerToggleSneakEvent)
                if (playerToggleSneakEvent.isCancelled()) {
                    player.updateMetadata()
                } else {
                    player.setSneaking(false)
                }
            }

            PlayerActionType.START_SPRINT -> {
                val playerToggleSprintEvent = PlayerToggleSprintEvent(player, true)
                server.getPluginManager().callEvent(playerToggleSprintEvent)
                if (playerToggleSprintEvent.isCancelled()) {
                    player.updateMetadata()
                } else {
                    player.setSprinting(true)
                }
            }

            PlayerActionType.STOP_SPRINT -> {
                val playerToggleSprintEvent = PlayerToggleSprintEvent(player, false)
                server.getPluginManager().callEvent(playerToggleSprintEvent)
                if (playerToggleSprintEvent.isCancelled()) {
                    player.updateMetadata()
                } else {
                    player.setSprinting(false)
                }
            }

            PlayerActionType.START_SWIMMING -> {
                val playerStartSwimming = PlayerToggleSwimEvent(player, true)
                server.getPluginManager().callEvent(playerStartSwimming)
                if (playerStartSwimming.isCancelled()) {
                    player.updateMetadata()
                } else {
                    player.setSwimming(true)
                }
            }

            PlayerActionType.STOP_SWIMMING -> {
                val playerStartSwimming = PlayerToggleSwimEvent(player, false)
                server.getPluginManager().callEvent(playerStartSwimming)
                if (playerStartSwimming.isCancelled()) {
                    player.updateMetadata()
                } else {
                    player.setSwimming(false)
                }
            }

            PlayerActionType.START_GLIDE -> {
                val playerToggleGlideEvent = PlayerToggleGlideEvent(player, true)
                server.getPluginManager().callEvent(playerToggleGlideEvent)
                if (playerToggleGlideEvent.isCancelled()) {
                    player.updateMetadata()
                } else {
                    player.setGliding(true)
                }
            }

            PlayerActionType.STOP_GLIDE -> {
                val playerToggleGlideEvent = PlayerToggleGlideEvent(player, false)
                server.getPluginManager().callEvent(playerToggleGlideEvent)
                if (playerToggleGlideEvent.isCancelled()) {
                    player.updateMetadata()
                } else {
                    player.setGliding(false)
                }
            }

            PlayerActionType.JUMP -> {
                val playerJumpEvent = PlayerJumpEvent(player)
                server.getPluginManager().callEvent(playerJumpEvent)
                if (player.getGameMode() == GameMode.SURVIVAL) return
                if (player.isSprinting() && !player.isSwimming()) {
                    player.exhaust(0.2F)
                } else {
                    player.exhaust(0.05F)
                }
            }

            PlayerActionType.DIMENSION_CHANGE_SUCCESS -> {
                val playerActionPacket = PlayerActionPacket()
                playerActionPacket.action = PlayerActionType.DIMENSION_CHANGE_SUCCESS
                player.sendPacket(playerActionPacket)
            }

            PlayerActionType.START_BREAK -> {
                val currentBreakTime = System.currentTimeMillis()
                val startBreakBlock = player.getWorld().getBlock(breakPosition).toJukeboxBlock()

                val playerInteractEvent = PlayerInteractEvent(player,
                        if (startBreakBlock.getType() == BlockType.AIR) PlayerInteractEvent.Action.LEFT_CLICK_AIR else PlayerInteractEvent.Action.LEFT_CLICK_BLOCK, player.getInventory().getItemInHand(), startBreakBlock)
                server.getPluginManager().callEvent(playerInteractEvent)

                if (player.getGameMode() == GameMode.SURVIVAL) {
                    if ((player.getLasBreakPosition() == breakPosition && currentBreakTime - player.getLastBreakTime() < 10) || breakPosition.distanceSquared(player.getLocation()) > 100) {
                        return
                    }
                    val breakTime = ceil(startBreakBlock.calculateBreakTime(player.getInventory().getItemInHand().toJukeboxItem(), player) * 20)
                    if (breakTime > 0) {
                        player.getWorld().sendLevelEvent(breakPosition, LevelEvent.BLOCK_START_BREAK, (65535 / breakTime).toInt())
                    }
                }
                player.setBreakingBlock(true)
                player.setLastBreakPosition(breakPosition)
                player.setLastBreakTime(currentBreakTime)
            }

            PlayerActionType.CONTINUE_BREAK -> {
                if (player.isBreakingBlock()) {
                    val continueBlockBreak = player.getWorld().getBlock(breakPosition)
                    player.getWorld().sendLevelEvent(breakPosition, LevelEvent.PARTICLE_CRACK_BLOCK, continueBlockBreak.getNetworkId() or (packet.face shl 24))
                }
            }
            PlayerActionType.STOP_BREAK, PlayerActionType.ABORT_BREAK -> {
                player.getWorld().sendLevelEvent(breakPosition, LevelEvent.BLOCK_STOP_BREAK)
            }
            PlayerActionType.RESPAWN -> {
                player.respawn()
            }
            PlayerActionType.START_FLYING -> {
                val playerToggleFlyEvent = PlayerToggleFlyEvent(player, true)
                server.getPluginManager().callEvent(playerToggleFlyEvent)
                if (playerToggleFlyEvent.isCancelled()) {
                    player.setFlying(false)
                }
            }
            PlayerActionType.STOP_FLYING -> {
                val playerToggleFlyEvent = PlayerToggleFlyEvent(player, false)
                server.getPluginManager().callEvent(playerToggleFlyEvent)
                if (playerToggleFlyEvent.isCancelled()) {
                    player.setFlying(true)
                }
            }
            else -> {}
        }
    }
}