package org.jukeboxmc.player;


import org.cloudburstmc.protocol.bedrock.data.GameType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum GameMode {

    SURVIVAL( "Survival", GameType.SURVIVAL, 0 ),
    CREATIVE( "Creative", GameType.CREATIVE, 1 ),
    ADVENTURE( "Adventure", GameType.ADVENTURE, 2 ),
    SPECTATOR( "Spectator", GameType.SPECTATOR, 6 );

    private final String identifier;
    private final GameType gameType;
    private final int id;

    GameMode( String identifier, GameType gameType, int id ) {
        this.identifier = identifier;
        this.gameType = gameType;
        this.id = id;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public GameType toGameType() {
        return this.gameType;
    }

    public int getId() {
        return this.id;
    }
}
