package org.jukeboxmc.server.anticheat.module.combat

import org.cloudburstmc.protocol.bedrock.data.LevelEvent
import org.cloudburstmc.protocol.bedrock.data.PlayerActionType
import org.cloudburstmc.protocol.bedrock.packet.PlayerAuthInputPacket
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.event.anticheat.CheatDetectedEvent
import org.jukeboxmc.api.event.player.PlayerInteractEvent
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.anticheat.module.AntiCheatModule
import org.jukeboxmc.server.extensions.fromVector3i
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.ceil

/**
 * @author Kaooot
 * @version 1.0
 */
class AntiCheatBlockBreakingModule : AntiCheatModule {

    private val blockPlayerActionTypes = arrayListOf(PlayerActionType.START_BREAK, PlayerActionType.ABORT_BREAK, PlayerActionType.CONTINUE_BREAK, PlayerActionType.BLOCK_PREDICT_DESTROY, PlayerActionType.BLOCK_CONTINUE_DESTROY)
    private val endActionForGameMode = hashMapOf(
        GameMode.SURVIVAL to PlayerActionType.ABORT_BREAK,
        GameMode.CREATIVE to PlayerActionType.BLOCK_PREDICT_DESTROY,
        GameMode.ADVENTURE to PlayerActionType.ABORT_BREAK,
        GameMode.SPECTATOR to PlayerActionType.ABORT_BREAK
    )

    fun handle(player: JukeboxPlayer, packet: PlayerAuthInputPacket, server: JukeboxServer) {
        for (playerAction in packet.playerActions) {
            if (!this.blockPlayerActionTypes.contains(playerAction.action)) {
                continue
            }

            val blockPosition = Vector().fromVector3i(playerAction.blockPosition)
            val lastBlockAction = player.getLastBlockAction()

            /*
            if (this.getHelper().getConfig().isBlockBreakingDetectionEnabled() && player.getWorld().getBlock(blockPosition).getType() == BlockType.AIR) {
                this.updateBlocksAndCallEvent(player, packet)
                return
            }
             */


            if (player.getLastBlockAction() != null && player.getLastBlockAction()!!.action == PlayerActionType.BLOCK_PREDICT_DESTROY &&
                playerAction.action == PlayerActionType.BLOCK_CONTINUE_DESTROY
            ) {
                this.handleStartBreak(player, server, blockPosition)
            }

            if (this.getHelper().getConfig().isBlockBreakingDetectionEnabled() &&
                (((lastBlockAction == null && playerAction.action != PlayerActionType.START_BREAK) || (lastBlockAction != null &&
                        !player.isBreakingBlock() && lastBlockAction.action == this.endActionForGameMode[player.getGameMode()] &&
                        playerAction.action != PlayerActionType.START_BREAK)) || ((player.getGameMode() == GameMode.CREATIVE && playerAction.action == PlayerActionType.ABORT_BREAK)) ||
                        (playerAction.action.equals(PlayerActionType.BLOCK_CONTINUE_DESTROY) && lastBlockAction != null && lastBlockAction.action == PlayerActionType.START_BREAK &&
                        ((player.getGameMode() == GameMode.SURVIVAL && playerAction.action != PlayerActionType.BLOCK_CONTINUE_DESTROY) ||
                                (player.getGameMode() == GameMode.CREATIVE && playerAction.action != PlayerActionType.BLOCK_PREDICT_DESTROY))) || (playerAction.action.equals(PlayerActionType.BLOCK_CONTINUE_DESTROY) && lastBlockAction != null && lastBlockAction.blockPosition.equals(
                    blockPosition
                ) && lastBlockAction.action != PlayerActionType.START_BREAK &&
                        (((lastBlockAction.action != PlayerActionType.BLOCK_PREDICT_DESTROY && lastBlockAction.action != PlayerActionType.BLOCK_CONTINUE_DESTROY && player.getGameMode() == GameMode.SURVIVAL) ||
                                (lastBlockAction.action != PlayerActionType.BLOCK_PREDICT_DESTROY && player.getGameMode() == GameMode.CREATIVE)) || lastBlockAction.action != PlayerActionType.ABORT_BREAK)))
            ) {
                this.updateBlocksAndCallEvent(player, packet)
                return
            }

            if (lastBlockAction != null && (lastBlockAction.blockPosition.x != blockPosition.getBlockX() || lastBlockAction.blockPosition.y != blockPosition.getBlockY() || lastBlockAction.blockPosition.z != blockPosition.getBlockZ())) {
                this.handleAbortBreak(player, Vector().fromVector3i(lastBlockAction.blockPosition))
                this.handleStartBreak(player, server, blockPosition)
            }

            val blockFace = playerAction.face

            if (player.getGameMode() != GameMode.CREATIVE && player.getLocation().distance(blockPosition.clone().add(0.5f, 0.5f, 0.5f)) > this.getHelper().getConfig().getMaxBlockBreakingReach()) {
                this.updateBlocksAndCallEvent(player, packet)
                return
            }

            when (playerAction.action) {
                PlayerActionType.START_BREAK -> {
                    this.handleStartBreak(player, server, blockPosition)
                }

                PlayerActionType.BLOCK_CONTINUE_DESTROY -> {
                    this.handleContinueBreak(player, blockPosition, blockFace)
                }

                PlayerActionType.ABORT_BREAK -> {
                    this.handleAbortBreak(player, blockPosition)
                }

                PlayerActionType.BLOCK_PREDICT_DESTROY -> {
                    this.handleContinueBreak(player, blockPosition, blockFace)
                    player.getWorld().getBlock(blockPosition).toJukeboxBlock().breakBlockHandling(player, player.getInventory().getItemInHand().toJukeboxItem())
                    player.setBreakingBlock(player.getGameMode() != GameMode.CREATIVE)
                }

                else -> {}
            }

            player.setLastBlockAction(playerAction)
        }
    }

