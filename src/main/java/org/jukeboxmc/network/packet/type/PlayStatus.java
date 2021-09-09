package org.jukeboxmc.network.packet.type;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum PlayStatus {

    LOGIN_SUCCESS,
    LOGIN_FAILED_CLIENT_OUTDATED,
    LOGIN_FAILED_SERVER_OUTDATED,
    PLAYER_SPAWN,
    LOGIN_FAILED_INVALID_TENANT,
    LOGIN_FAILED_VANILLA_EDU,
    LOGIN_FAILED_EDU_VANILLA,
    LOGIN_FAILED_SERVER_FULL

}
