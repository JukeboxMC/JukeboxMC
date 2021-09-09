package jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.blockentity.BlockEntity;
import org.jukeboxmc.blockentity.BlockEntitySign;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.packet.BlockEntityDataPacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockEntityDataHandler implements PacketHandler<BlockEntityDataPacket> {

    @Override
    public void handle( BlockEntityDataPacket packet, Server server, Player player ) {
        Vector blockPosition = packet.getBlockPosition();
        BlockEntity blockEntity = player.getWorld().getBlockEntity( blockPosition, player.getDimension() );

        if ( blockEntity instanceof BlockEntitySign ) {
            BlockEntitySign blockEntitySign = (BlockEntitySign) blockEntity;
            blockEntitySign.updateBlockEntitySign( packet.getNbt(), player );
        }
    }
}
