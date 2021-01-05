package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDirt extends Block {

    public BlockDirt() {
        super( "minecraft:dirt" );
    }

    public BlockDirt setDirtType( DirtType dirtType ) {
        return this.setState( "dirt_type", dirtType.name().toLowerCase() );
    }

    public DirtType getDirtType() {
        return this.stateExists( "dirt_type" ) ? DirtType.valueOf( this.getStringState( "dirt_type" ).toUpperCase() ) : DirtType.NORMAL;
    }

    public enum DirtType {
        NORMAL,
        COARSE
    }

}
