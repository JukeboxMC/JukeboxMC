package org.jukeboxmc.command;

import org.jukeboxmc.Server;

/**
 * @author Kaooot
 * @version 1.0
 */
public interface CommandSender {

    /**
     * Sends a message to the client
     *
     * @param message which should be send tot he client
     */
    void sendMessage( String message );

    /**
     * Proofs whether the client owns the specified permission
     *
     * @param permission which should be checked for
     *
     * @return whether the client owns the permission
     */
    boolean hasPermission( String permission );

    Server getServer();
}