package org.jukeboxmc.network.handler;

import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackChunkDataPacket;
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackChunkRequestPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.resourcepack.ResourcePack;

/**
 * @author pooooooon
 * @version 1.0
 */
public class ResourcePackChunkRequestHandler implements PacketHandler<ResourcePackChunkRequestPacket> {

    @Override
    public void handle( ResourcePackChunkRequestPacket packet, Server server, Player player ) {
        String[] resourcePackEntryElements = packet.getPackId().toString().split( "_" );
        String resourcePackEntryUuid = resourcePackEntryElements[0];
        ResourcePack resourcePack = server.getResourcePackManager().retrieveResourcePackById( resourcePackEntryUuid );
        if ( resourcePack != null ) {
            ResourcePackChunkDataPacket resourcePackChunkDataPacket = new ResourcePackChunkDataPacket();
            resourcePackChunkDataPacket.setPackId( packet.getPackId() );
            resourcePackChunkDataPacket.setPackVersion( packet.getPackVersion() );
            resourcePackChunkDataPacket.setChunkIndex( packet.getChunkIndex() );
            resourcePackChunkDataPacket.setData( Unpooled.wrappedBuffer( resourcePack.getChunk( 1048576 * packet.getChunkIndex(), 1048576 ) ) ); //Maybe wrong
            resourcePackChunkDataPacket.setProgress( 1048576L * packet.getChunkIndex() );
            player.getPlayerConnection().sendPacketImmediately( resourcePackChunkDataPacket );
        }
    }
}
