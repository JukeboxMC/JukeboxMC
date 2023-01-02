package org.jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Dimension {

    OVERWORLD( "Overworld" ),
    NETHER( "Nether" ),
    THE_END( "The End" );

    private final String name;

    Dimension( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
