package org.jukeboxmc.network.handler;

import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ResourcePackResponseHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        ResourcePackResponsePacket resourcePackResponsePacket = (ResourcePackResponsePacket) packet;
        ResourcePackResponsePacket.Status status = resourcePackResponsePacket.getResponseStatus();

        System.out.println( status.name() );

        if ( status == ResourcePackResponsePacket.Status.STATUS_REFUSED ) {
            //Disconnect
        } else if ( status == ResourcePackResponsePacket.Status.STATUS_SEND_PACKS ) {

        } else if ( status == ResourcePackResponsePacket.Status.STATUS_HAVE_ALL_PACKS ) {
           player.getPlayerConnection().sendResourcePackStack();
        } else if ( status == ResourcePackResponsePacket.Status.STATUS_COMPLETED ) {
            StartGamePacket startGamePacket = new StartGamePacket();
            startGamePacket.setEntityId( 1 );
            startGamePacket.setEntityRuntimeId( 1 );
            startGamePacket.setGameMode( GameMode.SURVIVAL );
            startGamePacket.setPosition( player.getLocation() );
            startGamePacket.setWorldName( player.getLocation().getWorld().getName() );
            startGamePacket.setWorldSpawn( new Vector( 0, 0,0 ) );
            player.getPlayerConnection().sendPacket( startGamePacket );

            BiomeDefinitionListPacket biomeDefinitionListPacket = new BiomeDefinitionListPacket();
            player.getPlayerConnection().sendPacket( biomeDefinitionListPacket );

            CreativeContentPacket creativeContentPacket = new CreativeContentPacket();
            player.getPlayerConnection().sendPacket( creativeContentPacket );

            //Do other stuff
        }

    }
}
