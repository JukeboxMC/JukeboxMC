package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemWheat;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockWheat extends Block {

    public BlockWheat() {
        super( "minecraft:wheat" );
    }

    @Override
    public ItemWheat toItem() {
        return new ItemWheat();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.WHEAT;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    public void setGrowth( int value ) { //0-7
        this.setState( "growth", value );
    }

    public int getGrowth() {
        return this.stateExists( "growth" ) ? this.getIntState( "growth" ) : 0;
    }
}
