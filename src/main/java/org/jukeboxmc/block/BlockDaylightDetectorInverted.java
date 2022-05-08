package org.jukeboxmc.block;

import org.jukeboxmc.item.ItemDaylightDetectorInverted;
import org.jukeboxmc.item.type.ItemToolType;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BlockDaylightDetectorInverted extends BlockWaterlogable {

    public BlockDaylightDetectorInverted() {
        super( "minecraft:daylight_detector_inverted" );
    }

    @Override
    public ItemDaylightDetectorInverted toItem() {
        return new ItemDaylightDetectorInverted();
    }

    @Override
    public BlockType getType() {
        return BlockType.DAYLIGHT_DETECTOR_INVERTED;
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
