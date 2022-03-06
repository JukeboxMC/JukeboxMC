package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.network.packet.type.ResourcePackEntry;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.resourcepack.ResourcePack;

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
                        player.sendPacket(resourcePackDataInfoPacket);
                    }
                }
                break;
            case HAVE_ALL_PACKS:
                ResourcePackStackPacket resourcePackStackPacket = new ResourcePackStackPacket();
                resourcePackStackPacket.setMustAccept( Server.getInstance().isForceResourcePacks() );
                player.sendPacket( resourcePackStackPacket );
                break;
            case COMPLETED:
                Location worldSpawn = player.getWorld().getSpawnLocation( player.getDimension() );
                worldSpawn.add( 0, player.getEyeHeight() , 0 );
                StartGamePacket startGamePacket = new StartGamePacket();
                startGamePacket.setEntityId( player.getEntityId() );
                startGamePacket.setEntityRuntimeId( player.getEntityId() );
                startGamePacket.setGameMode( player.getGameMode() );
                startGamePacket.setPosition( player.getSpawnLocation() != null ? player.getSpawnLocation().add( 0, player.getEyeHeight(), 0 ) :  worldSpawn );
                startGamePacket.setWorldSpawn( worldSpawn );
                startGamePacket.setYaw( player.getYaw() );
                startGamePacket.setPitch( player.getPitch() );
                startGamePacket.setWorldTime( player.getWorld().getWorldTime() );
                startGamePacket.setWorldName( "world" );
                startGamePacket.setServerEngine( "JukeboxMC" );
                startGamePacket.setDifficulty( player.getWorld().getDifficulty() );
                startGamePacket.setGamerules( player.getWorld().getGamerules() );
                player.sendPacket( startGamePacket );

                player.sendPacket( new AvailableActorIdentifiersPacket() );
                player.sendPacket( new BiomeDefinitionListPacket() );
                player.sendPacket( new CreativeContentPacket() );

                CraftingDataPacket craftingDataPacket = new CraftingDataPacket();
                craftingDataPacket.setCraftingData( server.getCraftingManager().getCraftingData() );
                player.sendPacket( craftingDataPacket );
                break;
        }
    }
}
