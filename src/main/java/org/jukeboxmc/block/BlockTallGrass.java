package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockTallGrass extends Block {

    public BlockTallGrass() {
        super( "minecraft:tallgrass" );
    }

    public BlockTallGrass setGrassType( GrassType grassType ) {
        return this.setStates( "tall_grass_type", grassType.name().toLowerCase() );
    }

    public GrassType getGrassType() {
        return this.states.containsKey( "tall_grass_type" ) ? GrassType.valueOf(  this.states.getString( "tall_grass_type" ).toUpperCase() ) : GrassType.DEFAULT;
    }

    public enum GrassType {
        DEFAULT,
        FERN,
        SNOW,
        TALL
    }
}
