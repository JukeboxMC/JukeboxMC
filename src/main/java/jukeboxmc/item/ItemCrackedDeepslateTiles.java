package jukeboxmc.item;

import org.jukeboxmc.block.BlockCrackedDeepslateTiles;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class ItemCrackedDeepslateTiles extends Item{

    public ItemCrackedDeepslateTiles() {
        super( "minecraft:cracked_deepslate_tiles" );
    }

    @Override
    public BlockCrackedDeepslateTiles getBlock() {
        return new BlockCrackedDeepslateTiles();
    }
}
