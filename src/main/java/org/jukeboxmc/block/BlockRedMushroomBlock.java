package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemRedMushroomBlock;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockRedMushroomBlock extends Block {

    public BlockRedMushroomBlock() {
        super( "minecraft:red_mushroom_block" );
    }

    @Override
    public ItemRedMushroomBlock toItem() {
        return new ItemRedMushroomBlock( this.runtimeId );
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.RED_MUSHROOM_BLOCK;
    }

    public BlockRedMushroomBlock setHugeMushroom( int value ) { //0-15
        return this.setState( "huge_mushroom_bits", value );
    }

    public int getHugeMushroom() {
        return this.stateExists( "huge_mushroom_bits" ) ? this.getIntState( "huge_mushroom_bits" ) : 0;
    }
}
