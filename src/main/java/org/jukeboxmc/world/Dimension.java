package org.jukeboxmc.world;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public enum Dimension {

    OVERWORLD( "Overworld", -64, 319 ),
    NETHER( "Nether", 0, 127 ),
    THE_END( "The End", 0, 255 );

    private final String name;
    private final int minY;
    private final int maxY;

    Dimension( String name, int minY, int maxY ) {
        this.name = name;
        this.minY = minY;
        this.maxY = maxY;
    }

    public String getName() {
        return this.name;
    }

    public int getMinY() {
        return this.minY;
    }

    public int getMaxY() {
        return this.maxY;
    }
}
