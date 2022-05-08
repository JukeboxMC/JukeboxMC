package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemPotatoes;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPotatoes extends Block {

    public BlockPotatoes() {
        super( "minecraft:potatoes" );
    }

    @Override
    public ItemPotatoes toItem() {
        return new ItemPotatoes();
    }

    @Override
    public BlockType getType() {
        return BlockType.POTATOES;
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