    private fun handleStartBreak(player: JukeboxPlayer, server: JukeboxServer, blockPosition: Vector) {
        val currentBreakTime = System.currentTimeMillis()
        val startBreakBlock = player.getWorld().getBlock(blockPosition).toJukeboxBlock()

        val playerInteractEvent = PlayerInteractEvent(
            player,
            if (startBreakBlock.getType() == BlockType.AIR) PlayerInteractEvent.Action.LEFT_CLICK_AIR else PlayerInteractEvent.Action.LEFT_CLICK_BLOCK, player.getInventory().getItemInHand(), startBreakBlock
        )
        server.getPluginManager().callEvent(playerInteractEvent)

        if (player.getGameMode() == GameMode.SURVIVAL) {
            if ((player.getLastBreakPosition() == blockPosition && currentBreakTime - player.getLastBreakTime() < 10) || blockPosition.distanceSquared(player.getLocation()) > 100) {
                return
            }
            val breakTime = ceil(startBreakBlock.calculateBreakTime(player.getInventory().getItemInHand().toJukeboxItem(), player) * 20)
            if (breakTime > 0) {
                player.getWorld().sendLevelEvent(blockPosition, LevelEvent.BLOCK_START_BREAK, (65535 / breakTime).toInt())
            }
        }
        player.setBreakingBlock(true)
        player.setLastBreakPosition(blockPosition)
        player.setLastBreakTime(currentBreakTime)
    }

    private fun handleContinueBreak(player: JukeboxPlayer, blockPosition: Vector, blockFace: Int) {
        if (player.isBreakingBlock()) {
            val continueBlockBreak = player.getWorld().getBlock(blockPosition)
            player.getWorld().sendLevelEvent(blockPosition, LevelEvent.PARTICLE_CRACK_BLOCK, continueBlockBreak.getNetworkId() or (blockFace shl 24))
        }
    }

    private fun handleAbortBreak(player: JukeboxPlayer, blockPosition: Vector) {
        player.getWorld().sendLevelEvent(blockPosition, LevelEvent.BLOCK_STOP_BREAK)
        player.getWorld().getBlock(blockPosition).toJukeboxBlock().update()
        player.setBreakingBlock(false)
    }


    private fun updateBlocksAndCallEvent(player: JukeboxPlayer, packet: PlayerAuthInputPacket) {
        for (playerAction in packet.playerActions) {
            if (!this.blockPlayerActionTypes.contains(playerAction.action)) {
                continue
            }
            player.getWorld().getBlock(Vector().fromVector3i(playerAction.blockPosition)).toJukeboxBlock().update()
        }
        this.getHelper().callEvent(player, CheatDetectedEvent.CheatType.BLOCK_BREAKING, player.getName() + " failed BlockBreakingCheat")
    }
}