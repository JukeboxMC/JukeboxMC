package org.jukeboxmc.player;

import lombok.Getter;

import java.net.InetSocketAddress;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class Player {

    @Getter
    private InetSocketAddress address;

    public Player( InetSocketAddress address ) {
        this.address = address;
    }

}
