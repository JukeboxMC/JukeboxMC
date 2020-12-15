package org.jukeboxmc.network.raknet.event.intern;

import lombok.Getter;
import org.jukeboxmc.network.raknet.Connection;
import org.jukeboxmc.network.raknet.event.RakNetEvent;

/**
 * @author LucGamesYT
 * @version 1.0
 */

public class PlayerCloseConnectionEvent extends RakNetEvent {

    @Getter
    private Connection connection;

    public PlayerCloseConnectionEvent( Connection connection ) {
        this.connection = connection;
    }
}
