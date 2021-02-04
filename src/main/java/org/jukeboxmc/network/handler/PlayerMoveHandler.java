package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerMoveEvent;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayerMovePacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.Biome;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerMoveHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        PlayerMovePacket playerMovePacket = (PlayerMovePacket) packet;

        Location fromLocation = player.getLocation();
        Chunk fromChunk = player.getChunk();
        Location toLocation = new Location( player.getLocation().getWorld(), playerMovePacket.getX(), playerMovePacket.getY() - player.getEyeHeight(), playerMovePacket.getZ(), playerMovePacket.getYaw(), playerMovePacket.getPitch() );

        PlayerMoveEvent playerMoveEvent = new PlayerMoveEvent( player, fromLocation, toLocation );
        Server.getInstance().getPluginManager().callEvent( playerMoveEvent );

        if ( playerMoveEvent.isCancelled() ) {
            playerMoveEvent.setTo( fromLocation );
            player.teleport( fromLocation );
        }

        player.setHeadYaw( playerMovePacket.getHeadYaw() );
        player.setLocation( playerMoveEvent.getTo() );
        player.setOnGround( playerMovePacket.isOnGround() );

        Chunk toChunk = player.getChunk();

        if ( fromChunk.getChunkX() != toChunk.getChunkX() || fromChunk.getChunkZ() != toChunk.getChunkZ() ) {
            fromChunk.removeEntity( player );
            toChunk.addEntity( player );
        }

        for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
            if ( onlinePlayer != player ) {
                onlinePlayer.getPlayerConnection().movePlayer( player, PlayerMovePacket.Mode.NORMAL );
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( " §bChunkX§7: §f" ).append( player.getChunkX() ).append( " §bChunkZ§7: §f" ).append( player.getChunkZ() );

        if ( player.getInventory().getItemInHand() != null ) {
            Item itemInHand = player.getInventory().getItemInHand();
            stringBuilder.append( "\n" ).append( "§7Item§8: §e" ).append( itemInHand.getClass().getSimpleName() ).append( " §7Meta§8: §e" ).append( itemInHand.getMeta() );
            stringBuilder.append( "\n" ).append( "§7BlockFace§8: §e" ).append( player.getDirection().name() );
            stringBuilder.append( "\n" ).append( "§7Block§8: §e" ).append( player.getWorld().getBlock( player.getLocation().subtract( 0, 1, 0 ) ).getName() );
            Biome biome = player.getWorld().getBiome( player.getLocation() );
            stringBuilder.append( "\n" ).append( "§7Biome§8: §e" ).append( biome != null ? biome.getName() : "Plains (Null)" );
            stringBuilder.append( "\n" ).append( "§7World§8: §e" ).append( player.getWorld().getName() );
        }
        player.sendTip( stringBuilder.toString() );
    }
}
