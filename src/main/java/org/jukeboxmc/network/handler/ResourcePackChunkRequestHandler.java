package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.resourcepack.ResourcePack;

import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkDataPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkRequestPacket;

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
            resourcePackChunkDataPacket.setData( resourcePack.getChunk( ( int ) 1048576 * packet.getChunkIndex(), (int) 1048576) );
            resourcePackChunkDataPacket.setProgress( 1048576 * packet.getChunkIndex() );
            player.getPlayerConnection().sendPacketImmediately( resourcePackChunkDataPacket ); 
        }
    }
}
