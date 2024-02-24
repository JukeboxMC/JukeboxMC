package org.jukeboxmc.api.command

import org.jukeboxmc.api.Server

/**
 * Represents a command sender that is able to execute server side commands
 */
interface CommandSender {

    /**
     * Sends a message to this command sender
     *
     * @param message the message that should be sent to the command sender
     */
    fun sendMessage(message: String)

    /**
     * Checks whether the command sender has the provided permission
     *
     * @param permission that is required in order to perform a specific action
     *
     * @return if the command sender holds the specified permission in his set of permissions
     */
    fun hasPermission(permission: String): Boolean

    /**
     * Retrieves the server instance of this command sender
     *
     * @return the default server instance
     */
    fun getServer(): Server

}