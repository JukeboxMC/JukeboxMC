package org.jukeboxmc.world.generator.biome;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public record BiomeHeight(double height, double scale) {

    public static final BiomeHeight DEFAULT = new BiomeHeight( 0.1, 0.3 );
    public static final BiomeHeight FLAT_SHORE = new BiomeHeight( 0, 0.025d );
    public static final BiomeHeight HIGH_PLATEAU = new BiomeHeight( 1.5d, 0.025d );
    public static final BiomeHeight FLATLANDS = new BiomeHeight( 0.125d, 0.05d );
    public static final BiomeHeight SWAMPLAND = new BiomeHeight( -0.2d, 0.1d );
    public static final BiomeHeight MID_PLAINS = new BiomeHeight( 0.2d, 0.2d );
    public static final BiomeHeight FLATLANDS_HILLS = new BiomeHeight( 0.275d, 0.25d );
    public static final BiomeHeight SWAMPLAND_HILLS = new BiomeHeight( -0.1d, 0.3d );
    public static final BiomeHeight LOW_HILLS = new BiomeHeight( 0.2d, 0.3d );
    public static final BiomeHeight HILLS = new BiomeHeight( 0.45d, 0.3d );
    public static final BiomeHeight MID_HILLS2 = new BiomeHeight( 0.1d, 0.4d );
    public static final BiomeHeight DEFAULT_HILLS = new BiomeHeight( 0.2d, 0.4d );
    public static final BiomeHeight MID_HILLS = new BiomeHeight( 0.3d, 0.4d );
    public static final BiomeHeight BIG_HILLS = new BiomeHeight( 0.525d, 0.55d );
    public static final BiomeHeight BIG_HILLS2 = new BiomeHeight( 0.55d, 0.5d );
    public static final BiomeHeight EXTREME_HILLS = new BiomeHeight( 1d, 0.5d );
    public static final BiomeHeight ROCKY_SHORE = new BiomeHeight( 0.1d, 0.8d );
    public static final BiomeHeight LOW_SPIKES = new BiomeHeight( 0.4125d, 1.325d );
    public static final BiomeHeight HIGH_SPIKES = new BiomeHeight( 1.1d, 1.3125d );
    public static final BiomeHeight RIVER = new BiomeHeight( -0.5d, 0d );
    public static final BiomeHeight OCEAN = new BiomeHeight( -1d, 0.1d );
    public static final BiomeHeight DEEP_OCEAN = new BiomeHeight( -1.8d, 0.1d );

}
