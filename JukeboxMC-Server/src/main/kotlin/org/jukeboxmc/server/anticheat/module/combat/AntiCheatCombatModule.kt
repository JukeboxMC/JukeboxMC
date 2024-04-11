package org.jukeboxmc.server.anticheat.module.combat

import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.server.anticheat.module.AntiCheatModule
import org.jukeboxmc.server.player.JukeboxPlayer
import java.util.*

/**
 * @author Kaooot
 * @version 1.0
 */
class AntiCheatCombatModule : AntiCheatModule {

    private val lastArmAnimations = hashMapOf<UUID, Long>()
    private val lastHitSounds = hashMapOf<UUID, Long>()

    fun isPlayerAllowedToHitEntity(player: JukeboxPlayer, entity: Entity): Boolean {
        return !this.getHelper().getConfig().isCombatDetectionEnabled() || (this.wasLastArmAnimationSent(player.getUUID()) && this.wasLastHitSoundSent(player.getUUID()) &&
                !this.getHelper().isBlockInLineOfSight(player, entity) && this.getHelper().isInLineOfSight(player, entity))
    }

    fun registerSwingArmTimestamp(uuid: UUID) = this.registerTimestamp(this.lastArmAnimations, uuid)

    fun registerHitSoundTimestamp(uuid: UUID) = this.registerTimestamp(this.lastHitSounds, uuid)

    fun cleanUp(player: JukeboxPlayer) {
        this.lastArmAnimations.remove(player.getUUID())
        this.lastHitSounds.remove(player.getUUID())
    }

    private fun wasLastArmAnimationSent(uuid: UUID): Boolean =
        if (!this.lastArmAnimations.containsKey(uuid)) {
            true
        } else {
            System.currentTimeMillis() - this.lastArmAnimations[uuid]!! < 500
        }

    private fun wasLastHitSoundSent(uuid: UUID): Boolean =
        if (!this.lastHitSounds.containsKey(uuid)) {
            true
        } else {
            System.currentTimeMillis() - this.lastHitSounds[uuid]!! < 500
        }
}