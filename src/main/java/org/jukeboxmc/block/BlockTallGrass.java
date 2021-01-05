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
        return this.setState( "tall_grass_type", grassType.name().toLowerCase() );
    }

    public GrassType getGrassType() {
        return this.stateExists( "tall_grass_type" ) ? GrassType.valueOf(  this.getStringState( "tall_grass_type" ).toUpperCase() ) : GrassType.DEFAULT;
    }

    public enum GrassType {
        DEFAULT,
        FERN,
        SNOW,
        TALL
    }
}
