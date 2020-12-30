package org.jukeboxmc.network.handler;

import org.jukeboxmc.item.Item;
import org.jukeboxmc.math.Location;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.packet.PlayerMovePacket;
import org.jukeboxmc.player.Player;
import org.jukeboxmc.world.chunk.Chunk;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class PlayerMoveHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        PlayerMovePacket playerMovePacket = (PlayerMovePacket) packet;
        Chunk fromChunk = player.getChunk();

        Location toLocation = new Location( player.getLocation().getWorld(), playerMovePacket.getX(), playerMovePacket.getY() - player.getEyeHeight(), playerMovePacket.getZ(), playerMovePacket.getYaw(), playerMovePacket.getPitch() );
        player.setHeadYaw( playerMovePacket.getHeadYaw() );
        player.setLocation( toLocation );
        player.setOnGround( playerMovePacket.isOnGround() );
        System.out.println( toLocation.toString() );

        Chunk toChunk = player.getChunk();

        if ( fromChunk.getChunkX() != toChunk.getChunkX() || fromChunk.getChunkZ() != toChunk.getChunkZ() ) {
            fromChunk.removePlayer( player );
            toChunk.addPlayer( player );
        }

        for ( Player onlinePlayer : player.getServer().getOnlinePlayers() ) {
            if ( onlinePlayer != player ) {
                onlinePlayer.getPlayerConnection().movePlayer( player, PlayerMovePacket.Mode.NORMAL );
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( " §bChunkX§7: §f" ).append( player.getChunkX() ).append( " §bChunkZ§7: §f" ).append( player.getChunkZ() );

        if(player.getInventory().getItemInHand() != null) {
            Item itemInHand = player.getInventory().getItemInHand();
            stringBuilder.append( "\n" ).append( "§7Item§8: §e" ).append( itemInHand.getClass().getSimpleName() ).append( " §7Meta§8: " ).append( itemInHand.getMeta() ).append( " §7Slot§8: §e" ).append( itemInHand.getSlot() );
        }

        player.sendTip( stringBuilder.toString() );
    }
}
