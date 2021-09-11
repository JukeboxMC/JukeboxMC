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

    @Override
    public ItemBeetroot toItem() {
        return new ItemBeetroot();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.BEETROOT;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean canPassThrough() {
        return true;
    }

    public void setGrowth( int value ) { //0-7
        this.setState( "growth", value );
    }

    public int getGrowth() {
        return this.stateExists( "growth" ) ? this.getIntState( "growth" ) : 0;
    }
}
