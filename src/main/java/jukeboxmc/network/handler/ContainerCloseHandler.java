package jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.ContainerClosePacket;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ContainerCloseHandler implements PacketHandler<ContainerClosePacket> {

    @Override
    public void handle( ContainerClosePacket packet, Server server, Player player ) {
        player.closeInventory( packet.getWindowId(), packet.isServerInitiated() );
    }
}
