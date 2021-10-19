package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.ResourcePackChunkDataPacket;
import org.jukeboxmc.network.packet.ResourcePackChunkRequestPacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.resourcepack.ResourcePack;

import java.util.UUID;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ResourcePackChunkRequestHandler implements PacketHandler<ResourcePackChunkRequestPacket> {

    @Override
    public void handle( ResourcePackChunkRequestPacket packet, Server server, Player player ) {
        ResourcePack resourcePack = player.getServer().getResourcePackManager().retrieveResourcePackById( packet.getUuid().toString() );
        if ( resourcePack != null ) {
            ResourcePackChunkDataPacket resourcePackChunkDataPacket = new ResourcePackChunkDataPacket();
            resourcePackChunkDataPacket.setUuid( UUID.fromString( resourcePack.getUuid() ) );
            resourcePackChunkDataPacket.setChunkIndex( packet.getChunkIndex() );
            resourcePackChunkDataPacket.setProgress( 1048576L * packet.getChunkIndex() );
            resourcePackChunkDataPacket.setData( resourcePack.getChunk( 1048576 * packet.getChunkIndex(), 1048576 ) );
            player.sendPacket( resourcePackChunkDataPacket );
        }
    }
}
