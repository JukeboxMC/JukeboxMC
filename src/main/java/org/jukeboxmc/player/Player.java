package org.jukeboxmc.player;

import lombok.Getter;
import org.jukeboxmc.network.raknet.Connection;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Player {

    @Getter
    private InetSocketAddress address;
    @Getter
    private PlayerConnection playerConnection;

    public Player( Connection connection ) {
        this.address = connection.getSender();
        this.playerConnection = new PlayerConnection( connection );
    }

    public String getHostName() {
        return this.address.getHostName();
    }

    public int getPort() {
        return this.address.getPort();
    }

}
