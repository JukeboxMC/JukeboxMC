package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.block.Block;
import org.jukeboxmc.blockentity.BlockEntitySign;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityDataHandler implements PacketHandler<BlockEntityDataPacket> {

    @Override
    public void handle( BlockEntityDataPacket packet, Server server, Player player ) {
        Vector vector = new Vector( packet.getBlockPosition() );
        vector.setDimension( player.getDimension() );
        Block block = player.getWorld().getBlock( vector );
        if ( block != null && block.getBlockEntity() != null ) {
            if ( block.getBlockEntity() instanceof BlockEntitySign blockEntitySign ) {
                blockEntitySign.updateBlockEntitySign( packet.getData(), player );
            }
        }
    }
}
