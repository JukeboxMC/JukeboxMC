package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockLightBlock extends Block {

    public BlockLightBlock() {
        super( "minecraft:light_block" );
    }

    public void setBlockLightLevel( int value ) { //0-15
        this.setState( "block_light_level", value );
    }

    public int getBlockLightLevel() {
        return this.stateExists( "block_light_level" ) ? this.getIntState( "block_light_level" ) : 0;
    }
}
