package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.InteractPacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class InteractHandler implements PacketHandler<InteractPacket> {
    @Override
    public void handle(@NotNull InteractPacket packet, Server server, @NotNull Player player ) {
        if ( packet.getAction().equals( InteractPacket.Action.OPEN_INVENTORY ) ) {
            player.openInventory( player.getInventory() );
        }
    }
}
