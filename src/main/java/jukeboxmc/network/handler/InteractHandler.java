package jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.InteractPacket;
import org.jukeboxmc.network.packet.type.InteractAction;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InteractHandler implements PacketHandler<InteractPacket> {

    @Override
    public void handle( InteractPacket packet, Server server, Player player ) {
        if ( packet.getAction() == InteractAction.OPEN_INVENTORY ) {
            player.openInventory( player.getInventory() );
        }
    }
}
