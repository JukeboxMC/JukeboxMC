package org.jukeboxmc.event.server;

import org.jukeboxmc.Server;
import org.jukeboxmc.event.Event;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ServerEvent extends Event {

    private final Server server;

    public ServerEvent( Server server ) {
        this.server = server;
    }

    public Server getServer() {
        return this.server;
    }
}
