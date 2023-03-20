package org.jukeboxmc.network.handler;

import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import org.jetbrains.annotations.NotNull;
import org.jukeboxmc.Server;
import org.jukeboxmc.player.Player;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ModalFormResponseHandler implements PacketHandler<ModalFormResponsePacket> {

    @Override
    public void handle(@NotNull ModalFormResponsePacket packet, Server server, @NotNull Player player ) {
        player.parseGUIResponse( packet.getFormId(), packet.getFormData() );
    }
}
