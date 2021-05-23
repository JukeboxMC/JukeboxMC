package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemBeetroot;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockBeetroot extends Block {

    public BlockBeetroot() {
        super( "minecraft:beetroot" );
    }

    public void setGrowth( int value ) { //0-7
        this.setState( "growth", value );
    }

    public int getGrowth() {
        return this.stateExists( "growth" ) ? this.getIntState( "growth" ) : 0;
    }

    @Override
    public ItemBeetroot toItem() {
        return new ItemBeetroot();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEETROOT;
    }

}
