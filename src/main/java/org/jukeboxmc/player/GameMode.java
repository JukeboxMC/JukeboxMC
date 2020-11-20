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
    private String gamemode;

    GameMode( String gamemode ) {
        this.gamemode = gamemode;
    }
}
