package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.LevelSoundEventPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class LevelSoundEventHandler implements PacketHandler<LevelSoundEventPacket> {

    @Override
    public void handle( LevelSoundEventPacket packet, Server server, Player player ) {
        switch ( packet.getLevelSound() ) {
            case LAND:
            case ATTACK_NODAMAGE:
            case FALL:
            case HIT:
            case ATTACK_STRONG:
                player.getWorld().sendChunkPacket( player.getChunkX(), player.getChunkZ(), packet );
                break;
            default:
                break;
        }
    }
}
