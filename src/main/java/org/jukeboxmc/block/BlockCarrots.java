package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCarrots;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCarrots extends Block {

    public BlockCarrots() {
        super( "minecraft:carrots" );
    }

    @Override
    public ItemCarrots toItem() {
        return new ItemCarrots();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CARROTS;
    }

    public void setGrowth( int value ) { //0-7
        this.setState( "growth", value );
    }

    public int getGrowth() {
        return this.stateExists( "growth" ) ? this.getIntState( "growth" ) : 0;
    }
}
