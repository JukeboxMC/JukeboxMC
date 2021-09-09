package jukeboxmc.item;

import org.jukeboxmc.block.BlockDaylightDetectorInverted;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDaylightDetectorInverted extends Item {

    public ItemDaylightDetectorInverted() {
        super ( "minecraft:daylight_detector_inverted" );
    }

    @Override
    public BlockDaylightDetectorInverted getBlock() {
        return new BlockDaylightDetectorInverted();
    }
}
