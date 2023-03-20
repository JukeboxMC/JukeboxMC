package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.BlockPickRequestPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.GameMode;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPickRequestHandler implements PacketHandler<BlockPickRequestPacket> {

    @Override
    public void handle(@NotNull BlockPickRequestPacket packet, Server server, @NotNull Player player ) {
        Vector position = new Vector( packet.getBlockPosition() );
        position.setDimension( player.getDimension() );
        Block pickedBlock = player.getWorld().getBlock( position );

        if ( player.getGameMode() == GameMode.CREATIVE ) {
            Item item = pickedBlock.toItem();
            if ( item.getType().equals( ItemType.AIR ) ) {
                Server.getInstance().getLogger().warn( "User try to pick air" );
                return;
            }
            if ( !player.getInventory().contains( item ) ) {
                player.getInventory().addItem( item );
            }

        }
    }
}
