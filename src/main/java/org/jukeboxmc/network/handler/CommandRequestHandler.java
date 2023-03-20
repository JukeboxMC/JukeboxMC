package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.event.player.PlayerCommandPreprocessEvent;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class CommandRequestHandler implements PacketHandler<CommandRequestPacket>{

    @Override
    public void handle(@NotNull CommandRequestPacket packet, @NotNull Server server, @NotNull Player player ) {
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
