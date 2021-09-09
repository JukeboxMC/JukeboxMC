package jukeboxmc.item;

import org.jukeboxmc.block.BlockDeepslateTiles;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemDeepslateTiles extends Item{

    public ItemDeepslateTiles() {
        super( "minecraft:deepslate_tiles" );
    }

    @Override
    public BlockDeepslateTiles getBlock() {
        return new BlockDeepslateTiles();
    }
}
