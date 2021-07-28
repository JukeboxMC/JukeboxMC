package org.jukeboxmc.network.handler;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.CommandOutput;
import org.jukeboxmc.network.packet.CommandRequestPacket;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.player.Player;

/**
 * @author Kaooot
 * @version 1.0
 */
public class CommandRequestHandler implements PacketHandler {

    @Override
    public void handle( Packet packet, Player player ) {
        CommandRequestPacket commandRequestPacket = (CommandRequestPacket) packet;

        CommandOutput commandOutput = Server.getInstance().getPluginManager().getCommandManager()
                .handleCommandInput( player, commandRequestPacket.getInputCommand() );

        player.sendMessage( ( !commandOutput.isSuccess() ? "§c" : "" ) + commandOutput.getMessage() );
    }
}