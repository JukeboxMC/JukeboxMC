package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemComposter;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockComposter extends BlockWaterlogable {

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

    @Override
    public double getHardness() {
        return 0.6;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public void setComposterFillLevel( int value ) {
        this.setState( "composter_fill_level", value );
    }

    public int getComposterFillLevel() {
        return this.stateExists( "composter_fill_level" ) ? this.getIntState( "composter_fill_level" ) : 0;
    }
}
