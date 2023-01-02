package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackDataInfoPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.resourcepack.ResourcePack;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ResourcePackClientResponseHandler implements PacketHandler<ResourcePackClientResponsePacket> {

    @Override
    public void handle( ResourcePackClientResponsePacket packet, Server server, Player player ) {
        switch ( packet.getStatus() ) {
            case REFUSED -> {
                player.getPlayerConnection().disconnect( "You have been disconnected." );
            }
            case SEND_PACKS -> {
                for ( String packIds : packet.getPackIds()) {
                    String[] resourcePackEntryElements = packIds.split( "_" );
                    String resourcePackEntryUuid = resourcePackEntryElements[0];

                    ResourcePack resourcePack = server.getResourcePackManager().retrieveResourcePackById(resourcePackEntryUuid);
                    if (resourcePack != null) {
                        int maxChunkSize = 1048576;

                        ResourcePackDataInfoPacket resourcePackDataInfoPacket = new ResourcePackDataInfoPacket();
                        resourcePackDataInfoPacket.setPackId(resourcePack.getUuid());
                        resourcePackDataInfoPacket.setMaxChunkSize(maxChunkSize);
                        resourcePackDataInfoPacket.setChunkCount((int) (resourcePack.getSize() / maxChunkSize));
                        resourcePackDataInfoPacket.setCompressedPackSize(resourcePack.getSize());
                        resourcePackDataInfoPacket.setHash( resourcePack.getSha256());
                        player.getPlayerConnection().sendPacket(resourcePackDataInfoPacket);
                    }
                }
            }
            case HAVE_ALL_PACKS -> {
                ResourcePackStackPacket resourcePackStackPacket = new ResourcePackStackPacket();
                resourcePackStackPacket.setGameVersion( "*" );
                resourcePackStackPacket.setExperimentsPreviouslyToggled( false );
                resourcePackStackPacket.setForcedToAccept( server.isForceResourcePacks() );
                player.getPlayerConnection().sendPacket( resourcePackStackPacket );
            }
            case COMPLETED -> player.getPlayerConnection().initializePlayer();
        }
    }
}
