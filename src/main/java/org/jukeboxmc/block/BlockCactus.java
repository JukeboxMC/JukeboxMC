package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemCactus;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockCactus extends Block {

    public BlockCactus() {
        super( "minecraft:cactus" );
    }

    @Override
    public ItemCactus toItem() {
        return new ItemCactus();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.CACTUS;
    }

    public void setAge( int value ) { //0-15
        this.setState( "age", value );
    }

    public int getAge() {
        return this.stateExists( "age" ) ? this.getIntState( "age" ) : 0;
    }

}
