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

    /**
     * Proofs whether the client owns the specified permission and sets a default value
     *
     * @param permission   which should be checked for
     * @param defaultValue which will be used when the client does not own the given permission
     *
     * @return whether the client possesses the permission
     */
    boolean hasPermission( String permission, boolean defaultValue );

    Server getServer();
}