package jukeboxmc.item;

import org.jukeboxmc.block.BlockJukebox;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemJukebox extends Item {

    public ItemJukebox() {
        super ( "minecraft:jukebox" );
    }

    @Override
    public BlockJukebox getBlock() {
        return new BlockJukebox();
    }
}
