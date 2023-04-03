package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.packet.LevelSoundEventPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LevelSoundEventHandler implements PacketHandler<LevelSoundEventPacket>{

    @Override
    public void handle( LevelSoundEventPacket packet, Server server, Player player ) {
        switch ( packet.getSound() ) {
            case LAND, ATTACK_NODAMAGE, FALL, HIT, ATTACK_STRONG ->
                    player.getWorld().sendChunkPacket( player.getChunkX(), player.getChunkZ(), packet );
        }
    }
}
