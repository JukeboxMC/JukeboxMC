package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemFrostedIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockFrostedIce extends Block {

    public BlockFrostedIce() {
        super( "minecraft:frosted_ice" );
    }

    @Override
    public ItemFrostedIce toItem() {
        return new ItemFrostedIce();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.FROSTED_ICE;
    }

    public void setAge( int value ) {
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }
}
