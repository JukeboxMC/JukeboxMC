package jukeboxmc.item;

import org.jukeboxmc.block.BlockWaterlily;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemWaterlily extends Item {

    public ItemWaterlily() {
        super ( "minecraft:waterlily" );
    }

    @Override
    public BlockWaterlily getBlock() {
        return new BlockWaterlily();
    }
}
