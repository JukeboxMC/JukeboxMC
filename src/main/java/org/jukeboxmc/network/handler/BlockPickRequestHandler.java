package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemAir;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.BlockPickRequestPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPickRequestHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        BlockPickRequestPacket blockPickRequestPacket = (BlockPickRequestPacket) packet;
        Vector position = blockPickRequestPacket.getPosition();
        position.setDimension( player.getDimension() );
        Block pickedBlock = player.getWorld().getBlock( position );

        if ( player.getGameMode() == GameMode.CREATIVE ) {
            Item item = pickedBlock.toItem();
            if ( item instanceof ItemAir ) {
                Server.getInstance().getLogger().warn( "User try to pick air" );
                return;
            }
            player.getInventory().addItem( item );
        }
    }
}
