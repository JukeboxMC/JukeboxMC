package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDaylightDetector;
import org.jukeboxmc.item.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDaylightDetector extends BlockWaterlogable {

    public BlockDaylightDetector() {
        super( "minecraft:daylight_detector" );
    }

    @Override
    public ItemDaylightDetector toItem() {
        return new ItemDaylightDetector();
    }

    @Override
    public BlockType getBlockType() {
        return BlockType.DAYLIGHT_DETECTOR;
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public double getHardness() {
        return 0.2;
    }

    @Override
    public ItemToolType getToolType() {
        return ItemToolType.AXE;
    }

    public void setRedstoneSignal( int value ) {
        this.setState( "redstone_signal", value );
    }

    public int getRedstoneSignal() {
        return this.stateExists( "redstone_signal" ) ? this.getIntState( "redstone_signal" ) : 0;
    }
}
