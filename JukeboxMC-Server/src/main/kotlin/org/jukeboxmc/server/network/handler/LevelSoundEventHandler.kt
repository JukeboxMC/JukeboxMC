package org.jukeboxmc.server.network.handler

import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.anticheat.module.combat.AntiCheatCombatModule
import org.jukeboxmc.server.player.JukeboxPlayer

class LevelSoundEventHandler : PacketHandler<LevelSoundEventPacket> {

    override fun handle(packet: LevelSoundEventPacket, server: JukeboxServer, player: JukeboxPlayer) {
        player.getWorld().sendChunkPacket(player.getChunkX(), player.getChunkZ(), packet)

        if (packet.sound == SoundEvent.ATTACK || packet.sound == SoundEvent.ATTACK_NODAMAGE || packet.sound == SoundEvent.ATTACK_STRONG) {
            server.getAntiCheatRegistry().getModule(AntiCheatCombatModule::class.java).registerHitSoundTimestamp(player.getUUID())
        }
    }
}