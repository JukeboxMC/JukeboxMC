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
        return this.setStates( "dirt_type", dirtType.name().toLowerCase() );
    }

    public DirtType getDirtType() {
        return this.states.containsKey( "dirt_type" ) ? DirtType.valueOf(  this.states.getString( "dirt_type" ).toUpperCase() ) : DirtType.NORMAL;
    }

    public enum DirtType {
        NORMAL,
        COARSE
    }

}
