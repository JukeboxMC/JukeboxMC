package org.jukeboxmc.network.raknet.event.intern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jukeboxmc.network.packet.Packet;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.event.RakNetEvent;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@AllArgsConstructor
public class ReceiveMinecraftPacketEvent extends RakNetEvent {

    @Getter
    private Connection connection;
    @Getter
    private Packet packet;

}
