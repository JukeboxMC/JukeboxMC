package org.jukeboxmc.network.handler;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.*;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.GameRule;
import org.jukeboxmc.world.GameRules;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ResourcePackResponseHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        ResourcePackResponsePacket resourcePackResponsePacket = (ResourcePackResponsePacket) packet;
        ResourcePackResponsePacket.Status status = resourcePackResponsePacket.getResponseStatus();

        if ( status == ResourcePackResponsePacket.Status.STATUS_REFUSED ) {
            player.getPlayerConnection().disconnect( "STATUS_REFUSED" );
        } else if ( status == ResourcePackResponsePacket.Status.STATUS_HAVE_ALL_PACKS ) {
            player.getPlayerConnection().sendResourcePackStack();
        } else if ( status == ResourcePackResponsePacket.Status.STATUS_COMPLETED ) {
            StartGamePacket startGamePacket = new StartGamePacket();
            startGamePacket.setEntityId( player.getEntityId() );
            startGamePacket.setEntityRuntimeId( player.getEntityId() );
            startGamePacket.setGameMode( player.getGameMode() );
            startGamePacket.setPosition( player.getLocation() );
            startGamePacket.setWorldName( player.getLocation().getWorld().getName() );
            startGamePacket.setWorldSpawn( new Vector( 0, 7, 0 ) );
            for ( GameRules<?> gameRules : GameRule.getAll() ) {
                startGamePacket.getGamerules().put( gameRules.getName(), gameRules );
            }
            player.getPlayerConnection().sendPacket( startGamePacket );


            player.getPlayerConnection().sendPacket( new AvailableActorIdentifiersPacket() );
            player.getPlayerConnection().sendPacket( new BiomeDefinitionListPacket() );

            player.getPlayerConnection().sendPacket( new CreativeContentPacket() );

        }

    }
}
