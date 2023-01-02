package org.jukeboxmc.command;

import org.jukeboxmc.Server;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public interface CommandSender {

    void sendMessage( String message );

    boolean hasPermission( String permission );

    Server getServer();
}
