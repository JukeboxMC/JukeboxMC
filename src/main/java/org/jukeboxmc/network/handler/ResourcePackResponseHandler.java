package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.network.packet.type.ResourcePackEntry;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.resourcepack.ResourcePack;
import org.jukeboxmc.world.Difficulty;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ResourcePackResponseHandler implements PacketHandler<ResourcePackResponsePacket>{

    @Override
    public void handle( ResourcePackResponsePacket packet, Server server, Player player ) {
        switch ( packet.getResponseStatus() ) {
            case REFUSED:
                player.disconnect( packet.getResponseStatus().name() );
                break;
            case SEND_PACKS:
                for ( ResourcePackEntry resourcePackEntry : packet.getResourcePackEntries()) {
                    ResourcePack resourcePack = player.getServer().getResourcePackManager().retrieveResourcePackById(resourcePackEntry.getUuid());

                    if (resourcePack != null) {
                        int maxChunkSize = 1048576;

                        ResourcePackDataInfoPacket resourcePackDataInfoPacket = new ResourcePackDataInfoPacket();
                        resourcePackDataInfoPacket.setResourcePackUuid(resourcePack.getUuid());
                        resourcePackDataInfoPacket.setMaxChunkSize(maxChunkSize);
                        resourcePackDataInfoPacket.setChunkCount((int) (resourcePack.getSize() / maxChunkSize));
                        resourcePackDataInfoPacket.setCompressedResourcePackSize(resourcePack.getSize());
                        resourcePackDataInfoPacket.setResourcePackSha256(resourcePack.getSha256());

                        player.getPlayerConnection().sendPacket(resourcePackDataInfoPacket);
                    }
                }
                break;
            case HAVE_ALL_PACKS:
                ResourcePackStackPacket resourcePackStackPacket = new ResourcePackStackPacket();
                resourcePackStackPacket.setMustAccept( Server.getInstance().isForceResourcePacks() );
                player.getPlayerConnection().sendPacket( resourcePackStackPacket );
                break;
            case COMPLETED:
                Vector worldSpawn = new Vector( 0, 73, 0 );

                StartGamePacket startGamePacket = new StartGamePacket();
                startGamePacket.setEntityId( player.getEntityId() );
                startGamePacket.setEntityRuntimeId( player.getEntityId() );
                startGamePacket.setGameMode( player.getGameMode() );
                startGamePacket.setPosition( worldSpawn );
                startGamePacket.setWorldSpawn( worldSpawn );
                startGamePacket.setYaw( player.getYaw() );
                startGamePacket.setPitch( player.getPitch() );
                startGamePacket.setWorldName( "world" );
                startGamePacket.setServerEngine( "JukeboxMC" );
                startGamePacket.setDifficulty( Difficulty.NORMAL ); //TODO
                startGamePacket.setGamerules( player.getWorld().getGamerules() );
                player.getPlayerConnection().sendPacket( startGamePacket );

                player.getPlayerConnection().sendPacket( new AvailableActorIdentifiersPacket() );
                player.getPlayerConnection().sendPacket( new BiomeDefinitionListPacket() );
                player.getPlayerConnection().sendPacket( new CreativeContentPacket() );
                break;
        }
    }
}
