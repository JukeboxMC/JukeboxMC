package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemNetherWart;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockNetherWart extends Block {

    public BlockNetherWart() {
        super( "minecraft:nether_wart" );
    }

    @Override
    public ItemNetherWart toItem() {
        return new ItemNetherWart();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.NETHER_WART;
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }

}
