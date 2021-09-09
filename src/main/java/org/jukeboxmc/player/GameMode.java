package org.jukeboxmc.player;

import lombok.Getter;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum GameMode {

    SURVIVAL( "Survival" ),
    CREATIVE( "Creative" ),
    ADVENTURE( "Adventure" ),
    SPECTATOR( "Spectator" );

    @Getter
    private final String identifier;

    GameMode( String gamemode ) {
        this.identifier = gamemode;
    }
}
