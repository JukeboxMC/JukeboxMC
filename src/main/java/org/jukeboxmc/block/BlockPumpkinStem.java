package org.jukeboxmc.block;

import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.item.ItemPumpkinStem;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockPumpkinStem extends Block {

    public BlockPumpkinStem() {
        super( "minecraft:pumpkin_stem" );
    }

    @Override
    public ItemPumpkinStem toItem() {
        return new ItemPumpkinStem();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.PUMPKIN_STEM;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    public void setGrowth( int value ) { //0-7
        this.setState( "growth", value );
    }

    public int getGrowth() {
        return this.stateExists( "growth" ) ? this.getIntState( "growth" ) : 0;
    }

    public void setBlockFace( BlockFace blockFace ) {
        this.setState( "facing_direction", blockFace.ordinal() );
    }

    public BlockFace getBlockFace() {
        return this.stateExists( "facing_direction" ) ? BlockFace.values()[this.getIntState( "facing_direction" )] : BlockFace.NORTH;
    }
}
