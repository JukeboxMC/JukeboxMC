package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemComposter;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockComposter extends Block {

    public BlockComposter() {
        super( "minecraft:composter" );
    }

    @Override
    public ItemComposter toItem() {
        return new ItemComposter();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.COMPOSTER;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setComposterFillLevel( int value ) {
        this.setState( "composter_fill_level", value );
    }

    public int getComposterFillLevel() {
        return this.stateExists( "composter_fill_level" ) ? this.getIntState( "composter_fill_level" ) : 0;
    }
}
