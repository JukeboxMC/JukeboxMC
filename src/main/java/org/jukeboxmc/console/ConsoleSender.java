package org.jukeboxmc.console;

import org.jukeboxmc.Server;
import org.jukeboxmc.command.CommandSender;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ConsoleSender implements CommandSender {

    private final Server server;

    public ConsoleSender( Server server ) {
        this.server = server;
    }

    @Override
    public void sendMessage( String message ) {
        this.server.getLogger().info( message );
    }

    @Override
    public boolean hasPermission( String permission ) {
        return true;
    }

    @Override
    public Server getServer() {
        return this.server;
    }
}
