package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemVine;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockVine extends Block {

    public BlockVine() {
        super( "minecraft:vine" );
    }

    @Override
    public ItemVine toItem() {
        return new ItemVine();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.VINE;
    }

    public void setVineDirection( int value ) { //0-15 TODO -> Add Direction Class
        this.setState( "vine_direction_bits", value );
    }

    public int getVineDirection() {
        return this.stateExists( "vine_direction_bits" ) ? this.getIntState( "vine_direction_bits" ) : 0;
    }
}
