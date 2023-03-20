package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkDataPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackChunkRequestPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.resourcepack.ResourcePack;

/**
 * @author pooooooon
 * @version 1.0
 */
public class ResourcePackChunkRequestHandler implements PacketHandler<ResourcePackChunkRequestPacket> {

    @Override
    public void handle(@NotNull ResourcePackChunkRequestPacket packet, @NotNull Server server, @NotNull Player player ) {
        String[] resourcePackEntryElements = packet.getPackId().toString().split( "_" );
        String resourcePackEntryUuid = resourcePackEntryElements[0];
        ResourcePack resourcePack = server.getResourcePackManager().retrieveResourcePackById( resourcePackEntryUuid );
        if ( resourcePack != null ) {
            ResourcePackChunkDataPacket resourcePackChunkDataPacket = new ResourcePackChunkDataPacket();
            resourcePackChunkDataPacket.setPackId( packet.getPackId() );
            resourcePackChunkDataPacket.setPackVersion( packet.getPackVersion() );
            resourcePackChunkDataPacket.setChunkIndex( packet.getChunkIndex() );
            resourcePackChunkDataPacket.setData( resourcePack.getChunk( 1048576 * packet.getChunkIndex(), 1048576) );
            resourcePackChunkDataPacket.setProgress( 1048576L * packet.getChunkIndex() );
            player.getPlayerConnection().sendPacketImmediately( resourcePackChunkDataPacket ); 
        }
    }
}
