package org.jukeboxmc.block;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedMushroomBlock extends Block {

    public BlockRedMushroomBlock() {
        super( "minecraft:red_mushroom_block" );
    }

    public void setHugeMushroom( int value ) { //0-15
        this.setState( "huge_mushroom_bits", value );
    }

    public int getHugeMushroom() {
        return this.stateExists( "huge_mushroom_bits" ) ? this.getIntState( "huge_mushroom_bits" ) : 0;
    }
}
