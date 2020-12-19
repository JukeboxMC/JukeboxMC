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
        return this.states( "dirt_type", dirtType.name().toLowerCase() );
    }

    public enum DirtType {
        NORMAL,
        COARSE
    }

}
