package org.jukeboxmc.network.handler;

import org.cloudburstmc.protocol.bedrock.packet.CommandRequestPacket;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerCommandPreprocessEvent;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CommandRequestHandler implements PacketHandler<CommandRequestPacket>{

    @Override
    public void handle( CommandRequestPacket packet, Server server, Player player ) {
        final String[] commandParts = packet.getCommand().substring( 1 ).split( " " );
        final String commandIdentifier = commandParts[0];
        PlayerCommandPreprocessEvent playerCommandPreprocessEvent = new PlayerCommandPreprocessEvent( player, commandIdentifier );
        server.getPluginManager().callEvent( playerCommandPreprocessEvent );

        if ( playerCommandPreprocessEvent.isCancelled() ) {
            return;
        }
        server.getPluginManager().getCommandManager().handleCommandInput( player, packet.getCommand().toLowerCase() );
    }
}
