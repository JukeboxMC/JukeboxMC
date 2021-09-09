package jukeboxmc.item;

import org.jukeboxmc.block.BlockBlueIce;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemBlueIce extends Item {

    public ItemBlueIce() {
        super ( "minecraft:blue_ice" );
    }

    @Override
    public BlockBlueIce getBlock() {
        return new BlockBlueIce();
    }
}
