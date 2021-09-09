package jukeboxmc.item;

import org.jukeboxmc.block.BlockDaylightDetector;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDaylightDetector extends Item {

    public ItemDaylightDetector() {
        super ( "minecraft:daylight_detector" );
    }

    @Override
    public BlockDaylightDetector getBlock() {
        return new BlockDaylightDetector();
    }
}
