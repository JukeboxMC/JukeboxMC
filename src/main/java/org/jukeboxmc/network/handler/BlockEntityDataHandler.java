package org.jukeboxmc.network.handler;

import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntitySign;
import org.jukeboxmc.math.BlockPosition;
import org.jukeboxmc.network.packet.BlockEntityDataPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityDataHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        BlockEntityDataPacket entityDataPacket = (BlockEntityDataPacket) packet;
        BlockPosition blockPosition = entityDataPacket.getBlockPosition();
        BlockEntity blockEntity = player.getWorld().getBlockEntity( blockPosition );

        if ( blockEntity instanceof BlockEntitySign ) {
            BlockEntitySign blockEntitySign = (BlockEntitySign) blockEntity;
            System.out.println(entityDataPacket.getNbt().toString());
            blockEntitySign.updateBlockEntitySign( entityDataPacket.getNbt(), player );
        }
    }
}
