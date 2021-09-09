package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.network.packet.CommandRequestPacket;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class CommandRequestHandler implements PacketHandler<CommandRequestPacket> {

    @Override
    public void handle( CommandRequestPacket packet, Server server, Player player ) {
        try {
            server.getPluginManager().getCommandManager().handleCommandInput( player, packet.getInputCommand() );
        } catch ( Throwable e ) {
            e.printStackTrace();
        }
    }
}